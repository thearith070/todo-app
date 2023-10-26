package com.test.todoapp.ui.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.todoapp.databinding.ItemTodoBinding
import com.test.todoapp.data.ToDo
import javax.inject.Inject

class ToDoAdapter @Inject constructor() :
    ListAdapter<ToDo, ToDoAdapter.ToDoViewHolder>(COMPARATOR) {

    private var itemCheckCallback: ((pos: Int) -> Unit)? = null
    private var itemDeleteCallback: ((pos: Int) -> Unit)? = null
    private var itemEditCallback: ((pos: Int) -> Unit)? = null

    fun setOnCheckItem(item: (pos: Int) -> Unit) = apply {
        this.itemCheckCallback = item
    }

    fun setOnEditItem(item: (pos: Int) -> Unit) = apply {
        this.itemEditCallback = item
    }

    fun setOnDeleteItem(item: (pos: Int) -> Unit) = apply {
        this.itemDeleteCallback = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ToDoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivCheck.setOnClickListener {
                itemCheckCallback?.invoke(adapterPosition)
            }
            binding.ivEdit.setOnClickListener {
                itemEditCallback?.invoke(adapterPosition)
            }
            binding.ivDelete.setOnClickListener {
                itemDeleteCallback?.invoke(adapterPosition)
                true
            }
        }

        fun bindItem(data: ToDo) {
            binding.apply {
                tvTask.text = data.task
                isCompleted = data.isCompleted
                ivEdit.isVisible = !data.isCompleted
                tvIsUpdated.isVisible = data.isUpdated
                if (data.isCompleted) {
                    tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvTask.paintFlags = 0
                }
            }
        }
    }

    companion object {

        val COMPARATOR = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.id == newItem.id && oldItem.isCompleted == newItem.isCompleted && oldItem.task == newItem.task
            }

            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem == newItem
            }
        }
    }
}