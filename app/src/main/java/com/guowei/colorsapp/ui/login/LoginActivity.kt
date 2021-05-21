package com.guowei.colorsapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guowei.colorsapp.R
import com.guowei.colorsapp.ui.common.activity.BaseActivity
import com.guowei.colorsapp.ui.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: LoginViewModel

    private lateinit var loginButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        injector.inject(this)

        loginButton = findViewById(R.id.loginButton)
        usernameEditText = findViewById(R.id.userNameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        loginButton.setOnClickListener {
            viewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.loginLiveData.observe(this, Observer {
            it.consume {
                Toast.makeText(this@LoginActivity, if (this) "Yes" else "No", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}