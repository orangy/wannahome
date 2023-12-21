import androidx.compose.desktop.ui.tooling.preview.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.seiko.imageloader.*
import com.seiko.imageloader.component.*
import com.seiko.imageloader.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*
import okio.Path.Companion.toOkioPath
import java.io.*

val httpClient = HttpClient(CIO) {
    install(ContentEncoding) {
        deflate(1.0F)
        gzip(0.9F)
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(HttpCookies)
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
}

val query = RealEstateQuery(
    realEstateType = 5,
    realEstateDealType = 4,
    cityIdList = listOf(95),
    subdistrictIds = listOf(47, 31, 23),
    areaFrom = 50,
    currencyId = 2,
    priceType = 1,
    priceFrom = 50000,
    priceTo = 100000,
    rooms = listOf(2, 3),
    advancedSearch = AdvancedSearch(
        floor = Floor(from = 2),
        bedrooms = Bedrooms(from = 1)
    ),
    page = 1,
    pageSize = 100
)

@Composable
@Preview
fun App() {
    var items by remember { mutableStateOf<RealEstateResponse?>(null) }
    LaunchedEffect(Unit) {
        httpClient.post("https://home.ss.ge/api/refresh_access_token").bodyAsText()
        val cookies = httpClient.cookies("https://home.ss.ge")
        val token = cookies["ss-session-token"]
        items = httpClient.post("https://api-gateway.ss.ge/v1/RealEstate/LegendSearch") {
            accept(ContentType.Application.Json)
            header("Accept-Language", "en")
            header("Authorization", "Bearer ${token?.value}")
            contentType(ContentType.Application.Json)
            setBody(query)
        }.body<RealEstateResponse>()
    }

    MaterialTheme {
        items?.let { items ->
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items.realStateItemModel) { item ->
                    Text(text = item.title)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item.appImages.forEach { image ->
                            Image(
                                rememberImagePainter(image.fileName),
                                contentDescription = "change image",
                                modifier = Modifier.size(100.dp),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = ApplicationName
    ) {
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
            App()
        }
    }
}

fun generateImageLoader(): ImageLoader = ImageLoader {
    components {
        setupDefaultComponents()
    }
    interceptor {
        // cache 100 success image result, without bitmap
        defaultImageResultMemoryCache()
        memoryCacheConfig {
            maxSizeBytes(32 * 1024 * 1024) // 32MB
        }
        diskCacheConfig {
            directory(getCacheDir().toOkioPath().resolve("image_cache"))
            maxSizeBytes(512L * 1024 * 1024) // 512MB
        }
    }
}

// about currentOperatingSystem, see app
private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$ApplicationName")
    else -> throw IllegalStateException("Unsupported operating system")
}

private const val ApplicationName = "WannaHome"