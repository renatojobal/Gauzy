package com.renatojobal.gauzy.mainactivity.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentDetailBinding
import com.renatojobal.gauzy.databinding.FragmentHomeBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import com.renatojobal.gauzy.mainactivity.home.ComponentAdapter


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentDetailBinding

    // View model
    private val sharedViewModel: SharedViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        // Bind data


        // Set up functionality
        setUpFunctionality()


        // Inflate the layout with binding root
        return binding.root
    }

    private fun setUpFunctionality() {


    }
}