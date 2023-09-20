package com.emirhanuyunmaz.kotlinfragmentnews.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.emirhanuyunmaz.kotlinfragmentnews.R
import com.emirhanuyunmaz.kotlinfragmentnews.databinding.ActivityMainBinding
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.NewsFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.SaveFragment
import com.emirhanuyunmaz.kotlinfragmentnews.view.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(NewsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuAllNews->{
                    println("news")
                    loadFragment(NewsFragment())
                    true
                }
                R.id.menuSaveNews->{
                    println("save")
                    loadFragment(SaveFragment())
                    true
                }
                R.id.menuSearchNews->{
                    println("search")
                    loadFragment(SearchFragment())
                    true
                }

                else -> {
                    true
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }
}