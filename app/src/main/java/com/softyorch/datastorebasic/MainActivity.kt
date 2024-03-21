package com.softyorch.datastorebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.softyorch.datastorebasic.data.FirebaseInstance
import com.softyorch.datastorebasic.databinding.ActivityMainBinding

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
        binding.btnId.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
        todoAdapter = TodoAdapter { ref ->
            firebaseInstance.removeFromDatabase(ref)
        }
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
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