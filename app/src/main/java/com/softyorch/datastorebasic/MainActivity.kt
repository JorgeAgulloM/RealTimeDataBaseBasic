package com.softyorch.datastorebasic

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.softyorch.datastorebasic.data.FirebaseInstance
import com.softyorch.datastorebasic.databinding.ActivityMainBinding
import com.softyorch.datastorebasic.databinding.DialogAddTaskBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)

        setUi()
        setupListeners()
    }

    private fun setUi() {
        todoAdapter = TodoAdapter { action, ref ->
            when (action) {
                Actions.DELETE -> firebaseInstance.removeFromDatabase(ref)
                Actions.DONE -> firebaseInstance.updateFromDatabase(ref)
            }

        }
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnAddTask -> {
                showDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val binding = DialogAddTaskBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        binding.btnAddTask.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "You can't leave empty fields", Toast.LENGTH_LONG).show()
            } else {
                firebaseInstance.writeOnFirebase(title, description)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setupListeners() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = getCleanSnapshot(snapshot)
                todoAdapter.setNewList(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Realtime onCanceled", error.details)
            }
        }

        firebaseInstance.setupDatabaseListener(postListener)
    }


    private fun getCleanSnapshot(snapshot: DataSnapshot): List<Pair<String, Todo>> {
        return snapshot.children.map { item ->
            Pair(item.key!!, item.getValue(Todo::class.java)!!)
        }
    }
}