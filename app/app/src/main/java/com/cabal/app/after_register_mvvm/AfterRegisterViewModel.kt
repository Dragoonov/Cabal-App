package com.cabal.app.after_register_mvvm

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cabal.app.R
import com.cabal.app.database.entities.User
import com.cabal.app.database.repository.Repository
import com.cabal.app.utils.ImageManager
import javax.inject.Inject

class AfterRegisterViewModel @Inject constructor(app: Application,
                             private val repository: Repository) : AndroidViewModel(app) {

    init {
        Log.v("AfterRegisterViewModel","INICJALIZACJA MODELU")
    }

    var avatarString: MutableLiveData<String>? = MutableLiveData("")

    fun updateUser(user: User) = repository.updateUser(user)

    fun loadDefaultImage() {
        if(avatarString?.value.equals("")) {
            val fileUri = Uri.parse("android.resource://com.cabal.app/" + R.drawable.default_avatar)
            val defaultImage = ImageManager.scaleImage(fileUri, getApplication<Application>().contentResolver, getApplication())
            avatarString?.value = ImageManager.convertBitmapToString(defaultImage)
        }
    }

    fun loadSelectedImage(selectedImage: Uri) {
        val resizedImage = ImageManager.scaleImage(selectedImage, getApplication<Application>().contentResolver, getApplication())
        avatarString?.value = ImageManager.convertBitmapToString(resizedImage)
    }

}