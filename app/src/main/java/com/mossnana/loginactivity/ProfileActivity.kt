package com.mossnana.loginactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        firebaseUser()

        btnEdit.setOnClickListener {
            val debug = Almab()
            val uid: String = debug.getUid()
            var profileName: String = txtName.text.toString()
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.getReference("/users/$uid")
            database = FirebaseDatabase.getInstance().reference
            database.child("users").child(uid).child("name").setValue(profileName)
            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseUser(): String {
        val debug = Almab()
        val uid: String = debug.getUid()
        var profileUrl: String = ""
        var profileName: String = ""
        var profileEmail: String = ""
        val txtName = findViewById(R.id.txtName) as EditText
        val txtEmail = findViewById(R.id.txtEmail) as EditText
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("/users/$uid")
        val database = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userInfo = dataSnapshot.getValue(User::class.java)
                profileUrl = userInfo!!.profileImageUrl.toString()
                profileName = userInfo!!.name.toString()
                profileEmail = userInfo!!.username.toString()
                Picasso.get().load(profileUrl)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView)
                txtName.setText(profileName)
                txtEmail.setText(profileEmail)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        databaseReference.addValueEventListener(database)
        return profileUrl
    }



    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, SetInfoActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener false
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }

}
