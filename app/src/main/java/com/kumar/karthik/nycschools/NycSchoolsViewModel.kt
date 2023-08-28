package com.kumar.karthik.nycschools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.repository.NycSchoolRepository
import com.kumar.karthik.nycschools.repository.NycSchoolsService
import com.kumar.karthik.nycschools.util.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NycSchoolsViewModel @Inject constructor(
    private val nycSchoolRepository: NycSchoolRepository,
    private val coroutineDispatcher: CoroutineDispatchers
): ViewModel() {

    private val _schoolsStateFlow = MutableStateFlow<SchoolsState>(SchoolsState.LoadingState)
    val schoolsStateFlow = _schoolsStateFlow.asStateFlow()

    init {
        fetchNycSchoolRepository()
    }

    internal fun fetchNycSchoolRepository() {
        viewModelScope.launch(coroutineDispatcher.io) {
            nycSchoolRepository.fetchAllNycSchools().collect {
                _schoolsStateFlow.value = it
            }
        }
    }
}