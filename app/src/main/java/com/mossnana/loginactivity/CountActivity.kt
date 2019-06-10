package com.mossnana.loginactivity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_count.*
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CountActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    val TAG = "CountActivity"

    var lastWinner: String = ""

    var serveState: String = ""

    var currentPointLeft: Int = 0
    var currentPointRight: Int = 0

    var playerAinLeft: String = ""
    var playerBinLeft: String = ""
    var playerAinRight: String = ""
    var playerBinRight: String = ""

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_count)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        val getIntent = getIntent()

        val getInfo = Almab().getUid()

        playerAinLeft = getIntent().getStringExtra("playerA").toString()
        playerBinLeft = getIntent().getStringExtra("playerB").toString()
        playerAinRight = getIntent().getStringExtra("playerC").toString()
        playerBinRight = getIntent().getStringExtra("playerD").toString()

        lastWinner = getIntent().getStringExtra("lastWinner").toString()

        pointLeft.setOnClickListener {
            currentPointLeft = pointLeft.text.toString().toInt()
            pointLeft.setText((currentPointLeft+1).toString())
            setState(true, currentPointLeft, currentPointRight)
            Toast.makeText(this, "Next Serve: ${serveState}", Toast.LENGTH_SHORT).show()
        }

        pointLeft.setOnLongClickListener{
            currentPointLeft = pointLeft.text.toString().toInt()
            if(currentPointLeft > 0) {
                pointLeft.setText((currentPointLeft-1).toString())
            }
            setState(false, currentPointLeft, currentPointRight)
            Toast.makeText(this, "Next Serve: ${serveState}", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        pointRight.setOnClickListener {
            currentPointRight = pointRight.text.toString().toInt()
            pointRight.setText((currentPointRight+1).toString())
            setState(false, currentPointLeft, currentPointRight)
            Toast.makeText(this, "Next Serve: ${serveState}", Toast.LENGTH_SHORT).show()
        }

        pointRight.setOnLongClickListener{
            currentPointRight = pointRight.text.toString().toInt()
            if(currentPointRight > 0) {
                pointRight.setText((currentPointRight - 1).toString())
            }
            setState(true, currentPointLeft, currentPointRight)
            Toast.makeText(this, "Next Serve: ${serveState}", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        btnReset.setOnClickListener {
            val resetScore: Int = 0
            pointLeft.setText((resetScore).toString())
            pointRight.setText((resetScore).toString())
            Toast.makeText(this, "Scores had reset.", Toast.LENGTH_SHORT).show()
        }

        btnSavePoint.setOnClickListener {
            var matchId: String = getTimeStamp()
            var leftTeamPoint: String = pointLeft.text.toString()
            var leftTeamName: String = getIntent.getStringExtra("leftTeamName")
            var leftTeamPlayerA: String = getIntent.getStringExtra("playerA")
            var leftTeamPlayerB: String = getIntent.getStringExtra("playerB")
            var rightTeamPoint: String = pointRight.text.toString()
            var rightTeamName: String = getIntent.getStringExtra("rightTeamName")
            var rightTeamPlayerA: String = getIntent.getStringExtra("playerC")
            var rightTeamPlayerB: String = getIntent.getStringExtra("playerD")
            var createBy: String = getInfo

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
                rightTeamPlayerB,
                createBy
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeStamp(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = current.format(formatter).toString()
        return formatted
    }

    private fun writeNewPost(matchId: String, leftTeamPoint: String, leftTeamName: String, leftTeamPlayerA: String, leftTeamPlayerB: String, rightTeamPoint: String, rightTeamName: String, rightTeamPlayerA: String, rightTeamPlayerB: String, createBy: String) {
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
            rightTeamPlayerB,
            createBy
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
                Toast.makeText(this, "Failed to Submit Point", Toast.LENGTH_SHORT).show()
            }
    }

    fun setState(isLeft: Boolean, clp: Int, crp: Int) {
        if(isLeft) {
            if(clp + crp !== 0) {
                if(clp % 2 === 0) {
                    serveState = playerBinLeft
                    lastWinner = "left"
                    return@setState
                } else {
                    serveState = playerAinLeft
                    lastWinner = "left"
                    return@setState
                }
            } else {
                if(lastWinner === "left") {
                    serveState = playerBinLeft
                    return@setState
                }
            }
        } else {
            if(clp + crp !== 0) {
                if(crp % 2 === 0) {
                    serveState = playerAinRight
                    lastWinner = "right"
                    return@setState
                } else {
                    serveState = playerBinRight
                    lastWinner = "right"
                    return@setState
                }
            } else {
                if(lastWinner === "right") {
                    serveState = playerAinRight
                    return@setState
                }
            }
        }
    }
}

