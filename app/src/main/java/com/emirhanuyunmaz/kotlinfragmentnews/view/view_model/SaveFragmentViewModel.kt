package com.emirhanuyunmaz.kotlinfragmentnews.view.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsData
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsDatabase
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveFragmentViewModel(private var database:NewsDatabase):ViewModel() {

    var savedNews=MutableLiveData<Resource<List<NewsData>>>()
    var error=MutableLiveData<Resource<Boolean>>()
    var loading=MutableLiveData<Resource<Boolean>>()
    private var job: Job? = null

    fun getDataFromDatabase(){
        loading.value=Resource.loading(true)
        job= CoroutineScope(Dispatchers.IO).launch{
            var dao=database.getDao()
            var allData=dao.getAllData()
            withContext(Dispatchers.Main){
                loading.value= Resource.loading(false)
                error.value=Resource.error("",false)
                savedNews.value=Resource.success(allData)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}