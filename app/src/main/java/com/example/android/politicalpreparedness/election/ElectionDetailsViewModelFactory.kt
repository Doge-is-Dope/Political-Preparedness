package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDao

class ElectionDetailsViewModelFactory(private val localDataSource: ElectionDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionDetailsViewModel::class.java)) {
            return ElectionDetailsViewModel(localDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
