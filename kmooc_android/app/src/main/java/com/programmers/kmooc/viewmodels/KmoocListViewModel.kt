package com.programmers.kmooc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.repositories.KmoocRepository
import java.util.Collections.addAll

class KmoocListViewModel(private val repository: KmoocRepository) : ViewModel() {

    var progressVisible = MutableLiveData<Boolean>()
    var lectureList = MutableLiveData<LectureList>()

    fun list() {
        progressVisible.postValue(true)
        repository.list {
            this.lectureList.postValue(it)
            progressVisible.postValue(false)
        }

    }

    fun next() {
        progressVisible.postValue(true)
        val currentLectureList = this.lectureList.value ?:return
        repository.next(currentLectureList) { lectureList ->
            val currentLectures = currentLectureList.lectures
            val mergedLectures = currentLectures.toMutableList()
                .apply { addAll(lectureList.lectures) }
            lectureList.lectures = mergedLectures
            this.lectureList.postValue(lectureList)
            progressVisible.postValue(false)
        }
    }
}

class KmoocListViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocListViewModel::class.java)) {
            return KmoocListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}