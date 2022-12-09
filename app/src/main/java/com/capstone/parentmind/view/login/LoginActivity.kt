package com.capstone.parentmind.view.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.capstone.parentmind.R
import com.capstone.parentmind.data.Result
import com.capstone.parentmind.data.remote.response.User
import com.capstone.parentmind.databinding.ActivityLoginBinding
import com.capstone.parentmind.model.UserFirebase
import com.capstone.parentmind.utils.checkEmailPattern
import com.capstone.parentmind.utils.makeToast
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
   private var _binding : ActivityLoginBinding? = null
   private val binding get() = _binding!!

   private val viewModel: LoginViewModel by viewModels()

   private lateinit var googleSignInClient: GoogleSignInClient
   private lateinit var auth: FirebaseAuth
   private lateinit var db: FirebaseDatabase
   private lateinit var userRef: DatabaseReference

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
      setTheme(R.style.Theme_ParentMind)
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

      // Initialize Firebase realtime database
      db = Firebase.database

      userRef = db.reference.child(USERS_CHILD)

      setupView()
      setupAction()
   }

   override fun onStart() {
      super.onStart()
      // Check if user is signed in (non-null) and update UI accordingly.
      var currentUser = auth.currentUser != null
      viewModel.isLogin().observe(this) { isLogin ->
         if (isLogin) {
            currentUser = currentUser && isLogin
         }
      }
      updateUI(currentUser)
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupAction() {
      binding.ivGoogleLogo.setOnClickListener {
         hideKeyboard()
         showLoading(true)
         googleLogin()
      }

      binding.btnLogin.setOnClickListener {
         hideKeyboard()
         showLoading(true)

         val email = binding.emailEditText.text.toString()
         val password = binding.passwordEditText.text.toString()

         viewModel.login(email, password).observe(this) {
            it?.let { result ->
               when (result) {
                  is Result.Loading -> {
                     showLoading(true)
                  }
                  is Result.Success -> {
                     if (result.data.status) {
                        firebaseAuthWithEmail(email, password, result.data.user)
                     } else {
                        showLoading(false)
                        makeToast(this, "Login gagal, ${result.data.message}")
                     }
                  }
                  is Result.Error -> {
                     showLoading(false)
                     makeToast(this, "Terjadi error, ${result.error}")
                  }
               }
            }
         }
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

               user?.let {
                  viewModel.loginFB(user.displayName!!, user.email!!, user.uid).observe(this) {
                     it?.let { result ->
                        when (result) {
                           is Result.Loading -> showLoading(true)
                           is Result.Success -> {
                              showLoading(false)
                              if (result.data.status) {
                                 makeToast(this, "Login berhasil, selamat datang ${result.data.user.name}")
                                 checkUserExistInFirebase(user.email!!, result.data.user)
                                 updateUI(true)
                              } else {
                                 makeToast(this, "Login gagal, ${result.data.message}")
                              }
                           }
                           is Result.Error -> {
                              showLoading(false)
                              makeToast(this, "Terjadi error, ${result.error}")
                           }
                        }
                     }
                  }
               }
            } else {
               Log.w(TAG, "signInWithCredential: failure ", task.exception)
               updateUI(false)
            }
         }
   }

   private fun firebaseAuthWithEmail(email: String, password: String, user: User) {
      auth.signInWithEmailAndPassword(email, password)
         .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
               showLoading(false)
               checkUserExistInFirebase(email, user)
               // Sign in success, update UI with the signed-in user's information
               Log.d(TAG, "signInWithEmail:success")
               val userAuth = auth.currentUser
               makeToast(this, "Login berhasil, selamat datang ${user.name}")
               updateUI(userAuth != null)
            } else {
               showLoading(false)
               // If sign in fails, display a message to the user.
               Log.w(TAG, "signInWithEmail:failure", task.exception)
//               makeToast(this, "Login gagal, ${task.exception?.message}")
               updateUI(false)
            }
         }
   }

   private fun checkUserExistInFirebase(email: String, user: User) {
      userRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
         override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) {
               addAuthUserToDatabase(user, auth.currentUser!!)
            }
         }

         override fun onCancelled(error: DatabaseError) {
         }
      })
   }

   private fun addAuthUserToDatabase(userBackend: User, userFirebase: FirebaseUser) {
      val user = UserFirebase(
         id_backend = userBackend.id,
         email = userFirebase.email,
         name = userBackend.name
      )

      userRef.child(userFirebase.uid).setValue(user)
   }

   private fun updateUI(isLogin: Boolean) {
      if (isLogin) {
         val homeIntent = Intent(this, HomeActivity::class.java)
         homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
            if (s.toString().length >= 6) {
               inputLayout.isErrorEnabled = false
               inputLayout.error = null
               setButtonStatus()
            } else {
               inputLayout.isErrorEnabled = true
               inputLayout.error = "Password minimal harus berisi 6 karakter"
               setButtonStatus()
            }
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

   private fun showLoading(isLoading: Boolean) {
      if (isLoading) {
         binding.viewLoading.root.visibility = View.VISIBLE
      } else {
         binding.viewLoading.root.visibility = View.GONE
      }
   }

   private fun hideKeyboard() {
      val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
      val windowHeightMethod = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight")
      val height = windowHeightMethod.invoke(imm) as Int
      @Suppress("DEPRECATION")
      if(height > 0) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
   }

   companion object {
      private const val TAG = "LoginActivity"
      const val USERS_CHILD = "users"
   }
}