package com.example.apnamart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.apnamart.R
import com.example.apnamart.ui.activities.MainActivity
import com.example.apnamart.ui.helper.Constants
import dagger.hilt.android.AndroidEntryPoint


abstract class BaseFragment<T: ViewBinding> : Fragment() {

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun finish() = requireActivity().finish()
}