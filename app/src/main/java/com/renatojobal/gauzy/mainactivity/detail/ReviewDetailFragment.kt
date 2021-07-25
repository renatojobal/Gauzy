package com.renatojobal.gauzy.mainactivity.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentComponentDetailBinding
import com.renatojobal.gauzy.databinding.FragmentReviewDetailBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [ReviewDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewDetailFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentReviewDetailBinding

    // View model
    private val sharedViewModel: SharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_review_detail,
            container,
            false
        )

        // Bind data
        binding.component = sharedViewModel.getSelectedComponent.value
        binding.user = sharedViewModel.userSession


        // Set up functionality
        setUpFunctionality()


        // Inflate the layout with binding root
        return binding.root
    }

    private fun setUpFunctionality() {


        // Listener of the button
        binding.frdBtnSumbit.setOnClickListener {

            // Get stars
            val targetStars : Float = binding.ratingBar.rating

            // Get comment
            val targetComment : String = binding.frdComment.text.toString()

            // Call shared view model
            sharedViewModel.addReview(targetStars, targetComment)


        }

    }


}