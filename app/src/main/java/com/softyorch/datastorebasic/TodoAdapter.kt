package com.softyorch.datastorebasic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private var todoList: List<Pair<String, Todo>> = emptyList(),
    private val onItemSelected: (Actions, String) -> Unit
) : RecyclerView.Adapter<TodoViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Pair<String, Todo>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Todo>, newItem: Pair<String, Todo>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<String, Todo>, newItem: Pair<String, Todo>): Boolean {
            return oldItem.second == newItem.second
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallBack)

    fun setNewList(newList: List<Pair<String, Todo>>) {
        asyncListDiffer.submitList(newList)
        todoList = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount() = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position], onItemSelected)
    }
}