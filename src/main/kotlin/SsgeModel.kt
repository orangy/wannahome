import kotlinx.serialization.Serializable

@Serializable
data class SSQuery(
    val realEstateType: Int? = null,
    val realEstateDealType: Int? = null,
    val currencyId: Int = 2, // 1 GEL, 2 USD
    val page: Int = 1, // 1 total, 2 per m2
    val pageSize: Int = 100,

    val cityIdList: List<Int>? = null,
    val subdistrictIds: List<Int>? = null,
    val streetIds: List<Int>? = null,
    val rooms: List<Int>? = null,
    val priceType: Int? = null,
    val priceFrom: Int? = null,
    val priceTo: Int? = null,
    val areaFrom: Int? = null,
    val areaTo: Int? = null,
    val advancedSearch: SSAdvancedQuery = SSAdvancedQuery(),
)

@Serializable
data class SSAdvancedQuery(
    val floorTypes: List<Int>? = null,
    val toilets: List<Int>? = null,
    val balcony_Loggias: List<Int>? = null,
    val floor: SSRange? = null,
    val bedrooms: SSRange? = null,
    val kitchenArea: SSRange? = null,
    val totalArea: SSRange? = null,
    val withImageOnly: Boolean? = null,
    val heating: Boolean? = null,
    val isConfirmed: Boolean? = null,
)

@Serializable
data class SSRange(
    val from: Int? = null,
    val to: Int? = null,
)

@Serializable
data class SSResponse(
    val realStateItemModel: List<SSResponseItem>,
    val totalCount: Int
)

@Serializable
data class SSResponseItem(
    val applicationId: Int,
    val status: Int,
    val address: SSAddress,
    val price: SSPrice,
    val appImages: List<SSImage>,
    val imageCount: Int,
    val title: String,
    val shortTitle: String,
    val description: String?,
    val totalArea: Double?,
    val totalAmountOfFloor: Double?,
    val floorNumber: String?,
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
    val userInfo: SSUserInfo?,
    val similarityGroup: String?
)

@Serializable
data class SSUserInfo(
    val name: String,
    val image: String,
    val userType: Int
)

@Serializable
data class SSAddress(
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
data class SSPrice(
    val priceGeo: Int? = null,
    val unitPriceGeo: Int? = null,
    val priceUsd: Int? = null,
    val unitPriceUsd: Int? = null,
    val currencyType: Int? = null
)

@Serializable
data class SSImage(
    val fileName: String,
    val isMain: Boolean,
    val is360: Boolean,
    val orderNo: Int? = null,
    val imageType: Int,
    val fileNameThumb: String? = null,
)

@Serializable
data class SSPhone(
    val applicationPhoneId: Int,
    val applicationId: Int,
    val phoneNumber: String,
    val isMain: Boolean,
    val isApproved: Boolean,
    val hasViber: Boolean,
    val hasWhatsapp: Boolean
)

@Serializable
data class SSDescription(
    val ka: String?,
    val en: String?,
    val ru: String?,
    val allLanguageTogather: String?,
    val serializedText: String?,
    val text: String?
)

@Serializable
data class SSDetails(
    val applicationId: Int,
    val isInactiveApplication: Boolean,
    val address: SSAddress,
    val price: SSPrice,
    val appImages: List<SSImage>,
    val applicationPhones: List<SSPhone>,
    val title: String,
    val description: SSDescription,
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
    val mapInfo: SSMapInfo,
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
    val mortgagePercentages: SSMortgage,
    val priceLevel: String,
    val metaTitle: String,
    val similarityGroup: String?
)

@Serializable
data class SSMapInfo(
    val subway_station: String?,
    val supermarket: Int,
    val school: Int,
    val park: Int,
    val pharmacy: Int,
    val mapScreen: String?,
    val isMapmarker: String?
)

@Serializable
data class SSMortgage(
    val bogNominal: String,
    val bogEffective: String,
    val tbcNominal: String,
    val tbcEffective: String,
    val teraNominal: String,
    val teraEffective: String
)

@Serializable
data class SSMetadata(
    val props: SSMetadataProps,
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
data class SSMetadataProps(
    val pageProps: SSPageProps,
)

@Serializable
data class SSPageProps(
    val session: String?,
    val locations: SSLocations,
    val credentialsToken: String?,
    val appLocale: String?,
    val hiddenApplications: List<Int>?,
    val hiddenUsers: List<Unit>?,
    val preferredCurrency: String? = null,
    val listingGrid: String? = null,
    val imgCounter: Int?
)

@Serializable
data class SSLocations(
    val visibleCities: List<SSVisibleCity>,
    val visibleMunicipalitetyChain: List<SSMunicipality>?,
    val municipalityChain: List<SSMunicipality>?
)

@Serializable
data class SSVisibleCity(
    val cityId: Int,
    val cityTitle: String,
    val latitude: Double?,
    val longitude: Double?,
    val districts: List<SSDistrict>
)

@Serializable
data class SSDistrict(
    val districtId: Int,
    val districtTitle: String,
    val latitude: Double?,
    val longitude: Double?,
    val subDistricts: List<SSSubDistrict>
)

@Serializable
data class SSSubDistrict(
    val subDistrictId: Int,
    val subDistrictTitle: String,
    val subDistrictTitleSeo: String,
    val latitude: Double?,
    val longitude: Double?,
    val streets: List<SSStreet>
)

@Serializable
data class SSStreet(
    val streetId: Int,
    val streetTitle: String,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class SSCity(
    val id: Int,
    val title: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class SSMunicipality(
    val municipalityId: Int,
    val municipalityTitle: String?,
    val latitude: Double?,
    val longitude: Double?,
    val cities: List<SSCity>?
)