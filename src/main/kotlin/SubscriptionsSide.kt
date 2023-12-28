import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*

@Composable
fun SubscriptionsSide() {
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
            IconButton({
                SubscriptionModel.subscriptions.add(
                    when (SubscriptionModel.subscriptions.size) {
                        0 -> Subscription(DealType.Rent, PropertyType.Apartment)
                        1 -> Subscription(DealType.Rent, PropertyType.Apartment, Range(100, 500))
                        2 -> Subscription(DealType.Sale, PropertyType.House, Range(50_000))
                        3 -> Subscription(DealType.DailyRent, PropertyType.Cottage, Range(null, 500))
                        else -> Subscription(DealType.Sale, PropertyType.Apartment)
                    }
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add subscription")
            }
        }

        if (SubscriptionModel.subscriptions.isEmpty()) {
            Text(
                "Press + button to add a subscription.",
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(start = grid * 8)
            )
        } else {
            SubscriptionModel.subscriptions.forEach { sub ->
                Column(Modifier
                    .fillMaxWidth()
                    .padding(start = grid * 4)
                    .clip(RoundedCornerShape(grid))
                    .let {
                        if (SubscriptionModel.activeSubscription === sub)
                            it.background(MaterialTheme.colorScheme.primaryContainer)
                        else
                            it
                    }
                    .clickable { SubscriptionModel.activeSubscription = sub }
                    .padding(horizontal = grid * 4, vertical = grid)
                ) {
                    Text("${sub.propertyType} for ${sub.dealType}")
                    val priceText = buildString {
                        if (sub.price.from != null) {
                            append("from \$${sub.price.from}")
                        }
                        if (sub.price.from != null && sub.price.to != null) {
                            append(" ")
                        }
                        if (sub.price.to != null) {
                            append("to \$${sub.price.to}")
                        }
                    }
                    if (priceText.isNotEmpty())
                        Text(priceText)
                }
            }
        }
    }
}