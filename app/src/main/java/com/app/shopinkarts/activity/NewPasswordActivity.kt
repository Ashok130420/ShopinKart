package com.app.shopinkarts.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityNewPasswordBinding
import com.app.shopinkarts.response.ForgotPasswordRestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewPasswordBinding
    var phoneNo = ""
    var password = ""
    var confirmPassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_password)

        phoneNo = intent.extras!!.getString("phoneNo", "")

        binding.headerNewPassword.nameTV.text = resources.getString(R.string.password_reset)
        binding.headerNewPassword.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.resetTV.setOnClickListener {
            password = binding.newPasswordET.text.toString().trim()
            confirmPassword = binding.confirmPasswordET.text.toString().trim()
            if (password.isEmpty()) {
                binding.newPasswordET.error = "Enter new password"
                binding.newPasswordET.requestFocus()
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                binding.confirmPasswordET.error = "Enter confirm password"
                binding.confirmPasswordET.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            forgotPasswordRestOtp()

        }
    }

    private fun forgotPasswordRestOtp() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["phone"] = phoneNo
        requestBody["newPassword"] = password

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Please Wait...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

        val call: Call<ForgotPasswordRestResponse> =
            RetrofitClient.instance!!.api.forgotPasswordRest(requestBody)
        call.enqueue(object : Callback<ForgotPasswordRestResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordRestResponse>,
                response: Response<ForgotPasswordRestResponse>
            ) {
                if (response.isSuccessful) {
                    val forgotPasswordSendOtpResponse = response.body()
                    if (forgotPasswordSendOtpResponse!!.status) {
                        mProgressDialog.dismiss()

                        Toast.makeText(
                            this@NewPasswordActivity,
                            forgotPasswordSendOtpResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent =
                            Intent(this@NewPasswordActivity, PasswordChangedActivity::class.java)
                        startActivity(intent)

                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@NewPasswordActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<ForgotPasswordRestResponse>, t: Throwable) {
                Toast.makeText(
                    this@NewPasswordActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                mProgressDialog.dismiss()
            }

        })
    }

    // Password hide and show
    fun passToggle(view: View) {
        binding.newPasswordToggleIV.setOnClickListener(object : View.OnClickListener {
            var button01pos = 0
            override fun onClick(v: View?) {
                if (button01pos == 0) {
                    binding.newPasswordToggleIV.setImageResource(R.drawable.eye_close)
                    button01pos = 1
                    binding.newPasswordET.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.newPasswordET.setSelection(binding.newPasswordET.length())
                } else if (button01pos == 1) {
                    binding.newPasswordToggleIV.setImageResource(R.drawable.eye_open)
                    button01pos = 0
                    binding.newPasswordET.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.newPasswordET.setSelection(binding.newPasswordET.length())
                }
            }
        })
    }

    fun cPassToggle(view: View) {
        binding.confirmPasswordToggleIV.setOnClickListener(object : View.OnClickListener {
            var button01pos = 0
            override fun onClick(v: View?) {
                if (button01pos == 0) {
                    binding.confirmPasswordToggleIV.setImageResource(R.drawable.eye_close)
                    button01pos = 1
                    binding.confirmPasswordET.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.confirmPasswordET.setSelection(binding.confirmPasswordET.length())
                } else if (button01pos == 1) {
                    binding.confirmPasswordToggleIV.setImageResource(R.drawable.eye_open)
                    button01pos = 0
                    binding.confirmPasswordET.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.confirmPasswordET.setSelection(binding.confirmPasswordET.length())
                }
            }
        })
    }

    // To remove EditText focus on touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}