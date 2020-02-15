package com.cabal.app.dialogs

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cabal.app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FilterEventsDialogFragment: DialogFragment() {

    private lateinit var listener: AlertDialogListener

    var eventName: String? = null

    interface AlertDialogListener {
        fun onDialogPositiveClick(dialogFragment: FilterEventsDialogFragment)
        fun onDialogNegativeClick(dialogFragment: FilterEventsDialogFragment)
    }

    companion object {
        fun newInstance(eventName: String, listener: AlertDialogListener) =
            FilterEventsDialogFragment().also { fragment ->
                fragment.arguments = Bundle().also { it.putString("name", eventName) }
                fragment.listener = listener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = MaterialAlertDialogBuilder(activity)
    .also {
        it.setMessage(getString(R.string.filterResult,eventName))
        it.setPositiveButton(R.string.accept) { _, _ -> listener.onDialogPositiveClick(this)}
        it.setNegativeButton(R.string.reject) { _, _ -> listener.onDialogNegativeClick(this)}
    }.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventName = arguments?.getString("name")
    }

}