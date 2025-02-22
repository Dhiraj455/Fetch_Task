package com.example.fetch_task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch_task.data.Hiring
import com.example.fetch_task.data.HiringApiInterface
import com.example.fetch_task.data.JSONData
import kotlinx.coroutines.launch

class HiringViewModel : ViewModel() {
    private val fetchService: HiringApiInterface = JSONData.api

    private val _groupedItems = MutableLiveData<Map<Int, List<Hiring>>>()
    val groupedItems: LiveData<Map<Int, List<Hiring>>> = _groupedItems

    private val _selectedListId = MutableLiveData(1)
    val selectedListId: LiveData<Int> = _selectedListId

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            val items = fetchService.getHiringData()
                .filter { it.name?.isNotBlank() == true }
                .sortedWith(compareBy({ it.listId }, { it.name }))
                .groupBy { it.listId }
            _groupedItems.postValue(items)
            Log.d("FetchViewModel", "Grouped Items: $items")
        }
    }

    fun setSelectedListId(listId: Int) {
        _selectedListId.postValue(listId)
    }
}
