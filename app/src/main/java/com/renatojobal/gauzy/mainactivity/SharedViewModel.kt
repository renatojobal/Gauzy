package com.renatojobal.gauzy.mainactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renatojobal.gauzy.repository.model.ComponentModel
import timber.log.Timber

class SharedViewModel(
    private val db: FirebaseFirestore = Firebase.firestore
) : ViewModel() {

    /** List of components to the home screen */
    private val _components = MutableLiveData<List<ComponentModel>>()
    val getComponentsAsLiveData : LiveData<List<ComponentModel>> = _components



    /**
     * This will hear any updater from firestore
     */
    private fun listenToComponents(){

        db.collection("component-collection").addSnapshotListener { snapshot, error ->
            // if there is an exception we want to skip
            if(error != null){
                Timber.w(error, "Listen failed")
                return@addSnapshotListener
            }
            // if we are here, we did not encounter an exception
            Log.i("SharedViewModel","No errors $snapshot")


            snapshot?.let { querySnapshot ->
                val allComponents = ArrayList<ComponentModel>()

                querySnapshot.documents.forEach { it ->
                    Log.d("SharedViewModel","${it}")
                    it.data?.let { data ->
                        val componentModel = ComponentModel(
                            score = data["score"] as Double,
                            career = data["career"] as String,
                            name = data["name"] as String,
                            reviewsNumber = data["reviewsNumber"] as Long
                        )
                        allComponents.add(componentModel)
                    }


                }

                Timber.d("Components: $allComponents")
                _components.postValue(allComponents)
            }
        }
        
    }

    /**
     * Init of this view model
     */
    init {
        Log.d("SharedViewModel", "View model initializer")
        listenToComponents()

    }


}