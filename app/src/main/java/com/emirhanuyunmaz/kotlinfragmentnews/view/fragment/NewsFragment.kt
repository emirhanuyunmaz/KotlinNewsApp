package com.emirhanuyunmaz.kotlinfragmentnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.NewsFragmentBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.adapter.AllNewsAdapter
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.NewsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment:Fragment() {

    private var _binding: NewsFragmentBinding? = null

    private val binding get() = _binding!!

    private var newsData: NewsModel? = null

    private lateinit var adapter : AllNewsAdapter

    private val viewModel by viewModel<NewsFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getDataFromApi()
        observeLiveData()
        binding.recyclerViewAllNews.layoutManager=LinearLayoutManager(this.requireContext())

        binding.newsRefreshLayout.setOnRefreshListener {
            viewModel.getDataFromApi()
            binding.newsRefreshLayout.isRefreshing=false
        }

    }

    private fun observeLiveData(){
        viewModel.newsList.observe(viewLifecycleOwner, Observer {resources->
            resources.data?.let {
                newsData=it
                adapter=AllNewsAdapter(newsData!!,this.requireContext())
                binding.recyclerViewAllNews.adapter=adapter
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {resources->
            resources.data?.let {
                if (it){
                    binding.textViewError.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.INVISIBLE
                    binding.recyclerViewAllNews.visibility=View.INVISIBLE
                }
                else{
                    binding.textViewError.visibility=View.INVISIBLE
                    binding.progressBar.visibility=View.INVISIBLE
                    binding.recyclerViewAllNews.visibility=View.VISIBLE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {resources->
            resources.data?.let {
                if (it){
                    binding.textViewError.visibility=View.INVISIBLE
                    binding.progressBar.visibility=View.VISIBLE
                    binding.recyclerViewAllNews.visibility=View.INVISIBLE
                }else{
                    binding.textViewError.visibility=View.INVISIBLE
                    binding.progressBar.visibility=View.INVISIBLE
                    binding.recyclerViewAllNews.visibility=View.VISIBLE
                }

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}