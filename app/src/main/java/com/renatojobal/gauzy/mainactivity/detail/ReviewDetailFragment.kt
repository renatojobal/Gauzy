package com.renatojobal.gauzy.mainactivity.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renatojobal.gauzy.R


/**
 * A simple [Fragment] subclass.
 * Use the [ReviewDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_detail, container, false)
    }


}