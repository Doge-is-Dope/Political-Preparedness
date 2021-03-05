package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import timber.log.Timber

class ElectionsViewModel(private val database: ElectionDao, private val apiService: CivicsApiService) : ViewModel() {

    private val _apiStatus = MutableLiveData<CivicsApiStatus>()
    val apiStatus: LiveData<CivicsApiStatus>
        get() = _apiStatus

    private val _upComingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upComingElections

    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    private val _navigateToElectionDetails = MutableLiveData<Election>()
    val navigateToElectionDetails: LiveData<Election>
        get() = _navigateToElectionDetails


    init {
        getUpcomingElectionsFromAPI()
        getSavedElectionsFromDatabase()
    }


    private fun getUpcomingElectionsFromAPI() {
        _apiStatus.value = CivicsApiStatus.LOADING
        viewModelScope.launch {
            try {
                val response = apiService.getElections()
                _apiStatus.value = CivicsApiStatus.DONE
                _upComingElections.value = response.elections

            } catch (e: Exception) {
                Timber.e("Error: %s", e.localizedMessage)
                _apiStatus.value = CivicsApiStatus.ERROR
                _upComingElections.value = ArrayList()
            }
        }
    }

    private fun getSavedElectionsFromDatabase() {
        viewModelScope.launch {
            _savedElections.value = database.getElections()
        }
    }


    /**
     * When the election is clicked, set the [_navigateToElectionDetails]
     * @param election The [Election] that was clicked on.
     */
    fun displayElectionDetails(election: Election) {
        _navigateToElectionDetails.value = election
    }

    /**
     * After the navigation has taken place, set [_navigateToElectionDetails] to null
     */
    fun displayElectionDetailsComplete() {
        _navigateToElectionDetails.value = null
    }
}

enum class CivicsApiStatus { LOADING, ERROR, DONE }