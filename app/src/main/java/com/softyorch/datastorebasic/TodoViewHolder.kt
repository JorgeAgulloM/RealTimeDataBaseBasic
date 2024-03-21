package com.softyorch.datastorebasic

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softyorch.datastorebasic.databinding.ItemTodoBinding

class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTodoBinding.bind(view)

    fun bind(todoTask: Pair<String, Todo>, onItemSelected: (Actions, String) -> Unit) {
        todoTask.second.apply {
            binding.apply {
                tvTitle.text = title
                tvDescription.text = description
                tvReference.text = todoTask.first
                binding.ivDone.setOnClickListener { onItemSelected(Actions.DONE, todoTask.first) }
                binding.ivDelete.setOnClickListener { onItemSelected(Actions.DELETE, todoTask.first) }
                val color = if (todoTask.second.done == true) {
                    R.color.gold
                } else {
                    R.color.purple_200
                }
                binding.cvItem.strokeColor = ContextCompat.getColor(binding.cvItem.context, color)
            }
        }
    }
}