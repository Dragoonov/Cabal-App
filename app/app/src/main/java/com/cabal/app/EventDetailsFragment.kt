package com.cabal.app


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cabal.app.models.EventModel

/**
 * A simple [Fragment] subclass.
 */
class EventDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }

    companion object {
        fun newInstance(model: EventModel?) = EventDetailsFragment()
    }


}

