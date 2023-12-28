import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun SideBar(modifier: Modifier) {
    Column(
        modifier.background(paleGray).fillMaxSize().padding(grid * 2),
        verticalArrangement = Arrangement.spacedBy(grid * 4)
    ) {
        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
            SubscriptionsSide()
            BookmarksSide()
        }
    }
}