package com.example.apnamart.ui.helper

import androidx.fragment.app.Fragment
import com.example.apnamart.ui.fragments.HomeFragment

class Constants {
    companion object {
        const val HOME_FRAGMENT = "HOME_FRAGMENT"
        const val BASE_URL = "https://api.github.com/search/"
        const val QUERY = "stars"
        const val SUCCESS_MSG = "Data fetched successfully"
        const val LOCAL_DATA = "Fetched local data, couldn't connect to server"
        fun getClass(id: String): Class<Fragment> {
            return when (id) {
                HOME_FRAGMENT -> HomeFragment::class.java as Class<Fragment>
                else -> HomeFragment::class.java as Class<Fragment>
            }
        }
    }
}