package com.example.lab3comp.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.lab3comp.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val myRepository: MyRepository
): ViewModel() {

    fun getAllItems() {
        myRepository.getAllItems()
    }

    fun addItem(dbItem: DBItem) {
        myRepository.addItem(dbItem)
        getAllItems()
    }

    fun update(dbItem: DBItem) {
        myRepository.updateItemDetails(dbItem)
        getAllItems()
    }

    fun findItemByID(itemId: String) {
        myRepository.findItemById(itemId)
    }

    fun deleteItem(dbItem: DBItem) {
        myRepository.deleteItem(dbItem)
        getAllItems()
    }

    val itemList: LiveData<List<DBItem>> = myRepository.allItems
    val foundItem: LiveData<DBItem> = myRepository.foundItem
}
