package com.softyorch.datastorebasic.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseInstance(context: Context) {

    private val database = Firebase.database

    init {
        FirebaseApp.initializeApp(context)
    }
}