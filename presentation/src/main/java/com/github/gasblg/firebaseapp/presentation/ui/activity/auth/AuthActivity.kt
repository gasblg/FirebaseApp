package com.github.gasblg.firebaseapp.presentation.ui.activity.auth

import android.content.Intent
import android.os.Bundle

import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.app.Activity
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.gasblg.firebaseapp.analytics.manager.AnalyticsManager
import com.github.gasblg.firebaseapp.domain.usecases.date.DateConverterUseCase
import com.github.gasblg.firebaseapp.analytics.model.InfoModel
import com.github.gasblg.firebaseapp.presentation.R
import com.github.gasblg.firebaseapp.presentation.Transitions
import com.github.gasblg.firebaseapp.presentation.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var authInstance: FirebaseAuth

    @Inject
    lateinit var analytics: AnalyticsManager

    @Inject
    lateinit var dateConverterUseCase: DateConverterUseCase


    private lateinit var binding: ActivityAuthBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            startActivity(Transitions.startMain(this@AuthActivity))
        }


    private val activityResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                showToast()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeProfile()
        bindClicks()
    }


    private fun bindClicks() {
        binding.googleButton.setOnClickListener {
            signIn()
        }
    }
    private fun checkVersionForNotifications() {
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startActivity(Transitions.startMain(this@AuthActivity))
        }
    }

    private fun signIn() {
        activityResult.launch(googleSignInClient.signInIntent)
    }

    private fun observeProfile() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveProfile.collect {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (it && user != null) {
                        analytics.login()
                        checkVersionForNotifications()
                    }
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        authInstance.signInWithCredential(credential).addOnCompleteListener {
            val firebaseUser = it.result.user
            if (it.isSuccessful) {
                firebaseUser?.let { user ->
                    sendEvent(user)
                    viewModel.setUser(user)
                }
            } else {
                showToast()
            }
        }
    }

    private fun sendEvent(user: FirebaseUser) {
        analytics.identify(
            InfoModel(
                user.uid,
                dateConverterUseCase.invoke(user.metadata?.creationTimestamp),
                dateConverterUseCase.invoke(user.metadata?.lastSignInTimestamp)
            )
        )
    }

    private fun showToast() {
        Toast.makeText(this, getString(R.string.sign_in_failed), Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Transitions.startMain(this@AuthActivity))
            finish()
        }
    }

}

