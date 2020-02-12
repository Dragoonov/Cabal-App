package com.cabal.app.after_register_mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.cabal.app.MyApplication
import com.cabal.app.R
import com.cabal.app.database.entities.User
import com.cabal.app.di.ViewModelFactory
import com.cabal.app.hobbies_edit_list.Hobbies
import com.cabal.app.navigation_bar.UserActivity
import com.cabal.app.utils.ImageManager
import com.cabal.app.utils.UserManager
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AfterRegisterActivity : AppCompatActivity() {

    val GET_FROM_GALLERY = 1
    private val INITIAL_RADIUS = 5
    private val TAG = "AfterRegisterActivity"
    var afterRegisterAccept: Button? = null
    var setAvatarButton: Button? = null
    var avatarImage: ImageView? = null
    var nickname: EditText? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var userManager: UserManager

    private lateinit var viewModel: AfterRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_register)
        if (savedInstanceState == null) Hobbies.initializeHobbies(this)
        nickname = findViewById(R.id.nicknameAdd)
        afterRegisterAccept = findViewById(R.id.afterRegisterAccept)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AfterRegisterViewModel::class.java)
        afterRegisterAccept?.setOnClickListener {
            viewModel.updateUser(User(
                    userManager.loggedUser!!.id,
                    nickname.toString(),
                    userManager.loggedUser!!.email,
                    viewModel.avatarString?.value,
                    INITIAL_RADIUS,
                    DoubleArray(2),
                    ArrayList(),
                    true
            ))
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        startActivity(Intent(this, UserActivity::class.java))
                        finish()
                    }, {
                        Log.v(TAG, "Sending AfterRegisterData failed: " + it.localizedMessage)
                    })
        }
        avatarImage = findViewById(R.id.avatarImage)
        viewModel.avatarString?.observe(this, Observer<String> {
            Glide.with(applicationContext)
                    .asBitmap()
                    .load(ImageManager.convertStringToBitmap(it))
                    .into(avatarImage!!)
        })
        viewModel.loadDefaultImage()
        avatarImage?.setOnClickListener { fireAvatarPick() }
        setAvatarButton = findViewById(R.id.setAvatarButton)
        setAvatarButton?.setOnClickListener { fireAvatarPick() }
        supportActionBar?.hide()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_FROM_GALLERY &&
                resultCode == Activity.RESULT_OK) {
            viewModel.loadSelectedImage(data?.data!!)
        }
    }

    private fun fireAvatarPick() {
        startActivityForResult(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                GET_FROM_GALLERY)
    }

}