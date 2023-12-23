import androidx.compose.desktop.ui.tooling.preview.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.seiko.imageloader.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

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
        floor = Floor(from = 2), bedrooms = Bedrooms(from = 1)
    ),
    page = 1,
    pageSize = 100
)

// https://api-gateway.ss.ge/v1/RealEstate/details?applicationId=28112287&updateViewCount=true


@Composable
@Preview
fun App() {
    var items by remember { mutableStateOf<SearchResponse?>(null) }
    LaunchedEffect(Unit) {
        val mainPage = httpClient.get("https://home.ss.ge/en/real-estate").bodyAsText()
        val metadataJson = mainPage.substringAfterLast("<script id=\"__NEXT_DATA__\" type=\"application/json\">")
            .substringBefore("</script></body></html>")
        metadata.raw = json.decodeFromString<Metadata>(metadataJson)
        httpClient.post("https://home.ss.ge/api/refresh_access_token").bodyAsText()
        val cookies = httpClient.cookies("https://home.ss.ge")
        val token = cookies["ss-session-token"]
        items = httpClient.post("https://api-gateway.ss.ge/v1/RealEstate/LegendSearch") {
            accept(ContentType.Application.Json)
            header("Accept-Language", "en")
            header("Authorization", "Bearer ${token?.value}")
            contentType(ContentType.Application.Json)
            setBody(query)
        }.body<SearchResponse>()
    }

    MaterialTheme(colorScheme = if (!isSystemInDarkTheme()) LightColors else DarkColors) {
        Column {
            items?.let { items ->
                LazyColumn(
                    Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items.realStateItemModel) { item ->
                        SearchItemCard(item, Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = ApplicationName
    ) {
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
            App()
        }
    }
}

const val ApplicationName = "WannaHome"


