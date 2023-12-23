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
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

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

    MaterialTheme(colorScheme = if (!isSystemInDarkTheme()) LightColors else DarkColors) {
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

const val ApplicationName = "WannaHome"


