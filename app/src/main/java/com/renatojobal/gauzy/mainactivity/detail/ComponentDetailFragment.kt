package com.renatojobal.gauzy.mainactivity.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentComponentDetailBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class ComponentDetailFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentComponentDetailBinding

    // View model
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // Adapter
    private lateinit var reviewAdapter: ReviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_component_detail,
            container,
            false
        )

        // Bind data
        binding.component = sharedViewModel.getSelectedComponent.value


        // Set up functionality
        setUpFunctionality()


        // Inflate the layout with binding root
        return binding.root
    }

    private fun setUpFunctionality() {
        Log.d("DetailFragment", "Setting up functionality")

        // Set up recycler view

        // Set up recycler view
        reviewAdapter = ReviewAdapter(sharedViewModel.getTargetReviews)

        binding.fdRvReviews.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = reviewAdapter
        }

        // Set up listener of the recycler view
        sharedViewModel.getTargetReviews.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Timber.d("List is empty")
            } else {
                // Show the moons as a list
                Timber.d("List is not empty")
                Timber.d("List showing: ${it.size} item")
                binding.fdRvReviews.adapter = reviewAdapter

            }
        })


        // Listener del boton
        binding.fdBtnScore.setOnClickListener {
            findNavController().navigate(ComponentDetailFragmentDirections.actionDetailFragmentToReviewDetailFragment())
        }

    }
}