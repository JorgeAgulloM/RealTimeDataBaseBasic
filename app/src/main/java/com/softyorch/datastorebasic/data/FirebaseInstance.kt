package com.softyorch.datastorebasic.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.softyorch.datastorebasic.Todo

class FirebaseInstance(context: Context) {

    private val database = Firebase.database
    private val myRef = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(title: String, description: String) {
        val newItem = myRef.push()
        newItem.setValue(Todo(title, description))
    }

    fun setupDatabaseListener(postListener: ValueEventListener) {
        database.reference.addValueEventListener(postListener)
    }

    fun removeFromDatabase(ref: String) {
        myRef.child(ref).removeValue()
    }

    fun updateFromDatabase(ref: String) {
        myRef.child(ref).child("done").setValue(true)
    }
}