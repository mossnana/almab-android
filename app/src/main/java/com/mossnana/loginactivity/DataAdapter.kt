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

        Picasso.get().load("https://cdn2.vectorstock.com/i/1000x1000/26/66/profile-icon-member-society-group-avatar-vector-18572666.jpg")
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .into(p0.imageTeamLeft)
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var matchId: TextView = itemView.findViewById(R.id.matchId)
    var imageTeamLeft: ImageView

    init {
        imageTeamLeft = itemView.findViewById(R.id.imageTeamLeft)
    }
}
