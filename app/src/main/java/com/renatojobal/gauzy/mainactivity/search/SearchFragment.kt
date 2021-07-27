package com.renatojobal.gauzy.mainactivity.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentSearchBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import com.renatojobal.gauzy.mainactivity.home.ComponentAdapter
import com.renatojobal.gauzy.mainactivity.home.HomeFragmentDirections
import com.renatojobal.gauzy.repository.model.ComponentModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    // Binding
    private lateinit var binding : FragmentSearchBinding

    // View model
    private val sharedViewModel : SharedViewModel by activityViewModels()

    // Recycler view adapter
    private lateinit var componentAdapter: ComponentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
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
        componentAdapter = ComponentAdapter(sharedViewModel.getFilteredComponents, object : ComponentAdapter.Listener {
            override fun onClickListener(view: View, componentModel: ComponentModel) {

                sharedViewModel.setSelectedComponent(componentModel)

                view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
            }

        })

        binding.fsRvComponents.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = componentAdapter
        }

        // Set up listener of the recycler view
        sharedViewModel.getFilteredComponents.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Timber.d("List is empty")

                binding.fsLlPropose.visibility = View.VISIBLE
            } else {
                // Show the moons as a list
                binding.fsLlPropose.visibility = View.GONE
                Timber.d("List is not empty")
                Timber.d("List showing: ${it.size} item")
                binding.fsRvComponents.adapter = componentAdapter

            }
        })


        // Listener of search bar
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    sharedViewModel.filterComponents(newText)

                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    sharedViewModel.filterComponents(query)

                }
                return true
            }


        })


        // Listener of floating action button
        binding.fsBtnPropose.setOnClickListener {
            // TODO: Go to add component

        }

    }





}