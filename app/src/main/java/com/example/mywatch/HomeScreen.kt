package com.example.mywatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mywatch.data.WatchModel
import com.example.mywatch.viewmodel.WatchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    watchViewModel: WatchViewModel,
    navigateToDetail: (Int) -> Unit,
    navigationToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredList by watchViewModel.filteredList.collectAsState()
    val searchQuery by watchViewModel.searchQuery.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Watch App")
        },
            actions = {
                IconButton(onClick = navigationToAbout) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "About_Page")
                }
            }
        )
    }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { watchViewModel.updateSearchQuery(it) },
                label = { Text("Cari jam...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            if (filteredList.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Data tidak ada")
                }

            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        filteredList,
                        key = { it.id }
                    ) { watch ->
                        WatchItemCard(
                            watch = watch,
                            onClick = { navigateToDetail(watch.id) }
                        )
                    }
                }
            }


        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchItemCard(
    watch: WatchModel,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,

        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = watch.image), contentDescription = watch.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = watch.title,
                style = MaterialTheme.typography.titleMedium, maxLines = 1, color = Color.Black
            )
            Text(
                text = watch.price,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
            )

        }
    }

}
