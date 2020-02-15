package com.cabal.app.hobbies_edit_list

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cabal.app.R
import com.cabal.app.database.entities.Hobby
import com.cabal.app.models.HobbyTypeModel

object Hobbies {

    private val switchesState = LinkedHashMap<Hobby, Boolean>()
    private var hobbyTypeModels: List<HobbyTypeModel>? = null
    private val TAG = "Hobbies"

    fun initializeHobbies(activity: Activity) {
        val linearView : LinearLayout? = activity.findViewById(R.id.hobbiesContainer)
        if (hobbyTypeModels == null) {
            hobbyTypeModels = ArrayList()
            loadHobbies(activity, hobbyTypeModels, linearView!!)
        }
        else {
            hobbyTypeModels?.forEach { linearView?.addView(initializeHobbyTypeView(it, linearView)) }
        }
    }

    fun loadHobbies(context: Context, hobbyTypeModels: List<HobbyTypeModel>?, linearLayout: LinearLayout): List<HobbyTypeModel>? = hobbyTypeModels

    fun getCheckedIds() = switchesState.keys.stream()
            .filter { switchesState[it]!!}
            .map{it.name}

    private fun initializeHobbyTypeView(model: HobbyTypeModel, parent: LinearLayout): View {
        val hobbyTypeView = LayoutInflater.from(parent.context).inflate(R.layout.hobby_type_item, parent, false)
        hobbyTypeView.findViewById<TextView>(R.id.hobbyTypeTitle).text = model.name
        val hobbies = hobbyTypeView.findViewById<LinearLayout>(R.id.subItem)
        model.hobbies.forEach { hobbies.addView(initializeHobbyView(it, hobbies)) }
        setHobbiesListVisibility(hobbies, model.isExpanded)
        setArrowsVisibility(hobbyTypeView, model.isExpanded)
        hobbyTypeView.setOnClickListener{
            val expanded = model.isExpanded
            model.isExpanded = !expanded
            setHobbiesListVisibility(hobbies, model.isExpanded)
            setArrowsVisibility(it, model.isExpanded)
        }
        return hobbyTypeView
    }

    private fun initializeHobbyView(model: Hobby, parent: LinearLayout): View {
        val hobbyView = LayoutInflater.from(parent.context).inflate(R.layout.hobby_item, parent, false)
        hobbyView.findViewById<TextView>(R.id.nameHobby).text = model.name
        Glide.with(hobbyView.context).load(model.description).into(hobbyView.findViewById(R.id.photoHobby))
        val switch = hobbyView.findViewById<Switch>(R.id.switchHobby)
        hobbyView.setOnClickListener{
            switch.isChecked = !switch.isChecked
            switchesState[model] = switch.isChecked
        }
        val switchState = switchesState.keys.stream().anyMatch{ it.name == model.name }
        switch.isChecked = switchState
        return hobbyView
    }

    private fun setHobbiesListVisibility(linearLayout: LinearLayout, visible: Boolean) {
        linearLayout.visibility = if (visible) VISIBLE else GONE
    }

    private fun setArrowsVisibility(view: View, visible: Boolean) {
        val arrowDown = view.findViewById<ImageView>(R.id.iconArrowDown)
        val arrowUp = view.findViewById<ImageView>(R.id.iconArrowUp)
        arrowUp.visibility = if (visible) VISIBLE else GONE
        arrowDown.visibility = if (visible) VISIBLE else GONE
    }
}