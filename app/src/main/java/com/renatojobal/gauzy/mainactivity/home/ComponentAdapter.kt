package com.renatojobal.gauzy.mainactivity.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.renatojobal.gauzy.R
import com.renatojobal.gauzy.databinding.ItemComponentBinding
import com.renatojobal.gauzy.repository.model.ComponentModel
import timber.log.Timber

class ComponentAdapter(
    private val componentList: LiveData<List<ComponentModel>>
) : RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder>(){

    interface Listener{

        abstract fun onClickListener(view : View)

    }

    /**
     * View holder in charge of bind data with layout
     */
    class ComponentViewHolder(private val itemBinding: ItemComponentBinding):
    RecyclerView.ViewHolder(itemBinding.root){

        /**
         * Bind the data with the layout
         */
        fun bind(componentModel: ComponentModel){
            Timber.d("Binding data with layout")

            itemBinding.component = componentModel

            itemBinding.root.setOnClickListener{ view ->
                view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())

            }

        }

    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        // Get inflater
        val layoutInflater = LayoutInflater.from(parent.context)

        // Using data binding
        val binding = DataBindingUtil.inflate<ItemComponentBinding>(
            layoutInflater,
            R.layout.item_component,
            parent,
            false
        )

        return ComponentViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        // Get elements from your data set at this position
        val targetComponent = getItem(position)

        // Replace the contents of the view with that element
        targetComponent?.let {
            holder.bind(it)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return if (componentList.value != null) {
            componentList.value?.size!!
        } else {
            0
        }
    }

    /**
     * Get the item form the live data with the given position
     */
    private fun getItem(position: Int): ComponentModel? {
        return componentList.value?.get(position)
    }
}