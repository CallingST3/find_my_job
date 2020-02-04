package com.apps.findmyjob.repository

import android.util.Log
import com.apps.findmyjob.util.APP_TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DatabaseRepository private constructor(){

    private var db:FirebaseDatabase? = null
    private lateinit var dbReference:DatabaseReference
    private lateinit var dbAuth:FirebaseAuth

    init {
        val db = FirebaseDatabase.getInstance()
        val dbReference = db.reference.child("Users")
        val dbAuth = FirebaseAuth.getInstance()
    }



    fun createUserWithEmailPassword(email: String, password: String) {
        dbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(APP_TAG, "register is successful")
            } else {
                Log.e(APP_TAG, "error while adding firebase user: ${task.exception}")
            }
        }
    }

}