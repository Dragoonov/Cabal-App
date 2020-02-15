package com.cabal.app.dialogs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cabal.app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogFragment : DialogFragment() {

    private lateinit var listener: AlertDialogListener

    interface AlertDialogListener {
        fun onDialogPositiveClick(dialogFragment: DialogFragment)
        fun onDialogNegativeClick(dialogFragment: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = MaterialAlertDialogBuilder(activity)
            .also {
            it.setMessage(R.string.ensure_quit)
            it.setPositiveButton(R.string.accept) { _, _ -> listener.onDialogPositiveClick(this)}
            it.setNegativeButton(R.string.reject) { _, _ -> listener.onDialogNegativeClick(this)}
        }.create()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AlertDialogListener
    }
}