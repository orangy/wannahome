import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionEditor(modifier: Modifier) {
    val subscriptionState = remember { mutableStateOf(Subscription(DealType.Any, PropertyType.Any)) }
    var subscription by subscriptionState
    var items by remember<MutableState<SearchResponse?>> { mutableStateOf(null) }
    LaunchedEffect(subscriptionState.value) {
        httpClient.post("https://home.ss.ge/api/refresh_access_token").bodyAsText()
        val cookies = httpClient.cookies("https://home.ss.ge")
        val token = cookies["ss-session-token"]
        items = httpClient.post("https://api-gateway.ss.ge/v1/RealEstate/LegendSearch") {
            accept(ContentType.Application.Json)
            header("Accept-Language", "en")
            header("Authorization", "Bearer ${token?.value}")
            contentType(ContentType.Application.Json)
            setBody(
                RealEstateQuery(
                    realEstateDealType = if (subscription.dealType == DealType.Any) null else subscription.dealType.value,
                    realEstateType = if (subscription.propertyType == PropertyType.Any) null else subscription.propertyType.value,
                )
            )
        }.body<SearchResponse>()
    }
    Column(modifier.background(darkGray)) {
        Row(
            Modifier.padding(grid * 2),
            horizontalArrangement = Arrangement.spacedBy(grid * 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnumTypeChip(
                subscriptionState,
                PropertyType.Any, "Property",
                PropertyType.entries,
                { it.propertyType },
                { subscription = subscription.copy(propertyType = it) }
            )
            Text("for")
            EnumTypeChip(
                subscriptionState,
                DealType.Any, "Deal",
                DealType.entries,
                { it.dealType },
                { subscription = subscription.copy(dealType = it) }
            )
        }
        SearchResults(items, Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Enum<T>> EnumTypeChip(
    subscriptionState: MutableState<Subscription>,
    any: T, anyText: String,
    values: List<T>,
    read: (Subscription) -> T,
    update: (T) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val value by remember { derivedStateOf { read(subscriptionState.value) } }
    FilterChip(
        value != any, onClick = { showMenu = true }, label = {
            Text(if (value != any) value.toString() else anyText, style = MaterialTheme.typography.labelMedium)
            DropdownMenu(showMenu, onDismissRequest = {
                showMenu = false
            }) {
                values.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.name)
                        },
                        onClick = {
                            update(it)
                            showMenu = false
                        },
                        trailingIcon = if (value == it) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else null)
                }
            }
        }, trailingIcon = {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Deal Type")
        })
}

@Composable
fun SearchResults(items: SearchResponse?, modifier: Modifier = Modifier) {
    Column(modifier.background(MaterialTheme.colorScheme.background)) {
        items?.let { items ->
            LazyColumn(
                Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(grid * 4),
                verticalArrangement = Arrangement.spacedBy(grid * 8)
            ) {
                items(items.realStateItemModel) { item ->
                    SearchItemCard(item, Modifier.fillMaxWidth())
                }
            }
        }
    }
}