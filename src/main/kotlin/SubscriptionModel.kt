import androidx.compose.runtime.*

object SubscriptionModel {
    var subscriptions = mutableStateListOf<Subscription>()
    var activeSubscription by mutableStateOf<Subscription?>(null)
}

class Subscription {
    
}
