package com.example.carrot.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.carrot.Network.RetrofitClient
import com.example.carrot.R
import com.example.carrot.Response.Data
import com.example.carrot.Response.User
import com.example.carrot.Response.UserResponse
import com.example.carrot.ResponseCode
import com.example.carrot.Service.AuthService
import com.example.carrot.View.LogInActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_additem.*
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.fragment_join.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class JoinFragment : Fragment(R.layout.fragment_join) {
    private lateinit var retrofit: Retrofit
    private lateinit var joinService: AuthService

    private var join: UserResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initRetrofit()
    }

    private fun initView() {
        initToolbar()
        initName()
        initPhoneNum()
        initPassword()
        initNext()
    }

    private fun initToolbar() {
        joinToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.joinToolbarFinish -> {
                    if (!etPhoneNum.text.isNullOrEmpty() && !etName.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()) {
                        checkUser()
                    } else {
                        setError()
                    }
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
        joinToolbar.setNavigationOnClickListener {
            activity?.finish()
            val intent = Intent(context, LogInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        joinService = retrofit.create(AuthService::class.java)
    }

    private fun initName() {
        etName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (etName.text.isNullOrEmpty()) {
                    tilName.error = "????????? ??????????????????."
                } else {
                    tilName.error = null
                }
            }
        }
    }

    private fun initPhoneNum() {
        etPhoneNum.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validatePhoneNum()
            }
        }
    }

    private fun initPassword() {
        etPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (etPassword.text.isNullOrEmpty()) {
                    tilPassword.error = "??????????????? ??????????????????."
                } else {
                    tilPassword.error = null
                }
            }
        }
    }

    private fun initNext() {
        btnNext.setOnClickListener {
            if (!etPhoneNum.text.isNullOrEmpty() && !etName.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()) {
                checkUser()
            } else {
                setError()
            }
        }
    }


    private fun validatePhoneNum(): Boolean {
        return if (etPhoneNum.text.isNullOrEmpty()) {
            tilPhoneNum.error = "????????? ????????? ??????????????????."
            false
        } else {
            val phoneNum = etPhoneNum.text.toString()
            val isValid = android.util.Patterns.PHONE.matcher(phoneNum).matches()

            if (!isValid) {
                tilPhoneNum.error = "????????? ????????? ????????? ???????????????. ex)010-1234-5678"
                false
            } else {
                tilPhoneNum.error = null
                true
            }
        }
    }

    private fun checkUser() {
        val name = etName.text.toString()
        val phoneNum = etPhoneNum.text.toString()
        val password = etPassword.text.toString()
        //val callUser = joinService.isSighUp(phoneNum)

        moveNext(name, phoneNum, password)
    }

    private fun moveNext(name: String, phoneNum: String, password: String) {
        if (!name.isNullOrEmpty() && !phoneNum.isNullOrEmpty() && !password.isNullOrEmpty()) {

            val join = joinService.signUp(name, phoneNum, password, "ROLE_ADMIN,ROLE_USER")

            join.enqueue(object: Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if(response.isSuccessful) {
                        Log.d("test", "${response.body()!!.successString}")
                        finish()
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("onFailure", "$t")
                }
            })

        }
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()

        Log.d("JoinFragment: finish: Success:: ", "???????????? ??????")
        Toast.makeText(context, "???????????? ??????", Toast.LENGTH_SHORT).show()
        activity?.finish()
    }

    private fun setError() {
        if (etName.text.isNullOrEmpty()) {
            tilName.error = "????????? ??????????????????."
        }
        if (etPhoneNum.text.isNullOrEmpty()) {
            tilPhoneNum.error = "??????????????? ??????????????????."
        }
        if (etPassword.text.isNullOrEmpty()) {
            tilPassword.error = "??????????????? ??????????????????."
        }
    }

}