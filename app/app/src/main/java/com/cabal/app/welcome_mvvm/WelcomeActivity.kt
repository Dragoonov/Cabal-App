package com.cabal.app.welcome_mvvm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cabal.app.AfterRegisterActivity
import com.cabal.app.R
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User
import com.cabal.app.navigation_bar.UserActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var explanation: TextView? = null
    var signInButton: SignInButton? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var viewModel: WelcomeViewModel? = null
    private val compositeDisposable = CompositeDisposable()

    companion object {
        private const val TAG = "WelcomeActivity"
        private const val REQUEST_CODE = 123
        private const val RC_SIGN_IN = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        viewModel = WelcomeViewModel(application)
        explanation = findViewById(R.id.explanation)
        showCoordinatesExplanation(false)
        signInButton = findViewById(R.id.sign_in_button)
        signInButton?.setSize(SignInButton.SIZE_STANDARD)
        signInButton?.setOnClickListener(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkPermission()
        supportActionBar?.hide()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.shortcut)?.setOnClickListener {
            viewModel?.onLoginFinished()
            goToAfterRegister()
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            viewModel!!.checkIfUserLoggedIn(account.id!!)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it) {
                            goToUserActivity()
                            Log.v(TAG,"Success!")
                        }
                    }, {
                        Log.e(TAG, it.localizedMessage!!)
                    })
        }
    }

    fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.sign_in_button) {
            signIn()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val account = completedTask.getResult(ApiException::class.java)
        compositeDisposable.add(viewModel?.getUserById(account?.id!!)
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({
                    goToUserActivity()
                }, {
                    Log.e(TAG, it.localizedMessage!! + ", ACCOUNT ID: " + account.id)
                    createUser(account.id!!)
                })!!)
    }

    private fun createUser(user: String = "errorAccount") {
        Log.v(TAG, "In createUser")
        compositeDisposable.add(viewModel?.createUser(User(
                user,
                "",
                "",
                "",
                0,
                DoubleArray(2),
                ArrayList(),
                true

        ))
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({
                    goToAfterRegister()
                }, {
                    Log.e(TAG, it.message!!)
                   // showLoginError()
                })!!)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE)
        } else {
            location
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location
                } else {
                    showCoordinatesExplanation(true)
                }
            }
        }
    }

    private val location: Unit
        get() {
            fusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener(this) { location: Location ->
                        viewModel?.saveCoordinates(Pair(location.latitude, location.longitude))
                    }.addOnFailureListener(this) { e: Exception -> Log.d(TAG, "onCreate: fail" + e.message) }
        }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        } catch (e: ApiException) {
            Log.v(TAG, e.message!!)
            createUser()
        }
    }

    fun showLoginError() = Toast.makeText(applicationContext, R.string.login_failed, Toast.LENGTH_SHORT).show()


    private fun goToAfterRegister() {
        startActivity(Intent(this, AfterRegisterActivity::class.java))
        finish()
    }

    private fun goToUserActivity() {
        startActivity(Intent(this, UserActivity::class.java))
        finish()
    }

    private fun showCoordinatesExplanation(show: Boolean) {
        explanation!!.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}