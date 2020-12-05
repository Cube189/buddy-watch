import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import java.lang.System.getenv

group = "me.gmur"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.toVersion(Version.java)

repositories {
    mavenCentral()
}

buildscript {
    dependencies {
        classpath("org.jooq:jooq-codegen:${Version.jooq}")
        classpath("org.postgresql:postgresql:${Version.postgres}")
    }
}

dependencies {
    implementation("ch.qos.logback:logback-classic:${Version.logger.logback}")
    implementation("ch.qos.logback:logback-core:${Version.logger.logback}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.jackson}")
    implementation("com.google.code.gson:gson:${Version.gson}")
    implementation("com.squareup.okhttp3:okhttp:${Version.okhttp}")
    implementation("io.micrometer:micrometer-core:${Version.micrometer}")
    implementation("io.micrometer:micrometer-registry-prometheus:${Version.micrometer}")
    implementation("net.logstash.logback:logstash-logback-encoder:${Version.logger.encoder}")
    implementation("org.apache.commons:commons-lang3:${Version.commons.lang}")
    implementation("org.flywaydb:flyway-core:${Version.flyway}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}")
    implementation("org.jooq:jooq:${Version.jooq}")
    implementation("org.jooq:jooq-codegen:${Version.jooq}")
    implementation("org.jooq:jooq-meta:${Version.jooq}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.spring.boot}")
    implementation("org.springframework.boot:spring-boot-starter-jooq:${Version.spring.boot}") {
        exclude(group = "org.jooq")
    }
//    implementation("org.springframework.boot:spring-boot-starter-security:${Version.spring.boot}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Version.spring.boot}")

    runtimeOnly("org.postgresql:postgresql:${Version.postgres}")

    testImplementation("io.kotest:kotest-assertions-core:${Version.kotest}")
    testImplementation("io.kotest:kotest-assertions-json:${Version.kotest}")
    testImplementation("io.kotest:kotest-extensions-spring:${Version.kotest}")
    testImplementation("io.kotest:kotest-property:${Version.kotest}")
    testImplementation("io.kotest:kotest-runner-junit5:${Version.kotest}")
    testImplementation("io.mockk:mockk:${Version.mockk}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.spring.boot}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test:${Version.spring.security}")
}

plugins {
    kotlin("jvm") version Version.kotlin
    kotlin("plugin.spring") version Version.kotlin

    id("com.avast.gradle.docker-compose") version Version.docker
    id("io.spring.dependency-management") version Version.spring.dependencyManagement
    id("org.flywaydb.flyway") version Version.flyway
    id("org.jlleitschuh.gradle.ktlint") version Version.ktlint
    id("org.springframework.boot") version Version.spring.boot
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main")
project.the<SourceSetContainer>()["main"].java.srcDirs(Path.generatedClasses)
kotlin.sourceSets["test"].kotlin.srcDirs("src/test")

sourceSets["main"].resources.srcDirs("src/main/resources")
sourceSets["test"].resources.srcDirs("src/test/resources")

val runsOnCi = !getenv("CI").isNullOrBlank()

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = Version.java
    }
}

tasks.build {
    dependsOn(tasks["ktlintCheck"])
}

tasks.clean {
    delete("$rootDir/${Path.generatedClasses}")
}

tasks.register<Test>("integration") {
    group = "verification"

    useJUnitPlatform {
        exclude(Path.unitTests)
        include(Path.integrationTests)
    }

    doFirst {
        if (!runsOnCi) environment["DB_PORT"] = dockerCompose.servicesInfos["postgres"]!!.tcpPorts[5432]
    }
}

tasks.register<DefaultTask>("jooqGenerate") {
    group = "jooq"

    dependsOn(tasks["flywayMigrate"])

    doLast {
        val config =
            """
            <configuration xmlns="http://www.jooq.org/xsd/jooq-codegen-3.14.0.xsd">
                <jdbc>
                    <driver>org.postgresql.Driver</driver>
                    <url>${Database.url}</url>
                    <user>${Database.username}</user>
                </jdbc>
                <generator>
                    <name>org.jooq.codegen.KotlinGenerator</name>
                    <database>
                        <name>org.jooq.meta.postgres.PostgresDatabase</name>
                        <inputSchema>${Database.schema}</inputSchema>
                    </database>
                    <generate>
                        <javaTimeTypes>true</javaTimeTypes>
                    </generate>
                    <target>
                        <packageName>me.gmur.buddywatch.jooq</packageName>
                        <directory>$rootDir/${Path.generatedClasses}</directory>
                    </target>
                </generator>
            </configuration>
            """.trimIndent()

        GenerationTool.generate(config)
    }
}

tasks.compileKotlin {
    dependsOn(tasks["jooqGenerate"])
}

tasks.register<Test>("unit") {
    group = "verification"

    useJUnitPlatform {
        exclude(Path.integrationTests)
    }
}

tasks.test {
    dependsOn(tasks["unit"], tasks["integration"])
}

dockerCompose {
    dependencies {
        startedServices = listOf("postgres")
    }

    if (!runsOnCi) {
        isRequiredBy(tasks["flywayMigrate"])
        isRequiredBy(tasks["jooqGenerate"])
        isRequiredBy(tasks["integration"])
        isRequiredBy(tasks["test"])
    }
}

flyway {
    url = Database.url
    user = Database.username
    schemas = arrayOf(Database.schema)
}

ktlint {
    filter {
        exclude { it.file.path.contains(Path.generatedClasses) }
    }
}

springBoot {
    buildInfo()
}
