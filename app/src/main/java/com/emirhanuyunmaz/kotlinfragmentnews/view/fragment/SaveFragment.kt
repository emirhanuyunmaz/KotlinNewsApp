package com.emirhanuyunmaz.kotlinfragmentnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.SaveNewsFragmentBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.adapter.SaveNewsAdapter
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsData
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.NewsFragmentViewModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.SaveFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveFragment:Fragment() {


    private var _binding: SaveNewsFragmentBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModel<SaveFragmentViewModel>()
    private var adapter :SaveNewsAdapter?=null
    private var newsArrarList:ArrayList<NewsData>?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SaveNewsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataFromDatabase()
        binding.recyclerViewSaveView.layoutManager=LinearLayoutManager(this@SaveFragment.requireContext())
        observeLiveData()
    }

    private fun observeLiveData(){

    viewModel.savedNews.observe(viewLifecycleOwner, Observer {resource->
        resource.data?.let {
            newsArrarList= ArrayList(it)
            adapter=SaveNewsAdapter(newsArrarList!!,this.requireContext())
            binding.recyclerViewSaveView.adapter=adapter
            adapter!!.notifyDataSetChanged()
        }


    })



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}