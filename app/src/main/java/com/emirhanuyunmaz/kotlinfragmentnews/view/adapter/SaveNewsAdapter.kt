package com.emirhanuyunmaz.kotlinfragmentnews.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirhanuyunmaz.kotlinfragmentnews.R
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.NewsRowLayoutBinding
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.SaveRowLayoutBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.ShowFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsData
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SaveNewsAdapter(var newsList:ArrayList<NewsData>,var context: Context):RecyclerView.Adapter<SaveNewsAdapter.SaveNewsViewHolder>(),
    KoinComponent {

    val database : NewsDatabase by inject()

    class SaveNewsViewHolder(var binding: SaveRowLayoutBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveNewsViewHolder {
        val bindingInflate=SaveRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SaveNewsViewHolder(bindingInflate)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: SaveNewsViewHolder, position: Int) {
        var news=newsList[position]
        holder.itemView.setOnClickListener {

            val transaction=(context as (AppCompatActivity)).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, ShowFragment(news.url))
            transaction.commit()
        }
        holder.binding.textViewDate.text=news.date
        holder.binding.textViewSource.text=news.source
        holder.binding.textViewTitle.text=news.title

        Glide.with(holder.itemView).load(news.imgUrl).into(holder.binding.imageView)
        holder.binding.imageView2.setOnClickListener {
            if(holder.binding.imageView2.tag.equals("saved")){
                //Data unsaved
                deleteDatabase(news)
            }
        }

    }


    private fun deleteDatabase(newsData: NewsData){
        newsList.clear()
        CoroutineScope(Dispatchers.IO).launch{
            val dao=database.getDao()
            dao.delete(newsData)
            var getAll=dao.getAllData()
            withContext(Dispatchers.Main){
                newsList.addAll(getAll)
                notifyDataSetChanged()
            }
        }
    }
}