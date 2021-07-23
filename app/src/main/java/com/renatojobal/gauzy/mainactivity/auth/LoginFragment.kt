package com.renatojobal.gauzy.mainactivity.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentLoginBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    // Data binding
    private lateinit var binding : FragmentLoginBinding

    // View model
    private val sharedViewModel : SharedViewModel by activityViewModels()

    private lateinit var auth: FirebaseAuth

    var provider = OAuthProvider.newBuilder("microsoft.com")


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

        // Microsoft provider
        setUpMicrosoftProvider()

        // Listener of the button
        binding.flCvLoginButton.setOnClickListener {

            val pendingResultTask: Task<AuthResult>? = FirebaseAuth.getInstance().pendingAuthResult;
            if (pendingResultTask != null) {
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask
                    .addOnSuccessListener {
                            // User is signed in.
                            // IdP data available in
                            //
                            Timber.d("hola")
                            Timber.i(it.getAdditionalUserInfo()?.getProfile().toString())
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                            // The OAuth ID token can also be retrieved:
                            // authResult.getCredential().getIdToken().
                        }
                    .addOnFailureListener {
                        // Handle failure.
                    }
            } else {
                // There's no pending result so you need to start the sign-in flow.
                // See below.

                this.activity?.let {
                    FirebaseAuth.getInstance()
                        .startActivityForSignInWithProvider(it, provider.build())
                        .addOnSuccessListener {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            Timber.d("hola")
                            Timber.i(it.getAdditionalUserInfo()?.getProfile().toString())
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                            // The OAuth ID token can also be retrieved:
                            // authResult.getCredential().getIdToken().
                        }
                        .addOnFailureListener(
                            OnFailureListener {
                                // Handle failure.
                            })
                }
            }
        }
    }

    private fun setUpMicrosoftProvider(){
        // Initialize Firebase Auth




        // Force re-consent.
        provider.addCustomParameter("prompt", "consent");

        // Target specific email with login hint.
        provider.addCustomParameter("login_hint", "user@utpl.edu.ec");

        // Optional "tenant" parameter in case you are using an Azure AD tenant.
        // eg. '8eaef023-2b34-4da1-9baa-8bc8c9d6a490' or 'contoso.onmicrosoft.com'
        // or "common" for tenant-independent tokens.
        // The default value is "common".
        provider.addCustomParameter("tenant", "6eeb49aa-436d-43e6-becd-bbdf79e5077d");

        provider.scopes = arrayListOf("mail.read", "calendars.read")



    }


}