package com.mossnana.loginactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private val TAG = "AlmaB"
    private lateinit var response_data: MutableList<DataModel>
    private var dataAdapter: DataAdapter? = null

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Log.d(TAG, "Home Click")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                Log.d(TAG, "Dash Click")
                replaceFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Log.d(TAG, "Nav Click")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val debug = Almab()
        debug.checkUser()

        val getUid = getIntent()
        if(getUid.getStringExtra("uid") != null) {
            var uid = getUid.getStringExtra("uid").toString()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(GridLayoutManager(this, 1))


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("/matchs")
        response_data = mutableListOf()

        dataAdapter = DataAdapter(response_data as ArrayList<DataModel>)
        recyclerView!!.setAdapter(dataAdapter)
        bindingData()
    }

    private fun bindingData() {
        databaseReference!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                response_data!!.add(dataSnapshot.getValue(DataModel::class.java)!!)
                dataAdapter!!.notifyDataSetChanged()

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val getUid = getIntent()
        var uid: String
        if(getUid.getStringExtra("uid") != null) {
            uid = getUid.getStringExtra("uid").toString()
        } else {
            uid = ""
        }

        when (item?.itemId) {
            R.id.btnAddActivity -> {
                val intent = Intent(this, CountActivity::class.java)
                intent.putExtra("uid", uid)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
