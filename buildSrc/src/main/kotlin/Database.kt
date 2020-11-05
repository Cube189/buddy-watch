import java.lang.System.getenv

object Database {
    val database = getenv("DB_NAME") ?: "postgres"
    val host = getenv("DB_HOST") ?: "localhost"
    val port = getenv("DB_PORT") ?: "5432"
    val schema = "public"
    val username = getenv("DB_USERNAME") ?: "postgres"
    val url = "jdbc:postgresql://${host}:${port}/${database}"
}
