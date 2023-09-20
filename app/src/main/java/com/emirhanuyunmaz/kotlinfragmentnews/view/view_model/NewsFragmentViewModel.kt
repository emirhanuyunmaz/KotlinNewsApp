package com.emirhanuyunmaz.kotlinfragmentnews.view.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsDownload
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsDownloadImpl
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsFragmentViewModel(private val newsDownload:NewsDownload):ViewModel() {

    var loading=MutableLiveData<Resource<Boolean>>()
    var error=MutableLiveData<Resource<Boolean>>()
    var newsList=MutableLiveData<Resource<NewsModel>>()
    private var job : Job? = null



    fun getDataFromApi(){
        loading.value=Resource.loading(true)
        job= CoroutineScope(Dispatchers.IO).launch {
            val resorce=newsDownload.downloadNews()
            withContext(Dispatchers.Main){
                resorce.data?.let {
                    loading.value=Resource.loading(false)
                    error.value= Resource.error("",false)
                    newsList.value= Resource.success(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}