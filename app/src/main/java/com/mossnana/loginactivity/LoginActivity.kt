package com.mossnana.loginactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("Login")

        val debug = Almab()
        debug.checkUser()

        btnLogin.setOnClickListener {
            action_validate()
        }

        btnRegister.setOnClickListener {
            val debug = Almab()
            debug.checkUser()
            action_register()
        }
    }

    private fun action_validate() {
        val email = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        var databaseReference = FirebaseDatabase.getInstance().reference

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Enter your Email and Password !!!", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in ${it.result}")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("uid", it.result?.user?.uid)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun action_register() {
        val email = txtUsername.text.toString()
        val intent = Intent(this, RegisterActivity::class.java)

        intent.putExtra("email",email)
        startActivity(intent)
    }
}
