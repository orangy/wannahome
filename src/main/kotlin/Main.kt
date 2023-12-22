import androidx.compose.desktop.ui.tooling.preview.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.seiko.imageloader.*
import com.seiko.imageloader.component.*
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

// https://api-gateway.ss.ge/v1/RealEstate/details?applicationId=28112287&updateViewCount=true

private val json = Json {
    ignoreUnknownKeys = true
}

@Composable
@Preview
fun App() {
    var items by remember { mutableStateOf<RealEstateResponse?>(null) }
    var metadata by remember { mutableStateOf<Metadata?>(null) }
    LaunchedEffect(Unit) {
        val mainPage = httpClient.get("https://home.ss.ge/en/real-estate").bodyAsText()
        val metadataJson = mainPage.substringAfterLast("<script id=\"__NEXT_DATA__\" type=\"application/json\">")
            .substringBefore("</script></body></html>")
        metadata = json.decodeFromString<Metadata>(metadataJson)
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

    AppTheme {
        Column {
            metadata?.let { metadata ->
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    metadata.props.pageProps.locations.visibleCities.forEach {
                        Text(it.cityTitle)
                    }
                }
            }

            items?.let { items ->
                LazyColumn(
                    Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items.realStateItemModel) { item ->
                        Column(
                            Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(text = "${item.price.priceUsd}$", color = MaterialTheme.colorScheme.onBackground)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                item.appImages.forEach { image ->
                                    Image(
                                        rememberImagePainter(image.fileName),
                                        contentDescription = "change image",
                                        modifier = Modifier.size(200.dp).clip(RoundedCornerShape(4.dp)),
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                            item.description?.let {
                                Text(text = it)
                            }
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

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) = MaterialTheme(colorScheme = if (!useDarkTheme) LightColors else DarkColors, content = content)


private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)