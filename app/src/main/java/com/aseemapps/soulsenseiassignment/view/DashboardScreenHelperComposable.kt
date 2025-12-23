package com.aseemapps.soulsenseiassignment.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aseemapps.soulsenseiassignment.data.model.DashboardApiRespose
import com.aseemapps.soulsenseiassignment.data.model.RailPageFaq
import com.aseemapps.soulsenseiassignment.viewmodel.DashboardViewmodel
@Composable
fun GenericProfileItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    description: String
) {
    Card(
        modifier = modifier
            .width(260.dp)
            .height(360.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xAA000000)
                            ),
                            startY = 300f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = name.uppercase(),
                    color = Color.White,
                    maxLines = 1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun DashboardContent(
    dashboardData: DashboardApiRespose,
    innerPadding: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFE2DECA))
    ) {

        item {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5B3B61),
                                Color(0xFF9B12B3),
                            )
                        )
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 0.dp),
                    text = "1 to 1 Big Card",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                HorizontalDataList(
                    dashboardData = dashboardData
                )
                Spacer(Modifier.height(16.dp))
            }
        }

        item {
            FaqSection()
        }
    }
}
@Composable
fun HorizontalDataList(
    modifier: Modifier = Modifier,
    dashboardData: DashboardApiRespose?
) {
    Box(
        modifier = modifier
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            dashboardData?.let {
                items(dashboardData.data.rail.items.size) { index ->
                    val item = dashboardData.data.rail.items[index]
                    GenericProfileItem(
                        imageUrl = item.image,
                        name = item.name,
                        description = item.shortDescription
                    )
                }
            }
        }

    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message)
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun FaqItem(
    faq: RailPageFaq,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = faq.question,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2B2B2B),
                modifier = Modifier.weight(1f)
            )

            androidx.compose.material3.Icon(
                imageVector = if (faq.isExpanded)
                    Icons.Default.KeyboardArrowUp
                else
                    Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black
            )
        }

        AnimatedVisibility(visible = faq.isExpanded) {
            Text(
                text = faq.answer,
                fontSize = 14.sp,
                color = Color(0xFF6B6B6B),
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Divider(
            modifier = Modifier.padding(top = 16.dp),
            color = Color(0xFFE0E0E0)
        )
    }
}
@Composable
fun FaqSection(
    viewModel: DashboardViewmodel = hiltViewModel()
) {
    val faqList = viewModel.faqList

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Frequently Asked Questions",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        faqList.forEachIndexed { index, faq ->
            FaqItem(
                faq = faq,
                onClick = { viewModel.onFaqClicked(index) }
            )
        }
    }
}



