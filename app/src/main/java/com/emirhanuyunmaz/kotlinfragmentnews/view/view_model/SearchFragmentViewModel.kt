package com.emirhanuyunmaz.kotlinfragmentnews.view.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsSearchDownload
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsSearchDownloadImpl
import com.emirhanuyunmaz.kotlinfragmentnews.view.service.NewsService
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragmentViewModel(private val api: NewsSearchDownload):ViewModel(){

    var serachNewsData=MutableLiveData<Resource<NewsModel>>()

    var loading=MutableLiveData<Resource<Boolean>>()
    private var job: Job? = null


    fun getDataFromApi(searchString: String){
        job= CoroutineScope(Dispatchers.IO).launch {
            var getData=api.downloadSearchNews(searchString)
            withContext(Dispatchers.Main){
                getData.data?.let {
                    serachNewsData.value=Resource.success(it)

                }

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}