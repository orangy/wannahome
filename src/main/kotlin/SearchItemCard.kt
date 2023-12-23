import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.seiko.imageloader.*

@Composable
fun SearchItemCard(item: SearchItem, modifier: Modifier = Modifier) {
    Column(
        modifier
            .clip(RoundedCornerShape(grid * 2))
            .background(MaterialTheme.colorScheme.background)
            .clickable { }
            .padding(grid * 4),
        verticalArrangement = Arrangement.spacedBy(grid * 4)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(grid * 2)) {
            ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                val address = listOfNotNull(
                    metadata.subdistricts[item.address.subdistrictId],
                    metadata.streets[item.address.streetId],
                    item.address.streetNumber
                ).joinToString()
                Text(text = address, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.weight(1f))
                item.totalArea?.let { area ->
                    Text(text = buildAnnotatedString {
                        append(if(area % 1 == 0.0) "${area.toInt()} m" else String.format("%.1f m", area))
                        withStyle(
                            SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontStyle = MaterialTheme.typography.labelSmall.fontStyle, 
                                fontSize = MaterialTheme.typography.labelSmall.fontSize
                            )
                        ) {
                            append("2")
                        }
                    }, color = MaterialTheme.colorScheme.onBackground)
                    Text(text = "•", color = MaterialTheme.colorScheme.secondary)
                }
                Text(
                    text = "${item.floorNumber}/${item.totalAmountOfFloor.toInt()} fl",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(text = "•", color = MaterialTheme.colorScheme.secondary)
                Text(text = "${item.numberOfBedrooms} bds", color = MaterialTheme.colorScheme.onBackground)
            }
        }

        Text(
            text = "${item.price.priceUsd}$",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge
        )

        Row(horizontalArrangement = Arrangement.spacedBy(grid * 2)) {
            item.appImages.forEach { image ->
                Image(
                    rememberImagePainter(image.fileName),
                    contentDescription = "change image",
                    modifier = Modifier.size(128.dp).clip(RoundedCornerShape(grid * 2)),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}