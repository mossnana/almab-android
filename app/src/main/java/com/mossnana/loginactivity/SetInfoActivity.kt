package com.mossnana.loginactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_set_info.*

class SetInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_info)
        setTitle("Set Infomation Before Start")
        var lastWinner: String = ""
        btnStart.setOnClickListener {
            if(leftTeamBefore.isChecked()) {
                lastWinner = "left"
            } else {
                lastWinner = "right"
            }
            val intent = Intent(this, CountActivity::class.java)
            intent.putExtra("leftTeamName", leftTeamName.text.toString())
            intent.putExtra("rightTeamName", rightTeamName.text.toString())
            intent.putExtra("playerA", playerA.text.toString())
            intent.putExtra("playerB", playerB.text.toString())
            intent.putExtra("playerC", playerC.text.toString())
            intent.putExtra("playerD", playerD.text.toString())
            intent.putExtra("lastWinner", lastWinner.toString())
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
