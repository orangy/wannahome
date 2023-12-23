import androidx.compose.runtime.*

object MetadataModel {
    var raw by mutableStateOf<Metadata?>(null)

    val cities by derivedStateOf {
        raw?.props?.pageProps?.locations?.municipalityChain?.flatMap { it.cities ?: emptyList() }?.associate {
            it.id to it.title
        } ?: emptyMap()
    }

    val districts by derivedStateOf {
        raw?.props?.pageProps?.locations?.visibleCities?.flatMap { it.districts }?.associate { city ->
            city.districtId to city.districtTitle
        } ?: emptyMap()
    }
    val subdistricts by derivedStateOf {
        raw?.props?.pageProps?.locations?.visibleCities?.flatMap { it.districts }?.flatMap { it.subDistricts }
            ?.associate { city ->
                city.subDistrictId to city.subDistrictTitle
            } ?: emptyMap()
    }
    val streets by derivedStateOf {
        raw?.props?.pageProps?.locations?.visibleCities?.flatMap { it.districts }?.flatMap { it.subDistricts }
            ?.flatMap { it.streets }
            ?.associate { city ->
                city.streetId to city.streetTitle
            } ?: emptyMap()
    }

    val municipality by derivedStateOf {
        raw?.props?.pageProps?.locations?.municipalityChain?.associate {
            it.municipalityId to it.municipalityTitle
        } ?: emptyMap()
    }
}

data class CityModel(
    val id: Int,
    val title: String,
)

