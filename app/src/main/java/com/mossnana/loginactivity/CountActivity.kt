package com.mossnana.loginactivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_count.*
import java.security.MessageDigest

class CountActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference


    val TAG = "CountActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_count)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        pointLeft.setOnClickListener {
            var currentPointLeft = pointLeft.text.toString().toInt()
            pointLeft.setText((currentPointLeft+1).toString())
        }

        pointRight.setOnClickListener {
            var currentPointRight = pointRight.text.toString().toInt()
            pointRight.setText((currentPointRight+1).toString())
        }

        btnReset.setOnClickListener {
            val resetScore: Int = 0
            pointLeft.setText((resetScore).toString())
            pointRight.setText((resetScore).toString())
        }

        btnSavePoint.setOnClickListener {

            var matchId: String = hash()
            var leftTeamPoint: String = pointLeft.text.toString()
            var leftTeamName: String = "Team A"
            var leftTeamPlayerA: String = "Player 1"
            var leftTeamPlayerB: String = "Player 2"
            var rightTeamPoint: String = pointRight.text.toString()
            var rightTeamName: String = "Team B"
            var rightTeamPlayerA: String = "Player 3"
            var rightTeamPlayerB: String = "Player 4"

            database = FirebaseDatabase.getInstance().reference

            writeNewPost(
                matchId,
                leftTeamPoint,
                leftTeamName,
                leftTeamPlayerA,
                leftTeamPlayerB,
                rightTeamPoint,
                rightTeamName,
                rightTeamPlayerA,
                rightTeamPlayerB
            )
        }
    }

    fun hash(): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val text = (1..32)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        val bytes = text.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        var sha256: String = ""
        for (byte in digest){
            sha256 += "%02x".format(byte).toString()
        }
        return sha256
    }


    private fun writeNewPost(matchId: String, leftTeamPoint: String, leftTeamName: String, leftTeamPlayerA: String, leftTeamPlayerB: String, rightTeamPoint: String, rightTeamName: String, rightTeamPlayerA: String, rightTeamPlayerB: String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        val key = FirebaseDatabase.getInstance().reference.child("matchs").push().key

        val matchs = Matchs(
            matchId,
            leftTeamPoint,
            leftTeamName,
            leftTeamPlayerA,
            leftTeamPlayerB,
            rightTeamPoint,
            rightTeamName,
            rightTeamPlayerA,
            rightTeamPlayerB
        )
        val postValues = matchs.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/matchs/$key"] = postValues

        FirebaseDatabase.getInstance().reference.updateChildren(childUpdates)
            .addOnSuccessListener{
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {

            }
    }

    fun gotoNewsFeed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
