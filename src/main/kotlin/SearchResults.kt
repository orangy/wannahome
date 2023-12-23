import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

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