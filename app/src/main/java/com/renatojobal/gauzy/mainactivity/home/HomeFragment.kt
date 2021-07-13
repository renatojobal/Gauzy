package com.renatojobal.gauzy.mainactivity.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentHomeBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentHomeBinding

    // View model
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // Recycler view adapter
    private lateinit var componentAdapter: ComponentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
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


        // Set up recycler view
        componentAdapter = ComponentAdapter(sharedViewModel.getComponentsAsLiveData)

        binding.fhRvComponents.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = componentAdapter
        }

        // Set up listener of the recycler view
        sharedViewModel.getComponentsAsLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Log.d("HomeFragment","List is empty")
            } else {
                // Show the moons as a list
                Log.d("HomeFragment","List is not empty")
                Log.d("HomeFragment","List showing: ${it.size} item")
                binding.fhRvComponents.adapter = componentAdapter

            }
        })

    }

}