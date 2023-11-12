package com.example.apnamart.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.apnamart.R
import com.example.apnamart.databinding.ActivityMainBinding
import com.example.apnamart.ui.helper.Constants
import com.example.apnamart.ui.helper.Constants.Companion.HOME_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(HOME_FRAGMENT)
    }

    private fun addFragment(id: String) {
        supportFragmentManager.commit {
            add(R.id.container, Constants.getClass(id), null)
            setReorderingAllowed(true)
            addToBackStack(id)
        }
    }
}