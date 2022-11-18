package com.capstone.parentmind.view.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivityLoginBinding
import com.capstone.parentmind.utils.checkEmailPattern
import com.capstone.parentmind.view.home.HomeActivity
import com.capstone.parentmind.view.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
   private var _binding : ActivityLoginBinding? = null
   private val binding get()= _binding!!

   private lateinit var googleSignInClient: GoogleSignInClient
   private lateinit var auth: FirebaseAuth

   private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
         val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
         try {
            val account = task.getResult(ApiException::class.java)
            Log.d(TAG, getString(R.string.firebase_auth_google, account.id))
            account.idToken?.let { firebaseAuthWithGoogle(it) }
         } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
         }
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = ActivityLoginBinding.inflate(layoutInflater)
      setContentView(binding.root)

      // Configure Google Sign In
      val gso = GoogleSignInOptions
         .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         .requestIdToken(getString(R.string.default_web_client_id))
         .requestEmail()
         .build()

      googleSignInClient = GoogleSignIn.getClient(this, gso)

      // Initialize Firebase Auth
      auth = Firebase.auth

      setupView()
      setupAction()
   }

   override fun onStart() {
      super.onStart()
      // Check if user is signed in (non-null) and update UI accordingly.
      val currentUser = auth.currentUser
      updateUI(currentUser)
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupAction() {
      binding.ivGoogleLogo.setOnClickListener {
         googleLogin()
      }

      binding.tvSignupButton.setOnClickListener {
         val registerIntent = Intent(this, RegisterActivity::class.java)
         startActivity(registerIntent)
      }
   }

   private fun setupView() {
      checkEmailInput()
      checkPasswordInput()
   }

   private fun googleLogin() {
      val loginIntent = googleSignInClient.signInIntent
      resultLauncher.launch(loginIntent)
   }

   private fun firebaseAuthWithGoogle(idToken: String) {
      val credential = GoogleAuthProvider.getCredential(idToken, null)
      auth.signInWithCredential(credential)
         .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
               Log.d(TAG, "signInWithCredential: success")
               val user = auth.currentUser
               updateUI(user)
            } else {
               Log.w(TAG, "signInWithCredential: failure ", task.exception )
               updateUI(null)
            }
         }
   }

   private fun updateUI(currentUser: FirebaseUser?) {
      if (currentUser != null) {
         val homeIntent = Intent(this, HomeActivity::class.java)
         startActivity(homeIntent)
         finish()
      }
   }

   private fun checkEmailInput() {
      val inputLayout = binding.emailInputLayout
      val editText = inputLayout.editText
      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            if (checkEmailPattern(s.toString())) {
               inputLayout.isErrorEnabled = false
               inputLayout.error = null
               setButtonStatus()
            } else {
               inputLayout.isErrorEnabled = true
               inputLayout.error = getString(R.string.input_valid_email_error)
               setButtonStatus()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun checkPasswordInput() {
      val inputLayout = binding.passwordInputLayout
      val editText = inputLayout.editText
      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            setButtonStatus()
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun setButtonStatus() {
      val isInputEmailNull = binding.emailEditText.text.isNullOrEmpty()
      val isEmailError = binding.emailInputLayout.isErrorEnabled
      val isInputPasswordNull = binding.passwordEditText.text.isNullOrEmpty()
      val isPasswordError = binding.passwordInputLayout.isErrorEnabled

      binding.btnLogin.isEnabled =
         (!isInputEmailNull && !isInputPasswordNull && !isEmailError && !isPasswordError)
   }

   companion object {
      private const val TAG = "LoginActivity"
   }
}