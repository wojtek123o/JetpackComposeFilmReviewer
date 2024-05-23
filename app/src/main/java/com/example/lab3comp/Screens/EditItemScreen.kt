package com.example.lab3comp.Screens

import android.app.DatePickerDialog
import android.text.style.TtsSpan.DateBuilder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab3comp.Data.DBItem
import com.example.lab3comp.Data.ItemViewModel
import com.example.lab3comp.R
import com.example.lab3comp.Screen
import com.example.lab3comp.ui.theme.CustomTextField
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

var itemId: String = ""
var text_main : String = ""
var text_2: String = ""
var review: String = ""
var itemRating: Float = 0.0f
var imgUri: String = ""
var date: Date? = null


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditItemScreen(itemIdToEdit: String?, navController: NavController, itemViewModel: ItemViewModel) {
    val context = LocalContext.current
    itemViewModel.findItemByID(itemIdToEdit!!)
    val selectedItem = itemViewModel.foundItem.observeAsState().value!!
    itemId = selectedItem.id.toString()
    text_main = selectedItem.text_main
    text_2 = selectedItem.text_2
    review = selectedItem.review
    itemRating = selectedItem.rating
    imgUri = selectedItem.imgResourceId.toString()
    date = selectedItem.date

    val title = remember {
        mutableStateOf(text_main)
    }
    val category = remember {
        mutableStateOf(text_2)
    }
    val filmReview = remember {
        mutableStateOf(review)
    }
    val rating = remember {
        mutableFloatStateOf(itemRating)
    }

    var selectedDate by remember { mutableStateOf(date) }

    val dateFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    val formattedDate: String? = selectedDate?.let { dateFormatter.format(it) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            labelResId = R.string.filmTitle,
            inputWrapper = title.value,
            maxLength = 50,
            maxLines = 1,
            isReadOnly = false
        ) {
            title.value = it
        }

        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            labelResId = R.string.filmCategory,
            inputWrapper = category.value,
            maxLength = 50,
            maxLines = 1 ,
            isReadOnly = false
        ) {
            category.value = it
        }

        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            labelResId = R.string.filmReview,
            inputWrapper = filmReview.value,
            maxLength = 500,
            maxLines = 7,
            isReadOnly = false
        ) {
            filmReview.value = it
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ocena", style = MaterialTheme.typography.h6.copy(fontStyle = FontStyle.Italic, fontSize = 16.sp), modifier = Modifier.align(
            Alignment.CenterHorizontally
        ))
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(value = rating.floatValue,
            isIndicator = false,
            style = RatingBarStyle.Fill(),
            onValueChange = {
                rating.floatValue = it
            },
            onRatingChanged = {},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
            onClick = {
                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate = GregorianCalendar(year, month, dayOfMonth).time
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            },
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                Text(text = formattedDate ?: "Wybierz datę")
            }
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                onClick = {
                val item = DBItem(
                    id = itemId.toInt(),
                    text_main = title.value,
                    text_2 = category.value,
                    review = filmReview.value,
                    rating = rating.floatValue,
                    imgResourceId = imgUri.toInt(),
                    date = selectedDate
                )
                itemViewModel.update(item)
                itemId = ""
                text_main = ""
                text_2 = ""
                review = ""
                itemRating = 0.0f
                imgUri = ""
                date = null
                keyboardController?.hide()
                navController.navigate(Screen.FilmListScreen.route)
            },
                modifier = Modifier.weight(1f)
                    .width(100.dp)) {
                Text(text = "Edytuj film")
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                onClick = {
                keyboardController?.hide()
                navController.popBackStack()
            },
                modifier = Modifier
                    .weight(1f)
                    .width(100.dp)) {
                Text(text = "Powrót")
            }
        }
    }
}