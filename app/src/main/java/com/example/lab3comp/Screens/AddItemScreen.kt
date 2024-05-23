package com.example.lab3comp.Screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddItemScreen(navController: NavController, itemViewModel: ItemViewModel) {
    val context = LocalContext.current

    val text_main = remember {
        mutableStateOf("")
    }

    val text_2 = remember {
        mutableStateOf("")
    }

    val review = remember {
        mutableStateOf("")
    }

    val rating = remember {
        mutableFloatStateOf(0.0f)
    }

    var selectedDate by remember { mutableStateOf<Date?>(null) }

    val dateFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    val formattedDate: String? = selectedDate?.let { dateFormatter.format(it) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            labelResId = R.string.filmTitle,
            inputWrapper = text_main.value,
            maxLength = 50,
            maxLines = 1,
            isReadOnly = false
        ) {
            text_main.value = it
        }
        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            labelResId = R.string.filmCategory,
            inputWrapper = text_2.value,
            maxLength = 50,
            maxLines = 1,
            isReadOnly = false
        ) {
            text_2.value = it
        }
        CustomTextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            labelResId = R.string.filmReview,
            inputWrapper = review.value,
            maxLength = 500,
            maxLines = 7,
            isReadOnly = false
        ) {
            review.value = it
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ocena",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(value = rating.floatValue,
            isIndicator = false,
            style = RatingBarStyle.Fill(),
            onValueChange = {
                rating.floatValue = it
            },
            onRatingChanged = {},
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.White),
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
                .align(CenterHorizontally)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                Text(text = formattedDate ?: "Wybierz datę")
            }
        }

        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                onClick = {
                itemViewModel.addItem(
                    DBItem(text_main = text_main.value, text_2 = text_2.value, review = review.value, rating = rating.floatValue, imgResourceId = 0, date = selectedDate)
                )
                text_main.value = ""
                text_2.value = ""
                review.value = ""
                rating.floatValue = 0.0f
                selectedDate = null
                keyboardController?.hide()
                navController.popBackStack()
            },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Dodaj film")
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.White),
                onClick = {
//                keyboardController?.hide()
                navController.navigate(Screen.FilmListScreen.route)
//                navController.popBackStack()
            },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Powrót")
            }
        }
    }
}