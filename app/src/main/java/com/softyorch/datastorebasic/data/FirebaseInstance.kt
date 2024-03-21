package com.softyorch.datastorebasic.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.softyorch.datastorebasic.Todo
import kotlin.random.Random

class FirebaseInstance(context: Context) {

    private val database = Firebase.database
    private val myRef = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase() {
        val randomValue: String = Random.nextInt(1, 200).toString()
        val newItem = myRef.push()
        newItem.setValue(getGenericTodoTaskItem(randomValue))
    }

    fun setupDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    private fun getGenericTodoTaskItem(randomValue: String) =
        Todo("Task $randomValue", "This is a cream description", false)
}