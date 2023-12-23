import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*

@Composable
fun SideBar(modifier: Modifier) {
    Column(
        modifier.background(paleGray).fillMaxSize().padding(grid * 2),
        verticalArrangement = Arrangement.spacedBy(grid * 4)
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(grid)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(grid * 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Subscriptions")
                    Text("Subscriptions", color = Color.DarkGray)
                    Spacer(Modifier.weight(1f))
                    IconButton({}) {
                        Icon(Icons.Default.Add, contentDescription = "Add subscription")
                    }
                }
                if (SubscriptionModel.subscriptions.isEmpty()) {
                    Text(
                        "Press + button to add a subscription.",
                        Modifier.fillMaxWidth().padding(start = grid * 8),
                        color = Color.LightGray
                    )
                } else {
                    SubscriptionModel.subscriptions.forEach {

                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(grid * 2)
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Bookmarks"
                )
                Text("Bookmarks", color = Color.DarkGray)
            }
        }
    }
}