package com.cabal.app.utils

import android.content.Context
import android.util.Log
import com.cabal.app.database.entities.Event
import com.cabal.app.database.entities.User
import com.google.gson.GsonBuilder
import org.json.JSONArray
import java.io.InputStream
import java.nio.charset.StandardCharsets

object JsonLoader {
    private const val TAG = "JsonLoader"
    private const val EVENTS_JSON_FILENAME = "events.json"
    private const val HOBBIES_JSON_FILENAME = "hobbies.json"
    private const val USERS_JSON_FILENAME = "users.json"

    fun loadEvents(context: Context?):  List<Event>?  {
            val builder = GsonBuilder()
            val gson = builder.create()
            val array = JSONArray(loadJSONFromAsset(context!!, EVENTS_JSON_FILENAME))
            val eventModelList = ArrayList<Event>()
//            for ( i in 0 until array.length()) {
//                val eventModel = Event(1,null,array.getString(i),null,)
//                val eventModel = gson.fromJson(array.getString(i), Event::class.java)
//                eventModelList.add(eventModel)
//            }
            return eventModelList
    }


//    fun loadHobbies (context: Context,hobbyTypeModels: List<HobbyTypeModel>): List<HobbyTypeModel> {
//        val builder = GsonBuilder()
//        val gson = builder.create()
//        return hobbyTypeModels
//        //TODO Finish this one?...
//    }
//
//    private fun loadHobbyTypeChildren(id: Int, model: HobbyTypeModel) {
//        val builder = GsonBuilder()
//        val gson = builder.create()
//        //TODO Finish this one?...
//    }


    fun loadUsers(context: Context?): List<User>  {
        val builder = GsonBuilder()
        val gson = builder.create()
        val array = JSONArray(loadJSONFromAsset(context!!, USERS_JSON_FILENAME))
        val userModels = ArrayList<User>()
        for ( i in 0 until array.length()) {
            val userModel = gson.fromJson(array.getString(i), User::class.java)
            userModels.add(userModel)
        }
        return userModels
    }

    private fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        val json: String
        val ist: InputStream
        val manager = context.assets
        Log.d(TAG, "path $jsonFileName")
        ist = manager.open(jsonFileName)
        val size = ist.available()
        val buffer = ByteArray(size)
        ist.read(buffer)
        ist.close()
        json = String(buffer, StandardCharsets.UTF_8)
        return json
    }
}