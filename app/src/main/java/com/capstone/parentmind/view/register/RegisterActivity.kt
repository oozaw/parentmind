package com.capstone.parentmind.view.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.capstone.parentmind.R
import com.capstone.parentmind.data.Result
import com.capstone.parentmind.databinding.ActivityRegisterBinding
import com.capstone.parentmind.utils.checkEmailPattern
import com.capstone.parentmind.utils.makeToast
import com.capstone.parentmind.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
   private var _binding: ActivityRegisterBinding? = null
   private val binding get() = _binding!!

   private lateinit var datePickerDialog: DatePickerDialog

   private val vIewModel: RegisterVIewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = ActivityRegisterBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setupView()
      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupView() {
      initDatePicker()
      binding.btnBirthdatePicker.hint = getTodayDate()

      // validation
      checkFullname()
      checkUsername()
      checkEmailInput()
      checkPasswordInput()
      checkPasswordConfirmInput()
   }

   private fun setupAction() {
      binding.btnBirthdatePicker.setOnClickListener {
         datePickerDialog.show()
      }

      binding.ivBackButton.setOnClickListener {
         @Suppress("DEPRECATION")
         super.onBackPressed()
      }

      binding.btnRegister.setOnClickListener {
         showLoading(true)
         hideKeyboard()

         val name = binding.fullnameEditText.text.toString()
         val username = binding.usernameEditText.text.toString()
         val email = binding.emailEditText.text.toString()
         val password = binding.passwordEditText.text.toString()

         vIewModel.register(name, username, email, password).observe(this) {
            it?.let { result ->
               when (result) {
                  is Result.Loading -> {
                     showLoading(true)
                  }
                  is Result.Success -> {
                     showLoading(false)
                     if (result.data.status) {
                        Intent(this, LoginActivity::class.java).also { intent ->
                           makeToast(this, "Register berhasil, silahkan login")
                           startActivity(intent)
                           finish()
                        }
                     } else {
                        makeToast(this, "Register gagal, ${result.data.message}")
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

      binding.tvLoginButton.setOnClickListener {
         val loginIntent = Intent(this, LoginActivity::class.java)
         startActivity(loginIntent)
      }
   }

   // DatePicker
   private fun getTodayDate(): String {
      val cal: Calendar = Calendar.getInstance()
      val year: Int = cal.get(Calendar.YEAR)
      var month: Int = cal.get(Calendar.MONTH)
      month += 1
      val day: Int = cal.get(Calendar.DAY_OF_MONTH)
      return makeDateString(day, month, year)
   }

   private fun initDatePicker() {
      val dateSetListener =
         OnDateSetListener { _, year, month, day ->
            var monthPicked = month
            monthPicked += 1
            val date: String = makeDateString(day, month, year)
            binding.btnBirthdatePicker.text = date
            setButtonStatus()
         }

      val cal = Calendar.getInstance()
      val year = cal[Calendar.YEAR]
      val month = cal[Calendar.MONTH]
      val day = cal[Calendar.DAY_OF_MONTH]

      @Suppress("DEPRECATION")
      val style: Int = AlertDialog.THEME_HOLO_LIGHT

      datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
   }

   private fun makeDateString(day: Int, month: Int, year: Int): String {
      return "$day " + getMonthFormat(month) + " " + year
   }

   private fun getMonthFormat(month: Int): String {
      if (month == 1) return "Januari"
      if (month == 2) return "Februari"
      if (month == 3) return "Maret"
      if (month == 4) return "April"
      if (month == 5) return "Mei"
      if (month == 6) return "Juni"
      if (month == 7) return "Juli"
      if (month == 8) return "Agustus"
      if (month == 9) return "September"
      if (month == 10) return "Oktober"
      if (month == 11) return "November"
      return if (month == 12) "Desember" else "Januari"
   }

   // validation
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
            val passwordConfirmValue = binding.passwordConfirmEditText.text.toString()

            if (passwordConfirmValue.isNotEmpty()) {
               if (s.toString() == passwordConfirmValue) {
                  binding.passwordConfirmInputLayout.isErrorEnabled = false
                  binding.passwordConfirmInputLayout.error = null
                  setButtonStatus()
               } else {
                  binding.passwordConfirmInputLayout.isErrorEnabled = true
                  binding.passwordConfirmInputLayout.error = "Kata sandi tidak sesuai"
                  setButtonStatus()
               }
            }

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

   private fun checkPasswordConfirmInput() {
      val inputLayout = binding.passwordConfirmInputLayout
      val editText = inputLayout.editText

      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            val passwordValue = binding.passwordEditText.text.toString()

            if (s.toString() == passwordValue) {
               inputLayout.isErrorEnabled = false
               inputLayout.error = null
               setButtonStatus()
            } else {
               inputLayout.isErrorEnabled = true
               inputLayout.error = "Kata sandi tidak sesuai"
               setButtonStatus()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun checkFullname() {
      val inputLayout = binding.fullnameInputLayout
      val editText = inputLayout.editText

      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            if (s.isNullOrEmpty()) {
               inputLayout.isErrorEnabled = true
               inputLayout.error = "Nama lengkap tidak boleh kosong"
               setButtonStatus()
            } else {
               inputLayout.isErrorEnabled = false
               inputLayout.error = null
               setButtonStatus()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun checkUsername() {
      val inputLayout = binding.usernameInputLayout
      val editText = inputLayout.editText

      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

            if (s.toString().length < 6) {
               inputLayout.isErrorEnabled = true
               inputLayout.error = "Username minimal harus berisi 6 karakter"
               setButtonStatus()
            } else {
               inputLayout.isErrorEnabled = false
               inputLayout.error = null
               setButtonStatus()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun setButtonStatus() {
      val isFullnameNull = binding.fullnameEditText.text.isNullOrEmpty()
      val isFullnameError = binding.fullnameInputLayout.isErrorEnabled
      val isEmailNull = binding.emailEditText.text.isNullOrEmpty()
      val isEmailError = binding.emailInputLayout.isErrorEnabled
      val isUsernameNull = binding.usernameEditText.text.isNullOrEmpty()
      val isUsernameError = binding.usernameInputLayout.isErrorEnabled

      val listColorBirthdateButton = binding.btnBirthdatePicker.textColors
      val currentColor = binding.btnBirthdatePicker.currentTextColor
      val isBirthdateNull = binding.btnBirthdatePicker.text.isNullOrEmpty()

      val isPasswordNull = binding.passwordEditText.text.isNullOrEmpty()
      val isPasswordError = binding.passwordInputLayout.isErrorEnabled
      val isPasswordConfirmNull = binding.passwordConfirmEditText.text.isNullOrEmpty()
      val isPasswordConfirmError = binding.passwordConfirmInputLayout.isErrorEnabled

      binding.btnRegister.isEnabled =
         (!isFullnameNull && !isFullnameError
                 && !isUsernameNull && !isUsernameError
                 && !isEmailNull && !isEmailError
                 && !isBirthdateNull
                 && !isPasswordNull && !isPasswordError
                 && !isPasswordConfirmNull && !isPasswordConfirmError)
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
      const val TAG = "RegisterActivity"
   }
}