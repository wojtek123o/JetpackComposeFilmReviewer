package com.example.lab3comp.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab3comp.Data.DBItem
import com.example.lab3comp.Data.ItemViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lab3comp.R
import com.example.lab3comp.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilmListScreen(navController: NavController, itemViewModel: ItemViewModel) {
    itemViewModel.getAllItems()
    val lazyListState = rememberLazyListState()

    Scaffold(
        content = {
            val movieList: List<DBItem> by itemViewModel.itemList.observeAsState(initial = listOf())
            if (movieList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                    item {
                        Text(
                            text = "Lista filmów",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    items(items = movieList) { dbItem ->
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize()
                                .clickable {
                                    navController.navigate(
                                        Screen.ItemDetailsScreen.routeWithArgs(dbItem.id.toString())
                                    )
                                },
                            elevation = 10.dp,
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                androidx.compose.material.Text(text = "Tytuł: " + dbItem.text_main)
                                androidx.compose.material.Text(text = "Kategoria: " + dbItem.text_2)
                            }

                        }

                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    androidx.compose.material.Text(
                        "Brak fimów.\nKliknij + aby dodać film.",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    navController.navigate(Screen.AddItemScreen.route)
                }) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = stringResource(id = R.string.desc_add_fab),
                    )
                }
            }
        }
    )
}
