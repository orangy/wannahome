import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.seiko.imageloader.*

@Composable
fun SearchItemCard(item: SearchItem, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    Column(
        modifier.clip(RoundedCornerShape(grid * 2)).background(
            if (isHovered) paleGray else MaterialTheme.colorScheme.background
        )
            .padding(grid * 4).hoverable(interactionSource),
        verticalArrangement = Arrangement.spacedBy(grid * 4),
    ) {
        Row {
            ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                val address = listOfNotNull(
                    metadata.streets[item.address.streetId], item.address.streetNumber
                ).joinToString()
                Row(
                    Modifier.clip(RoundedCornerShape(grid * 2)).background(
                        if (isHovered) darkGray else paleGray
                    ).alignByBaseline()
                ) {
                    metadata.subdistricts[item.address.subdistrictId]?.let { district ->
                        Row(
                            Modifier.clip(RoundedCornerShape(grid * 2))
                                .background(MaterialTheme.colorScheme.primaryContainer).padding(grid * 4, grid * 2)
                                .alignByBaseline(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Location")
                            Text(text = district, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                    Box(
                        Modifier.height(IntrinsicSize.Max).padding(grid * 4, grid * 2).alignByBaseline(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = address, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(Modifier.weight(1f))
                Row(
                    Modifier.height(IntrinsicSize.Max).padding(grid * 4, grid * 2).alignByBaseline(),
                    horizontalArrangement = Arrangement.spacedBy(grid * 2)
                ) {
                    val info = buildAnnotatedString {
                        item.totalArea?.let { area ->
                            append(if (area % 1 == 0.0) "${area.toInt()} m" else String.format("%.1f m", area))
                            withStyle(
                                SpanStyle(
                                    baselineShift = BaselineShift.Superscript,
                                    fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
                                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                                )
                            ) {
                                append("2")
                            }
                            withStyle(SpanStyle(color = Color.LightGray)) {
                                append(" • ")
                            }
                        }
                        append("${item.floorNumber}/${item.totalAmountOfFloor.toInt()} fl")
                        withStyle(SpanStyle(color = Color.LightGray)) {
                            append(" • ")
                        }
                        append("${item.numberOfBedrooms} bds")
                    }

                    Text(info, color = MaterialTheme.colorScheme.onBackground, overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(grid * 2)) {
            item.appImages.forEach { image ->
                Image(
                    rememberImagePainter(image.fileName),
                    contentDescription = "Property Image",
                    modifier = Modifier.size(128.dp).clip(RoundedCornerShape(grid * 2)),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Text(
            text = "${String.format("%,d", item.price.priceUsd)}$",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
    }
}