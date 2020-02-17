package com.cabal.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cabal.app.utils.JsonLoader

class RatingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        val users = view.findViewById<RecyclerView>(R.id.recycleUsers)
        view.findViewById<Button>(R.id.rateOk).setOnClickListener {
            activity?.supportFragmentManager?.run {
                beginTransaction()
                        .remove(this@RatingFragment)
                        .commit()
                popBackStack()
            }
        }
        users.run {
            layoutManager = LinearLayoutManager(context)
            adapter = RatingAdapter(JsonLoader.loadUsers(context))
        }
        return view
    }
}