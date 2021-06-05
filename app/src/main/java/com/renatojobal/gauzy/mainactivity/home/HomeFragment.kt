package com.renatojobal.gauzy.mainactivity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentHomeBinding

    // View model


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        // Todo: View model


        // Bind data


        // Set up functionality
        setUpFunctionality()


        // Inflate the layout with binding root
        return binding.root
    }

    private fun setUpFunctionality() {
        // TODO("Not yet implemented")
    }

}