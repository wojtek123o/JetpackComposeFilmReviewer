package com.example.lab3comp.Drawer

import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.lab3comp.R

@Composable
fun AppBar(
    onNavigationItemClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationItemClick) {
                Icon(
                    imageVector = Icons.Default.Menu, //icona hamburgera
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )
}