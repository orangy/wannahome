import kotlinx.serialization.Serializable

@Serializable
data class RealEstateQuery(
    val realEstateType: Int,
    val realEstateDealType: Int,
    val cityIdList: List<Int>,
    val subdistrictIds: List<Int>,
    val areaFrom: Int,
    val currencyId: Int,
    val priceType: Int,
    val priceFrom: Int,
    val priceTo: Int,
    val rooms: List<Int>,
    val advancedSearch: AdvancedSearch,
    val page: Int,
    val pageSize: Int
)

@Serializable
data class AdvancedSearch(
    val floor: Floor,
    val bedrooms: Bedrooms
)

@Serializable
data class Floor(
    val from: Int
)

@Serializable
data class Bedrooms(
    val from: Int
)

@Serializable
data class RealEstateResponse(
    val realStateItemModel: List<RealEstateItem>,
    val totalCount: Int
)

@Serializable
data class RealEstateItem (
    val applicationId: Int,
    val status: Int,
    val address: Address,
    val price: Price,
    val appImages: List<AppImages>,
    val imageCount: Int,
    val title: String,
    val shortTitle: String,
    val description: String?,
    val totalArea: Double,
    val totalAmountOfFloor: Double,
    val floorNumber: String,
    val numberOfBedrooms: Int,
    val type: Int,
    val dealType: Int,
    val isMovedUp: Boolean,
    val isHighlighted: Boolean,
    val isUrgent: Boolean,
    val vipStatus: Int,
    val hasRemoteViewing: Boolean,
    val videoLink: String?, // replace with actual type if known
    val commercialRealEstateType: Int,
    val orderDate: String,
    val createDate: String,
    val userId: String,
    val isFavorite: Boolean,
    val isForUkraine: Boolean,
    val isHidden: Boolean,
    val isUserHidden: Boolean,
    val isConfirmed: Boolean,
    val detailUrl: String,
    val homeId: String?, // replace with actual type if known
    val userInfo: UserInfo?, // replace with actual type if known
    val similarityGroup: String? // replace with actual type if known
)

@Serializable
data class UserInfo(
    val name: String,
    val image: String,
    val userType: Int
)

@Serializable
data class Address (
    val municipalityId: Int?, // replace with actual type if known
    val municipalityTitle: String?, // replace with actual type if known
    val cityId: Int,
    val cityTitle: String,
    val districtId: Int?,
    val districtTitle: String?,
    val subdistrictId: Int?,
    val subdistrictTitle: String?,
    val streetId: Int?,
    val streetTitle: String?,
    val streetNumber: String?
)

@Serializable
data class Price (
    val priceGeo: Int,
    val unitPriceGeo: Int,
    val priceUsd: Int,
    val unitPriceUsd: Int,
    val currencyType: Int
)

@Serializable
data class AppImages (
    val fileName: String,
    val isMain: Boolean,
    val is360: Boolean,
    val orderNo: Int,
    val imageType: Int
)