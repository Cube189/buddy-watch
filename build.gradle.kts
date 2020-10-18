import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.System.getenv

group = "me.gmur"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.toVersion(Version.java)

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:${Version.logger.logback}")
    implementation("ch.qos.logback:logback-core:${Version.logger.logback}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.jackson}")
    implementation("io.micrometer:micrometer-core:${Version.micrometer}")
    implementation("io.micrometer:micrometer-registry-prometheus:${Version.micrometer}")
    implementation("net.logstash.logback:logstash-logback-encoder:${Version.logger.encoder}")
    implementation("org.flywaydb:flyway-core:${Version.flyway}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.spring.boot}")
    implementation("org.springframework.boot:spring-boot-starter-jooq:${Version.spring.boot}")
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
    id("org.jlleitschuh.gradle.ktlint") version Version.ktlint
    id("org.springframework.boot") version Version.spring.boot
}

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

tasks.register<Test>("integration") {
    group = "verification"

    useJUnitPlatform {
        exclude(Path.unitTests)
        include(Path.integrationTests)
    }

    dependsOn(tasks["bootJar"])

    doFirst {
        if (!runsOnCi) environment["DB_PORT"] = dockerCompose.servicesInfos["postgres"]!!.tcpPorts[5432]
    }
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
    if (!runsOnCi) isRequiredBy(tasks["integration"])
}

springBoot {
    buildInfo()
}
