package com.softyorch.datastorebasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun setUi() {
        binding.btnId.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
    }
}