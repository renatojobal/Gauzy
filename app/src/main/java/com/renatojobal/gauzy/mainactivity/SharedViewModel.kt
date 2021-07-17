package com.renatojobal.gauzy.mainactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renatojobal.gauzy.repository.model.ComponentModel
import com.renatojobal.gauzy.repository.model.ReviewModel
import timber.log.Timber

class SharedViewModel(
    private val db: FirebaseFirestore = Firebase.firestore
) : ViewModel() {

    /** List of components to the home screen */
    private val _components = MutableLiveData<List<ComponentModel>>()
    val getComponentsAsLiveData: LiveData<List<ComponentModel>> = _components


    /**
     * Selected component with data
     */
    private val _selectedComponent: MutableLiveData<ComponentModel> = MutableLiveData()


    /**
     * List of review of selected component
     */
    private val _targetReviews = MutableLiveData<List<ReviewModel>>()
    val getTargetReviews: LiveData<List<ReviewModel>> = _targetReviews


    /**
     * This will hear any updater from firestore
     */
    private fun listenToComponents() {

        db.collection("component-collection").addSnapshotListener { snapshot, error ->
            // if there is an exception we want to skip
            if (error != null) {
                Timber.w(error, "Listen failed")
                return@addSnapshotListener
            }
            // if we are here, we did not encounter an exception
            Timber.i("No errors $snapshot")


            snapshot?.let { querySnapshot ->
                val allComponents = ArrayList<ComponentModel>()

                querySnapshot.documents.forEach { it ->
                    Timber.d("${it.id}")
                    val docId : String = it.id
                    it.data?.let { data ->
                        val componentModel = ComponentModel(
                            docId = docId,
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
     * Set the selected component and search for reviews
     */
    fun setSelectedComponent(componentModel: ComponentModel) {

        _selectedComponent.postValue(componentModel)

        listenReviews(componentModel.docId)


    }

    /**
     * Hear to the reviews of the component change
     */
    private fun listenReviews(componentId: String) {

        db
            .collection("component-collection")
            .document(componentId)
            .collection("review-collection")
            .addSnapshotListener { snapshot, error ->
                // if there is an exception we want to skip
                if (error != null) {
                    Timber.w(error, "Listen failed")
                    return@addSnapshotListener
                }
                // if we are here, we did not encounter an exception
                Timber.i("No errors $snapshot")


                snapshot?.let { querySnapshot ->
                    val allReviews = ArrayList<ReviewModel>()

                    querySnapshot.documents.forEach { it ->
                        Timber.d( "${it}")
                        it.data?.let { data ->
                            val reviewModel = ReviewModel(
                                comment = data["comment"] as String,
                                score = data["score"] as Long,
                                user = "TODO"
                            )
                            allReviews.add(reviewModel)
                        }


                    }

                    Timber.d("Components: $allReviews")
                    _targetReviews.postValue(allReviews)
                }
            }
    }


    /**
     * Init of this view model
     */
    init {
        Timber.d("View model initializer")
        listenToComponents()

    }


}