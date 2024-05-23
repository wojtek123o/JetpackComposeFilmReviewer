package com.example.lab3comp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab3comp.Data.ItemViewModel
import com.example.lab3comp.R
import com.example.lab3comp.Screen
import com.example.lab3comp.ui.theme.CustomTextField
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ItemDetailsScreen(itemId: String?, navController: NavController, itemViewModel: ItemViewModel) {

    itemViewModel.findItemByID(itemId!!)
    val selectedItem = itemViewModel.foundItem.observeAsState().value
    val showDialog = remember { mutableStateOf(false) }

    val dateFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    val formattedDate: String? = selectedItem?.date?.let { dateFormatter.format(it) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (selectedItem != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.White,
                    elevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Tytuł",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = selectedItem.text_main,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Kategoria",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = selectedItem.text_2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Recenzja",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = selectedItem.review,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ocena",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        RatingBar(
                            value = selectedItem.rating,
                            isIndicator = true,
                            style = RatingBarStyle.Fill(),
                            onValueChange = {},
                            onRatingChanged = {},
                            modifier = Modifier.align(CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Data obejrzenia: ${formattedDate ?: ""}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }



                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                        onClick = {
                            showDialog.value = true
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Usuń",
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                        onClick = {
                            navController.navigate(
                                Screen.EditItemScreen.routeWithArgs(
                                    selectedItem.id.toString()
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "Edytuj")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                        onClick = {
                            navController.navigateUp()
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "Powrót")
                    }
                    if (showDialog.value) {
                        AlertDialog(
                            onDismissRequest = {
                                showDialog.value = false
                            },
                            title = { Text(text = "Czy chcesz usunąć film?") },
                            buttons = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextButton(
                                        onClick = {
                                            itemViewModel.deleteItem(selectedItem)
//                                            navController.navigate(Screen.FilmListScreen.route)
                                            navController.popBackStack()
                                            showDialog.value = false
                                        }
                                    ) {
                                        Text("Tak")
                                    }
                                    TextButton(
                                        onClick = {
                                            showDialog.value = false
                                        }
                                    ) {
                                        Text("Anuluj")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
