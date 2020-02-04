package com.apps.findmyjob.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.apps.findmyjob.R
import com.apps.findmyjob.util.APP_TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var db :FirebaseDatabase
    private lateinit var dbReference: DatabaseReference
    private lateinit var dbAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance()
        dbReference = db.reference.child("Users")
        dbAuth = FirebaseAuth.getInstance()

        buttonCreateAccount.setOnClickListener { button ->

            val firstName = editTextFirstName.text.trim().toString()
            val lastName = editTextLastName.text.trim().toString()
            val email = editTextEmail.text.trim().toString()
            val password = editTextPassword.text.trim().toString()
            val confirmPassword = editTextVerifyPassword.text.trim().toString()

            if (userDataValid(firstName, lastName, email, password, confirmPassword)) {
                dbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Log.d(APP_TAG, "register is successful")
                    } else {
                        Log.e(APP_TAG, "error while adding firebase user: ${task.exception}")
                    }
                }
            } else {
                Toast.makeText(button.context, "Заполните все поля", Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun userDataValid(firstName : String, lastName : String, email:String, password:String, confirmPassword:String) : Boolean {
        return firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty() &&
                password == confirmPassword
    }


}
