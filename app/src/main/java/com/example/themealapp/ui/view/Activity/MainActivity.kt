package com.example.themealapp.ui.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.themealapp.R
import com.example.themealapp.data.RoomDb.MealDataBase
import com.example.themealapp.databinding.ActivityMainBinding
import com.example.themealapp.ui.view.Fragment.FavoritesFragment
import com.example.themealapp.ui.view.Fragment.MainFragment
import com.example.themealapp.ui.view.Fragment.SearchFragment
import com.example.themealapp.ui.viewmodel.MainFragmentViewModel
import com.example.themealapp.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    val viewModel : MainFragmentViewModel by lazy {
        val mealDatabase = MealDataBase.getInstance(this)
        val mainViewModelProviderFactory = MainViewModelFactory(mealDatabase)
        ViewModelProvider(this,mainViewModelProviderFactory)[MainFragmentViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.mainFragment -> replaceFragment(MainFragment())
                R.id.searchFragment -> replaceFragment(SearchFragment())
                R.id.favoritesFragment -> replaceFragment(FavoritesFragment())
            }
            true
        }

    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment)
        fragmentTransaction.commit()
    }
}