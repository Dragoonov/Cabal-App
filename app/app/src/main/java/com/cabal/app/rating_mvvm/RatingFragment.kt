package com.cabal.app.rating_mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cabal.app.MyApplication
import com.cabal.app.R
import com.cabal.app.database.entities.User
import com.cabal.app.di.ViewModelFactory
import com.cabal.app.utils.Converters
import com.cabal.app.utils.JsonLoader
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

fun Bundle.getUsersList() = Converters.fromStringToUserList(getString("usersJSON")!!)
fun Bundle.putUsersList(users: List<User>) = putString("usersJSON",Converters.fromUserList(users))

class RatingFragment : Fragment() {

    lateinit var usersList: List<User>

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: RatingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity?.application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this,factory)[RatingViewModel::class.java]
        viewModel.usersList = arguments!!.getUsersList()
        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        val users = view.findViewById<RecyclerView>(R.id.recycleUsers)
        view.findViewById<Button>(R.id.rateOk).setOnClickListener {
            viewModel.updateUsers()
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        activity?.supportFragmentManager?.run {
                            beginTransaction()
                                    .remove(this@RatingFragment)
                                    .commit()
                            popBackStack()
                    }
                        Log.e("RatingFragment", "Success posting users to database")
                    },{
                        Log.d("RatingFragment", it.localizedMessage!!)
                    })
            }

        users.run {
            layoutManager = LinearLayoutManager(context)
            adapter = RatingAdapter(viewModel)
        }
        return view
    }

    companion object {
        fun newInstance(users: List<User>): RatingFragment = RatingFragment().apply {
                arguments = Bundle().apply {
                    putUsersList(users)
                }
            }
        }
}
