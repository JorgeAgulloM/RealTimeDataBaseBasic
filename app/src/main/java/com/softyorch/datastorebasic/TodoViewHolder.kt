package com.softyorch.datastorebasic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.softyorch.datastorebasic.databinding.ItemTodoBinding

class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTodoBinding.bind(view)

    fun bind(todoTask: Pair<String, Todo>) {
        todoTask.second.apply {
            binding.apply {
                tvTitle.text = title
                tvDescription.text = description
            }
        }
    }
}