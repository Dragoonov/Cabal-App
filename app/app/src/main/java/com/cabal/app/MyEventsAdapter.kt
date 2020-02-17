package com.cabal.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cabal.app.database.entities.Event
import com.cabal.app.utils.Filters
import com.cabal.app.utils.UserManager

class MyEventsAdapter(events: List<Event>) : RecyclerView.Adapter<MyEventsAdapter.ViewHolder>(), Filterable {

    var originalEvents: List<Event> = events
    var filteredEvents: List<Event> = events

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_events_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = filteredEvents.size

    override fun getFilter(): Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults = FilterResults().also {
                val filteredList = Filters.performFiltering(originalEvents)
                it.count = filteredList.size
                it.values = filteredList
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) =
            let {
                filteredEvents = results?.values as List<Event>
                notifyDataSetChanged()
            }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val myEventName = view.findViewById<TextView>(R.id.myEventName)
        private val myEventDate = view.findViewById<TextView>(R.id.myEventDate)
        private val myEventLocation = view.findViewById<TextView>(R.id.myEventLocation)
        private val adminStar = view.findViewById<ImageView>(R.id.myEventAdminStar)
        private val myEventPicture = view.findViewById<ImageView>(R.id.myEventPicture)
        private val myEventRateButton = view.findViewById<Button>(R.id.myEventRateUsers)

        init {
            view.setOnClickListener {
                (it.context as AppCompatActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                EventDetailsFragment.newInstance(filteredEvents[adapterPosition]))
                        .addToBackStack(null)
                        .commit()
            }
            myEventRateButton.setOnClickListener {
                (it.context as AppCompatActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, RatingFragment())
                        .addToBackStack(null)
                        .commit()
            }
        }

        fun bind(position: Int) {
            val event = filteredEvents[position]
            myEventName.text = event.name
            myEventDate.text = event.date.toString()
            myEventLocation.text = event.location
            adminStar.visibility = if (UserManager().loggedUser?.nick == event.creator) View.VISIBLE else View.GONE
            Glide.with(myEventName.context).load(event.image).into(myEventPicture)
        }

    }
}

