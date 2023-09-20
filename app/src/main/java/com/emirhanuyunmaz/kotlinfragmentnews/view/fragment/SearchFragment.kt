package com.emirhanuyunmaz.kotlinfragmentnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.SearchFragmentBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.adapter.AllNewsAdapter
import com.emirhanuyunmaz.kotlinfragmentnews.view.view_model.SearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment:Fragment() {

    private var _binding: SearchFragmentBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchFragmentViewModel>()
    private var adapter : AllNewsAdapter? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearchFragment.layoutManager=LinearLayoutManager(this.requireContext())

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.getDataFromApi(p0!!)
                return true
            }

        })

        observeLiveData()
    }
    private fun observeLiveData(){

        viewModel.serachNewsData.observe(viewLifecycleOwner, Observer {resource->
            resource.data?.let {
                adapter= AllNewsAdapter(it,this.requireContext())
                binding.recyclerViewSearchFragment.adapter=adapter
                adapter!!.notifyDataSetChanged()
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}