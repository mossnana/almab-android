package com.mossnana.loginactivity

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class Almab {

    constructor()

    val user = FirebaseAuth.getInstance().currentUser

    fun checkUser() {
        val TAG = "Check User"

        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            val uid = user.uid
            Log.d(TAG, "Current Name: $name")
            Log.d(TAG, "Current email: $email")
            Log.d(TAG, "Current Photo: $photoUrl")
            Log.d(TAG, "Current User ID: $uid")
        }
    }

    fun getUid(): String {
        val uid = user!!.uid
        return uid
    }
}