import androidx.compose.runtime.*
import kotlinx.serialization.Serializable

object SubscriptionModel {
    var subscriptions = mutableStateListOf<Subscription>()
    var activeSubscription by mutableStateOf<Subscription?>(null, referentialEqualityPolicy())
}

data class Subscription(
    val dealType: DealType,
    val propertyType: PropertyType,
    val price: Range = Range(),
)

data class Range(
    val from: Int? = null,
    val to: Int? = null
)

@Serializable
enum class PropertyType(val value: Int) {
    Any(0), Cottage(1), Hotel(2), Land(3), House(4), Apartment(5), Commercial(6)
}

@Serializable
enum class DealType(val value: Int) {
    Any(0), Lease(1), Rent(2), DailyRent(3), Sale(4)
}
