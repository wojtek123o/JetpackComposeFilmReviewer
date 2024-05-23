package com.example.lab3comp.Data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyRepository(private val myDao: MyDao) {

    val allItems = MutableLiveData<List<DBItem>>()
    val foundItem = MutableLiveData<DBItem>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addItem(newItem: DBItem) {
        coroutineScope.launch(Dispatchers.IO) {
            myDao.insert(newItem)
        }
    }

    fun updateItemDetails(newItem: DBItem) {
        coroutineScope.launch(Dispatchers.IO) {
            myDao.update(newItem)
        }
    }

    fun getAllItems() {
        coroutineScope.launch(Dispatchers.IO) {
            allItems.postValue(myDao.getAllItems())
        }
    }

    fun deleteItem(item: DBItem) {
        coroutineScope.launch(Dispatchers.IO) {
            myDao.delete(item)
        }
    }

    fun findItemById(itemId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            foundItem.postValue(myDao.getItem(itemId))
        }
    }
}