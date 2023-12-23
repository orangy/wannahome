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
data class SearchResponse(
    val realStateItemModel: List<SearchItem>,
    val totalCount: Int
)

@Serializable
data class SearchItem(
    val applicationId: Int,
    val status: Int,
    val address: Address,
    val price: Price,
    val appImages: List<ImageFile>,
    val imageCount: Int,
    val title: String,
    val shortTitle: String,
    val description: String?,
    val totalArea: Double?,
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
    val videoLink: String?, 
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
    val homeId: String?, 
    val userInfo: UserInfo?, 
    val similarityGroup: String? 
)

@Serializable
data class UserInfo(
    val name: String,
    val image: String,
    val userType: Int
)

@Serializable
data class Address(
    val municipalityId: Int?, 
    val municipalityTitle: String?, 
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
data class Price(
    val priceGeo: Int,
    val unitPriceGeo: Int,
    val priceUsd: Int,
    val unitPriceUsd: Int,
    val currencyType: Int
)

@Serializable
data class ImageFile(
    val fileName: String,
    val isMain: Boolean,
    val is360: Boolean,
    val orderNo: Int,
    val imageType: Int,
    val fileNameThumb: String? = null,
)

@Serializable
data class ApplicationPhones(
    val applicationPhoneId: Int,
    val applicationId: Int,
    val phoneNumber: String,
    val isMain: Boolean,
    val isApproved: Boolean,
    val hasViber: Boolean,
    val hasWhatsapp: Boolean
)

@Serializable
data class Description(
    val ka: String?,
    val en: String?,
    val ru: String?,
    val allLanguageTogather: String?,
    val serializedText: String?, 
    val text: String? 
)

@Serializable
data class RealStateDetails(
    val applicationId: Int,
    val isInactiveApplication: Boolean,
    val address: Address,
    val price: Price,
    val appImages: List<ImageFile>,
    val applicationPhones: List<ApplicationPhones>,
    val title: String,
    val description: Description,
    val status: String,
    val realEstateType: String,
    val realEstateTypeId: Int,
    val realEstateDealType: String,
    val realEstateDealTypeId: Int,
    val isUrgent: Boolean,
    val isHighlighted: Boolean,
    val isMovedUp: Boolean,
    val vipStatus: String,
    val orderDate: String,
    val userId: String,
    val uploadVideoLink: String?,
    val videoLinkThumb: String?,
    val applicationVideoLink: String?,
    val applicationVideoLinkThumb: String?,
    val userImage: String?,
    val userEntityType: String,
    val agencyId: Int?,
    val agencyName: String?,
    val agencyLogoUrl: String?,
    val locationLatitude: Double,
    val locationLongitude: Double,
    val isFavorite: Boolean,
    val isConfirmed: Boolean,
    val comment: String?,
    val blockReason: String?,
    val contactPerson: String,
    val mapInfo: MapInfo,
    val cadastralCode: String?,
    val hasRemoteViewing: Boolean,
    val isForUkraine: Boolean,
    val isPetFriendly: Boolean,
    val homeId: String?,
    val houseUrlPrefix: String,
    val companyName: String?,
    val companyLogo: String?,
    val companyId: String?,
    val projectName: String?,
    val projectId: String?,
    val viewCount: Int,
    val houseUrl: String,
    val houseRooms: List<String>?,
    val airConditioning: Boolean,
    val balcony: Boolean,
    val basement: Boolean,
    val cableTelevision: Boolean,
    val drinkingWater: Boolean,
    val electricity: Boolean,
    val elevator: Boolean,
    val fridge: Boolean,
    val furniture: Boolean,
    val garage: Boolean,
    val glazedWindows: Boolean,
    val heating: Boolean,
    val hotWater: Boolean,
    val internet: Boolean,
    val ironDoor: Boolean,
    val lastFloor: Boolean,
    val naturalGas: Boolean,
    val securityAlarm: Boolean,
    val sewage: Boolean,
    val storage: Boolean,
    val telephone: Boolean,
    val tv: Boolean,
    val washingMachine: Boolean,
    val water: Boolean,
    val wiFi: Boolean,
    val withPool: Boolean,
    val viewOnYard: Boolean,
    val viewOnStreet: Boolean,
    val comfortable: Boolean,
    val light: Boolean,
    val areaOfHouse: String,
    val areaOfYard: String,
    val bedrooms: Int,
    val floor: String,
    val floors: String,
    val kitchenArea: String?,
    val numberOfGuests: Int,
    val rooms: String,
    val totalArea: String,
    val toilet: String,
    val project: String,
    val balcony_Loggia: String,
    val realEstateStatus: String,
    val realEstateStatusId: Int,
    val floorType: Int,
    val floorTypeText: String?,
    val state: String,
    val houseWillHaveToLive: Boolean?,
    val commercialType: Int,
    val commercialTypeText: String?,
    val userApplicationCount: Int,
    val mortgagePercentages: MortgagePercentages,
    val priceLevel: String,
    val metaTitle: String,
    val similarityGroup: String?
)

@Serializable
data class MapInfo(
    val subway_station: String?, 
    val supermarket: Int,
    val school: Int,
    val park: Int,
    val pharmacy: Int,
    val mapScreen: String?, 
    val isMapmarker: String? 
)

@Serializable
data class MortgagePercentages(
    val bogNominal: String,
    val bogEffective: String,
    val tbcNominal: String,
    val tbcEffective: String,
    val teraNominal: String,
    val teraEffective: String
)

@Serializable
data class Metadata(
    val props: Props,
    val page: String?,
    val buildId: String?,
    val isFallback: Boolean?,
    val gip: Boolean?,
    val appGip: Boolean?,
    val locale: String?,
    val locales: List<String>?,
    val defaultLocale: String?,
    val scriptLoader: List<Unit>?
)

@Serializable
data class Props(
    val pageProps: PageProps,
)

@Serializable
data class PageProps(
    val session: String?, 
    val locations: Locations,
    val credentialsToken: String?,
    val appLocale: String?,
    val hiddenApplications: List<Int>?,
    val hiddenUsers: List<Unit>?,
    val preferredCurrency: String? = null,
    val listingGrid: String? = null,
    val imgCounter: Int?
)

@Serializable
data class Locations(
    val visibleCities: List<VisibleCity>,
    val visibleMunicipalitetyChain: List<Municipality>?,
    val municipalityChain: List<Municipality>?
)

@Serializable
data class VisibleCity(
    val cityId: Int,
    val cityTitle: String,
    val latitude: Double?,
    val longitude: Double?,
    val districts: List<District>
)

@Serializable
data class District(
    val districtId: Int,
    val districtTitle: String,
    val latitude: Double?,
    val longitude: Double?,
    val subDistricts: List<SubDistrict>
)

@Serializable
data class SubDistrict(
    val subDistrictId: Int,
    val subDistrictTitle: String,
    val subDistrictTitleSeo: String,
    val latitude: Double?,
    val longitude: Double?,
    val streets: List<Street>
)

@Serializable
data class Street(
    val streetId: Int,
    val streetTitle: String,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class City(
    val id: Int,
    val title: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class Municipality(
    val municipalityId: Int,
    val municipalityTitle: String?,
    val latitude: Double?,
    val longitude: Double?,
    val cities: List<City>?
)