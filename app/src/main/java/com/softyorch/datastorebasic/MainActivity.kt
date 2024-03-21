package com.softyorch.datastorebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.softyorch.datastorebasic.data.FirebaseInstance
import com.softyorch.datastorebasic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseInstance: FirebaseInstance
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
    }

    private fun setupListeners() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data: String? = snapshot.getValue<String>()

                data?.let {
                    binding.tvResult.text = it
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Realtime onCanceled", error.details)
            }
        }

        firebaseInstance.setupDatabaseListener(postListener)
    }
}