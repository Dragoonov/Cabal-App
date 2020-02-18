package com.cabal.app.navigation_bar.search_events

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabal.app.R
import com.cabal.app.database.entities.User
import java.util.stream.Collectors

class EventCardsAdapter(dataset: List<User>) : RecyclerView.Adapter<EventCardsAdapter.MyViewHolder>() {

    private val data: List<String?> = dataset.stream().map { it.nick }.collect(Collectors.toList())


    class MyViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.member_item, parent, false) as TextView)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
            let {holder.view.text = data[position] }
}