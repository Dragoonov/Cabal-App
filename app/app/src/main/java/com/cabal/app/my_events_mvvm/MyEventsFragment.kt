package com.cabal.app.my_events_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cabal.app.AddEventFragment
import com.cabal.app.MyApplication
import com.cabal.app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MyEventsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory


    private lateinit var viewModel: MyEventsViewModel

    private lateinit var adapter: MyEventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = (activity?.application as MyApplication)
        application.appComponent.inject(this)
        adapter = MyEventsAdapter(ArrayList(), application)
        viewModel = ViewModelProviders.of(this, factory)[MyEventsViewModel::class.java]
        viewModel.events.observe(this, Observer {
            adapter.filteredEvents = it
            adapter.originalEvents = it
            adapter.notifyDataSetChanged()
        })

        val view = inflater.inflate(R.layout.fragment_my_events, container, false)
        view.findViewById<FloatingActionButton>(R.id.btnAddEvent).setOnClickListener {
                        activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, AddEventFragment())
                    .addToBackStack(null)
                    .commit()
        }

        view.findViewById<CheckBox>(R.id.authorSwitch).setOnCheckedChangeListener { _, checked ->
            adapter.filters.AUTHOR_FILTER = checked
            adapter.filter.filter("")
        }
        view.findViewById<CheckBox>(R.id.guestSwitch).setOnCheckedChangeListener { _, checked ->
            adapter.filters.GUEST_FILTER = checked
            adapter.filter.filter("")
        }
        view.findViewById<CheckBox>(R.id.finishedSwitch).setOnCheckedChangeListener { _, checked ->
            adapter.filters.FINISHED_FILTER = checked
            adapter.filter.filter("")
        }
        view!!.findViewById<RecyclerView>(R.id.recycleMyEvents).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        return view
    }
}