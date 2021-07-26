package com.renatojobal.gauzy.mainactivity.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.ItemReviewBinding
import com.renatojobal.gauzy.repository.model.ReviewModel
import timber.log.Timber

class ReviewAdapter(
    private val reviewList : LiveData<List<ReviewModel>>
): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>()
{

    /**
     * View holder in charge of bind data with layout
     */
    class ReviewViewHolder(private val itemBinding: ItemReviewBinding):
        RecyclerView.ViewHolder(itemBinding.root){

        /**
         * Bind the data with the layout
         */
        fun bind(reviewModel: ReviewModel){
            Timber.d("Binding data with layout")

            itemBinding.review = reviewModel
            itemBinding.scoreFloat = reviewModel.score.toFloat()

        }

    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        // Get inflater
        val layoutInflater = LayoutInflater.from(parent.context)

        // Using data binding
        val binding = DataBindingUtil.inflate<ItemReviewBinding>(
            layoutInflater,
            R.layout.item_review,
            parent,
            false
        )

        return ReviewViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        // Get elements from your data set at this position
        val targetReview = getItem(position)

        // Replace the contents of the view with that element
        targetReview?.let {
            holder.bind(it)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return if (reviewList.value != null) {
            reviewList.value?.size!!
        } else {
            0
        }
    }

    /**
     * Get the item form the live data with the given position
     */
    private fun getItem(position: Int): ReviewModel? {
        return reviewList.value?.get(position)
    }



}