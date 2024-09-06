package com.example.procoreinterview.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.procoreinterview.data.model.ApiItem
import com.example.procoreinterview.data.model.ItemEntity
import com.example.procoreinterview.presentation.viewmodel.ItemViewModel
import com.example.procoreinterview.ui.theme.white
import com.example.procoreinterview.ui.theme.whiteGrayOrange
import com.example.procoreinterview.util.ScreenRoutes
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemListScreen(navController: NavController,viewModel: ItemViewModel = hiltViewModel())
{

    val items by viewModel.items.collectAsState(initial = emptyList())
val context = LocalContext.current
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {2
        refreshing = true
        viewModel.refreshItems()
        refreshing = false
        Toast.makeText(context,"refreshing => $refreshing ",Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(Unit) {
        viewModel.refreshItems()
    }
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = ::refresh
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.1f)
            .background(whiteGrayOrange, shape = RectangleShape)
    ) {
        Text(text = "Items List", modifier = Modifier.align(Alignment.Center),
             style = MaterialTheme.typography.h4, color = white)
        }


    Box(modifier = Modifier.pullRefresh(state)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 100.dp)


        ) {
            Log.d("ItemListScreen", "Rendering ${items.size} items")

            items(items) { item ->
                Log.d("ItemListScreen", "Rendering item: ${item.name}")

                //            Text(text = item.name, modifier = Modifier.padding(6.dp))
                ItemCard(navController,item = item)
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)

        )
    }

}




@Composable
fun ItemCard(navController: NavController, item: ItemEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
            // Navigate to the ItemDetailsScreen using the item's ID
            navController.navigate(ScreenRoutes.ItemDetailsScreen.createRoute(item.id.toString()))
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name)
            Text(text = item.description)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ItemListPreview(){
//    ItemListScreen()
//}