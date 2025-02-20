package com.example.mywatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatch.data.WatchData
import com.example.mywatch.data.WatchModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchViewModel:ViewModel() {

    private val _watchList = MutableStateFlow(WatchData.item)
    val watchList : StateFlow<List<WatchModel>> = _watchList

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredList : StateFlow<List<WatchModel>> =
        combine(_watchList,_searchQuery) { list,query ->
            if (query.isBlank()){
                list
            }else{
                list.filter { it.title.contains(query, ignoreCase = true) }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _watchList.value
        )

    fun updateSearchQuery(newQuery: String){
        _searchQuery.value = newQuery
    }

    fun toggleFavorite(watchId: Int){
        viewModelScope.launch {
            _favoriteIds.update{ current ->
                if (current.contains(watchId)){
                    current - watchId
                }else{
                    current + watchId
                }
            }
        }
    }
}

