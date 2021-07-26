package com.renatojobal.gauzy.mainactivity.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.FragmentLoginBinding
import com.renatojobal.gauzy.mainactivity.SharedViewModel
import com.renatojobal.gauzy.repository.model.User
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    // Data binding
    private lateinit var binding: FragmentLoginBinding

    // View model
    private val sharedViewModel: SharedViewModel by activityViewModels()
    

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
                Timber.d("There's something already here! Finish the sign-in for your user.")
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask
                    .addOnSuccessListener {
                        // User is signed in.
                        // IdP data available in
                        //
                        Timber.d("hola")
                        Timber.i(it.additionalUserInfo?.getProfile().toString())
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        // The OAuth access token can also be retrieved:
                        // authResult.getCredential().getAccessToken().
                        // The OAuth ID token can also be retrieved:
                        // authResult.getCredential().getIdToken().
                    }
                    .addOnFailureListener {exception ->
                        // Handle failure.
                        Timber.e(exception, "Sign in flow failed")
                    }
            } else {
                Timber.d("There's no pending result so you need to start the sign-in flow.")
                //
                // See below.

                this.activity?.let {
                    FirebaseAuth.getInstance()
                        .startActivityForSignInWithProvider(it, provider.build())
                        .addOnSuccessListener { authResult ->
                            // User is signed in.

                            Timber.d("hola")
                            Timber.i(authResult.additionalUserInfo?.profile.toString())
                            Timber.i("Microsoft tenant id: ${authResult.user?.tenantId}")



                            Timber.i(authResult.user.toString())
                            Timber.d(authResult.user?.photoUrl.toString())
                            Timber.d(authResult.user?.metadata.toString())
                            Timber.d(authResult.user?.providerData.toString())

                            // Set user for current session
                            authResult.user?.let { firebaseUser ->

                                val user = User(
                                    uid = firebaseUser.uid,
                                    displayName = firebaseUser.displayName!!,
                                    email = firebaseUser.email!!
                                )

                                sharedViewModel.userSession = user

                                sharedViewModel.addNewUser(user)

                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            }



                        }
                        .addOnFailureListener { exception ->
                            // Handle failure.
                           Timber.e(exception, "Sign in flow failed")
                        }
                }
            }
        }
    }


    private fun setUpMicrosoftProvider() {
        // Initialize Firebase Auth


        // Force re-consent.
        provider.addCustomParameter("prompt", "consent");



        // Optional "tenant" parameter in case you are using an Azure AD tenant.
        // eg. '8eaef023-2b34-4da1-9baa-8bc8c9d6a490' or 'contoso.onmicrosoft.com'
        // or "common" for tenant-independent tokens.
        // The default value is "common".
        provider.addCustomParameter("tenant", "6eeb49aa-436d-43e6-becd-bbdf79e5077d");

        provider.scopes = arrayListOf(
            "mail.read",
            "calendars.read",
            "profile"
        )


    }





}