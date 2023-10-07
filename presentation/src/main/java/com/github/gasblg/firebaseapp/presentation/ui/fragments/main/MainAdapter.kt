package com.github.gasblg.firebaseapp.presentation.ui.fragments.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.presentation.ui.swipe.SwipeControllerActions
import com.github.gasblg.firebaseapp.presentation.databinding.ItemBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ItemHolder>(), SwipeControllerActions {

    private var itemsList = listOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(ItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    private lateinit var onItemClickListener: ((item: Item, position: Int) -> Unit)

    fun setOnItemClickListener(onItemClickListener: (item: Item, position: Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private lateinit var onDeleteItemClickListener: ((item: Item, position: Int) -> Unit)

    fun setOnDeleteItemClickListener(onDeleteItemClickListener: (item: Item, position: Int) -> Unit) {
        this.onDeleteItemClickListener = onDeleteItemClickListener
    }

    private lateinit var onEditItemClickListener: ((item: Item, position: Int) -> Unit)

    fun setOnEditItemClickListener(onEditItemClickListener: (item: Item, position: Int) -> Unit) {
        this.onEditItemClickListener = onEditItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Item>) {
        itemsList = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item?) {
            binding.apply {
                item?.let { item ->
                    name.text = item.name
                    tvDescription.text = item.description
                    this.apply {

                        name.text = item.name
                        tvDescription.text = item.description

                        this.root.setOnClickListener {
                            onItemClickListener.invoke(item, adapterPosition)
                        }

                        this.root.setOnLongClickListener {
                            onEditItemClickListener.invoke(item, adapterPosition)
                            true
                        }

                    }

                }
            }
        }
    }

    override fun onRightClicked(position: Int) {
        val item = itemsList[position]
        onDeleteItemClickListener.invoke(item, position)
        notifyItemChanged(position)
    }

}



