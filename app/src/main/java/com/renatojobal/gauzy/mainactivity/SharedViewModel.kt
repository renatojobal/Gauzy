package com.renatojobal.gauzy.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renatojobal.gauzy.repository.model.ComponentModel
import com.renatojobal.gauzy.repository.model.ReviewModel
import com.renatojobal.gauzy.repository.model.User
import timber.log.Timber
import java.text.NumberFormat

class SharedViewModel(
    private val db: FirebaseFirestore = Firebase.firestore
) : ViewModel() {

    /**
     * Current user
     */
    lateinit var userSession: User


    /** List of components to the home screen */
    private val _components = MutableLiveData<List<ComponentModel>>()
    val getComponentsAsLiveData: LiveData<List<ComponentModel>> = _components

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

                querySnapshot.documents.forEach { doc ->
                    Timber.d(doc.id)
                    val docId: String = doc.id
                    doc.data?.let { data ->
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
     * Selected component with data
     */
    private val _selectedComponent: MutableLiveData<ComponentModel> = MutableLiveData()
    val getSelectedComponent: LiveData<ComponentModel> = _selectedComponent
    fun setSelectedComponent(componentModel: ComponentModel) {
        _selectedComponent.postValue(componentModel)
        listenReviews(componentModel.docId)
    }

    /**
     * Current review
     */
    private val _ownReview : MutableLiveData<ReviewModel> = MutableLiveData()
    val getOwnReview : LiveData<ReviewModel> = _ownReview
    fun setOwnReview(review: ReviewModel?){
       _ownReview.postValue(review)
    }

    /**
     * List of review of selected component
     */
    private val _targetReviews = MutableLiveData<List<ReviewModel>>()
    val getTargetReviews: LiveData<List<ReviewModel>> = _targetReviews

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

                    querySnapshot.documents.forEach { doc ->

                        doc.data?.let { data ->
                            Timber.d("User: ${data["user"]}")

                            Timber.d("Score data type: ${data["score"]!!::class.simpleName}")


                            val reviewModel : ReviewModel = ReviewModel(
                                comment = data["comment"] as String,
                                score = data["score"] as Double,
                                user = data["user"] as DocumentReference,
                                userDisplayName = data ["userDisplayName"] as String
                            )

                            // If the review is form the user, do not show it
                            if(reviewModel.userDisplayName != userSession.displayName){

                                allReviews.add(reviewModel)
                            } else{

                                // Add at the beginning
                                setOwnReview(reviewModel)

                            }




                        }


                    }

                    Timber.d("Components: $allReviews")
                    _targetReviews.postValue(allReviews)
                }
            }
    }

    /**
     * Add the review to the currentComponent and the user in the current session
     */
    fun addReview(targetStars: Double, targetComment: String) {

        // Get user info

        // Create review
        val targetReview = ReviewModel(
            comment = targetComment,
            score = targetStars,
            user = userSession.let { db.collection("user-collection").document(it.uid) },
            userDisplayName = userSession.displayName
        )



        _selectedComponent.value?.docId?.let {
            db
                .collection("component-collection")
                .document(it)
                .collection("review-collection")
                .add(
                    targetReview
                )
                .addOnSuccessListener {
                    Timber.i("Review added")
                }
                .addOnFailureListener { e ->
                    Timber.e(e, "Error adding review")
                }
        }

    }

    /** List of components to the search screen */
    private val _filteredComponents = MutableLiveData<List<ComponentModel>>()
    val getFilteredComponents: LiveData<List<ComponentModel>> = _filteredComponents

    /**
     * Busca en la base de datos
     */
    fun filterComponents(targetName: String, targetCareer: String = "ALL"){
        val filteredComponents = ArrayList<ComponentModel>()
        _filteredComponents.postValue(filteredComponents)

        db
            .collection("component-collection")
            .whereEqualTo("name", targetName)
            .get()
            .addOnCompleteListener {

                it.result?.forEach { doc ->

                    Timber.d(doc.id)
                    val docId: String = doc.id
                    doc.data.let { data ->
                        val componentModel = ComponentModel(
                            docId = docId,
                            score = data["score"] as Double,
                            career = data["career"] as String,
                            name = data["name"] as String,
                            reviewsNumber = data["reviewsNumber"] as Long
                        )
                        filteredComponents.add(componentModel)
                    }




                    Timber.d("Components: $filteredComponents")
                    _filteredComponents.postValue(filteredComponents)

                }
            }


    }


    /**
     * Create a document for the user to allow it be referenced
     */
    fun addNewUser(user: User) {
        db.collection("user-collection")
            .document(user.uid)
            .set(user)
    }


    /**
     * Init of this view model
     */
    init {
        Timber.d("View model initializer")
        listenToComponents()

    }


}