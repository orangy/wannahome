import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

val json = Json {
    ignoreUnknownKeys = true
}

val httpClient = HttpClient(CIO) {
    install(ContentEncoding) {
        deflate(1.0F)
        gzip(0.9F)
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpCookies)
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
}