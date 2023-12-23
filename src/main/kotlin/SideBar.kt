import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*

@Composable
fun SideBar(modifier: Modifier) {
    Column(
        modifier.background(paleGray).fillMaxSize().padding(grid * 4),
        verticalArrangement = Arrangement.spacedBy(grid * 4)
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(grid * 2)) {
                Icon(Icons.Default.Search, contentDescription = "Search")
                Text("Search", color = Color.DarkGray)
            }
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(grid * 2)) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Bookmarks"
                )
                Text("Bookmarks", color = Color.DarkGray)
            }
        }
    }
}