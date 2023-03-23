package com.test.todoapp.ui.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.todoapp.databinding.ItemTodoBinding
import com.test.todoapp.room.ToDo
import javax.inject.Inject

class ToDoAdapter @Inject constructor() :
    ListAdapter<ToDo, ToDoAdapter.ToDoViewHolder>(COMPARATOR) {

    private var itemUpdateCallback: ((pos: Int) -> Unit)? = null
    private var itemLongPress: ((pos: Int) -> Unit)? = null
    private var itemClick: ((pos: Int) -> Unit)? = null

    fun setUpdateCompleted(item: (pos: Int) -> Unit) = apply {
        this.itemUpdateCallback = item
    }

    fun setOnItemClick(item: (pos: Int) -> Unit) = apply {
        this.itemClick = item
    }

    fun setOnLongPressed(item: (pos: Int) -> Unit) = apply {
        this.itemLongPress = item
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
                itemUpdateCallback?.invoke(adapterPosition)
            }
            binding.roots.setOnClickListener {
                itemClick?.invoke(adapterPosition)
            }
            binding.roots.setOnLongClickListener {
                itemLongPress?.invoke(adapterPosition)
                true
            }
        }

        fun bindItem(data: ToDo) {
            binding.apply {
                tvTask.text = data.task
                root.isSelected = data.isCompleted
                isCompleted = data.isCompleted
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