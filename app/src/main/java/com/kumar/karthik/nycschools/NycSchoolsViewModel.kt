package com.kumar.karthik.nycschools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.repository.NycSchoolRepository
import com.kumar.karthik.nycschools.util.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NycSchoolsViewModel @Inject constructor(
    private val nycSchoolRepository: NycSchoolRepository,
    private val coroutineDispatcher: CoroutineDispatchers
): ViewModel() {

    private val _schoolsStateFlow = MutableStateFlow<SchoolsState>(SchoolsState.LoadingState)
    val schoolsStateFlow = _schoolsStateFlow.asStateFlow()

    private val _schoolRecordFlow = MutableStateFlow<SchoolPerformanceRecordState>(
        SchoolPerformanceRecordState.LoadingState
    )
    val schoolRecordFlow: StateFlow<SchoolPerformanceRecordState> = _schoolRecordFlow.asStateFlow()

    init {
        fetchNycSchoolRepository()
    }

    private fun fetchNycSchoolRepository() {
        viewModelScope.launch(coroutineDispatcher.io) {
            nycSchoolRepository.fetchAllNycSchools().collect {
                _schoolsStateFlow.value = it
            }
        }
    }

    fun fetchNycSchoolData(dbn: String) {
        viewModelScope.launch(coroutineDispatcher.io) {
            _schoolRecordFlow.value = _schoolsStateFlow.transformLatest { schoolState ->
                emit(
                    when {
                        schoolState is SchoolsState.ValidSchoolDataState -> {
                            schoolState.schoolsRecords.firstOrNull { schoolRecord ->
                                schoolRecord.dbn == dbn
                            }?.let { schoolRecord ->
                                SchoolPerformanceRecordState.SchoolPerformanceDataRecordState(schoolRecord)
                            } ?: SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState
                        } else -> {
                            SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState
                        }
                    }
                )
            }.first()
        }
    }
}