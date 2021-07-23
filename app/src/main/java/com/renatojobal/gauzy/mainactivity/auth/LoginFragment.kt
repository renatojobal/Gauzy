package com.renatojobal.gauzy.mainactivity.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentLoginBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import java.lang.RuntimeException


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentLoginBinding

    // View model
    private val sharedViewModel : SharedViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get binding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
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

        binding.flCvLoginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }


}