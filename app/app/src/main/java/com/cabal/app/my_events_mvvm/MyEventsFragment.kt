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
import com.cabal.app.MyEventsAdapter
import com.cabal.app.R
import com.cabal.app.utils.Filters
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MyEventsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory


    private lateinit var viewModel: MyEventsViewModel

    private val adapter: MyEventsAdapter = MyEventsAdapter(ArrayList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity?.application as MyApplication).appComponent.inject(this)
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
            Filters.AUTHOR_FILTER = checked
            adapter.filter.filter("")
        }
        view.findViewById<CheckBox>(R.id.authorSwitch).setOnCheckedChangeListener { _, checked ->
            Filters.GUEST_FILTER = checked
            adapter.filter.filter("")
        }
        view.findViewById<CheckBox>(R.id.authorSwitch).setOnCheckedChangeListener { _, checked ->
            Filters.FINISHED_FILTER = checked
            adapter.filter.filter("")
        }
        view!!.findViewById<RecyclerView>(R.id.recycleMyEvents).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        return view
    }
}