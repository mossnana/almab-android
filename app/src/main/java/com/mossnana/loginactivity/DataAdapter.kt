package com.mossnana.loginactivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DataAdapter(val dataModelList: List<DataModel>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.news_feed,p0,false))
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val dataModel = dataModelList[p1]
        p0.matchId.text = dataModel.matchId
        p0.rightTeamPoint.text = dataModel.rightTeamPoint
        p0.leftTeamPoint.text = dataModel.leftTeamPoint
        p0.rightTeamName.text = dataModel.rightTeamName
        p0.leftTeamName.text = dataModel.leftTeamName

        var teamLeftPoint = dataModel.leftTeamPoint.toString().toInt()
        var teamRightPoint = dataModel.rightTeamPoint.toString().toInt()

        if(teamLeftPoint > teamRightPoint) {
            Picasso.get().load("https://i.imgur.com/jCGbLaf.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamLeft)

            Picasso.get().load("https://i.imgur.com/WDQaXz2.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamRight)
        } else if (teamLeftPoint < teamRightPoint) {
            Picasso.get().load("https://i.imgur.com/WDQaXz2.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamLeft)

            Picasso.get().load("https://i.imgur.com/jCGbLaf.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamRight)
        } else {
            Picasso.get().load("https://i.imgur.com/9xRKwXI.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamLeft)

            Picasso.get().load("https://i.imgur.com/9xRKwXI.png")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(p0.imageTeamRight)
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var matchId: TextView = itemView.findViewById(R.id.matchId)
    var rightTeamPoint: TextView = itemView.findViewById(R.id.rightTeamPoint)
    var leftTeamPoint: TextView = itemView.findViewById(R.id.leftTeamPoint)
    var leftTeamName: TextView = itemView.findViewById(R.id.leftTeamName)
    var rightTeamName: TextView = itemView.findViewById(R.id.rightTeamName)
    var imageTeamLeft: ImageView
    var imageTeamRight: ImageView

    init {
        imageTeamLeft = itemView.findViewById(R.id.imageTeamLeft)
        imageTeamRight = itemView.findViewById(R.id.imageTeamRight)
    }
}
