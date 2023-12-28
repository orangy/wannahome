import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*

@Composable
fun BookmarksSide() {
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