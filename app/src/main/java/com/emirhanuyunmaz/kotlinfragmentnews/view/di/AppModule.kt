package com.emirhanuyunmaz.kotlinfragmentnews.view.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.emirhanuyunmaz.kotlinfragmentnews.view.adapter.AllNewsAdapter
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.SearchFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsDownload
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsDownloadImpl
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsSearchDownload
import com.emirhanuyunmaz.kotlinfragmentnews.view.repository.NewsSearchDownloadImpl
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsDatabase
import com.emirhanuyunmaz.kotlinfragmentnews.view.service.NewsService
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Const.Companion.BASE_URL
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Const.Companion.DATABASE_NAME
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.NewsFragmentViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.SaveFragmentViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.SearchFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.scoped
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClassifier

val appModule= module {

    //Create Single
    single {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    single<NewsDownloadImpl> {
        NewsDownloadImpl(get())
    }

    viewModel {
        NewsFragmentViewModel(get())
    }

    single {
        NewsDownloadImpl(get()) as NewsDownload
    }

    single {
        Room.databaseBuilder(androidApplication()
            .applicationContext,
            NewsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    single {
        SaveFragmentViewModel(get())
    }
    single {
        SearchFragmentViewModel(get())
    }
    single {
        NewsSearchDownloadImpl(get()) as NewsSearchDownload
    }

    scope<AllNewsAdapter> {
        scoped {
            Room.databaseBuilder(androidApplication()
                .applicationContext,
                NewsDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }




}