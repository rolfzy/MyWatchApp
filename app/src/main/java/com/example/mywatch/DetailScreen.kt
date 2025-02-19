package com.example.mywatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mywatch.data.WatchModel
import com.example.mywatch.viewmodel.WatchViewModel

@Composable
fun DetailScreen(
    watchViewModel: WatchViewModel,
    watchId: Int,
    onbackClick: () -> Unit
) {

    val watchList by watchViewModel.watchList.collectAsState()
    val favoriteIds by watchViewModel.favoriteIds.collectAsState()

    val watch = watchList.find { it.id == watchId } ?: return
    val isFavorite = favoriteIds.contains(watchId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = watch.title)
                },
                navigationIcon = {
                    IconButton(onClick = onbackClick) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_media_previous),
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
        bottomBar = {
            BottomAppBar(

                tonalElevation = 8.dp, containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Button(
                    onClick = { }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = if (isFavorite) "Remove from favorite" else "Tambah ke favorite")


                }

            }
        }) { innerpadding ->
        DetailContent(watch = watch, modifier = Modifier.padding(innerpadding))


    }

}

@Composable
fun DetailContent(
    watch: WatchModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Image(
            painter = painterResource(id = watch.image), contentDescription = watch.title,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = watch.title,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = watch.price,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = watch.shortDesc,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = watch.LongtDesc,
            style = MaterialTheme.typography.bodyMedium, overflow = TextOverflow.Visible
        )

    }

}