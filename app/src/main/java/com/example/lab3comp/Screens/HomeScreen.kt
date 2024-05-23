package com.example.lab3comp.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3comp.R

@Composable
fun HomeScreen(page: String?) {
    var currentPage = "0"
    val context = LocalContext.current
    if (page == null) {
        if (getFromPreferences(context) != null) {
            currentPage = getFromPreferences(context) ?: "0"
            saveToPreferences(context, currentPage)
        }
    } else {
        saveToPreferences(context, page)
        currentPage = page
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.invitation),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        val imageArr = listOf(
            R.drawable.movie2,
            R.drawable.movie1,
            R.drawable.movie3
        )

        Image(
            painter = painterResource(id = imageArr[currentPage.toInt()]),
            contentDescription = null,
            modifier = Modifier
                .size(212.dp, 219.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.autor),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


fun saveToPreferences(context: Context, page: String?) {
    val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("page_key", page)
        apply()
    }
}

fun getFromPreferences(context: Context): String? {
    val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
    return sharedPref.getString("page_key", null)
}
