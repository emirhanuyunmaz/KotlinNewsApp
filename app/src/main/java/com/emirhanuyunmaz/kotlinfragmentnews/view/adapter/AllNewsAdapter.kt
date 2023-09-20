package com.emirhanuyunmaz.kotlinfragmentnews.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirhanuyunmaz.kotlinfragmentnews.R
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.NewsRowLayoutBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.SearchFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.ShowFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsData
import com.emirhanuyunmaz.kotlinfragmentnews.view.room_database.NewsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AllNewsAdapter(var newsModel:NewsModel,var context:Context) :RecyclerView.Adapter<AllNewsAdapter.AllNewsViewHolder>(),KoinComponent {

    val database :NewsDatabase by inject()

    class AllNewsViewHolder(var binding:NewsRowLayoutBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsViewHolder {
        val binding=NewsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllNewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsModel!!.articles.size
    }

    override fun onBindViewHolder(holder: AllNewsViewHolder, position: Int) {
        newsModel?.let {
            var artic=it.articles[position]
            holder.itemView.setOnClickListener {

                val transaction=(context as (AppCompatActivity)).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, ShowFragment(artic.url))
                transaction.commit()
            }

            holder.binding.textViewDate.text=artic.publishedAt
            holder.binding.textViewTitle.text=artic.title
            holder.binding.textViewSource.text=artic.source.name
            Glide.with(holder.itemView).load(artic.urlToImage).into(holder.binding.imageView)
            holder.binding.imageView2.setOnClickListener {
                if(holder.binding.imageView2.tag.equals("unsaved")){
                    //Data Saved
                    var newNewsData=NewsData(artic.url,artic.urlToImage.toString(),artic.title,artic.source.name,artic.publishedAt)
                    insertDatabase(newNewsData)
                    holder.binding.imageView2.tag="saved"
                    Glide.with(holder.itemView).load(R.drawable.saved).into(holder.binding.imageView2)
                }
            }


        }
    }

    private fun insertDatabase(newsData: NewsData){
        CoroutineScope(Dispatchers.IO).launch{
            val dao=database.getDao()
            dao.insert(newsData)
        }
    }

}