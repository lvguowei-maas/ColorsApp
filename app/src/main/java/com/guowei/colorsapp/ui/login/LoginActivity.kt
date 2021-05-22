package com.guowei.colorsapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guowei.colorsapp.R
import com.guowei.colorsapp.ui.colors.ColorsActivity
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
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        injector.inject(this)

        loginButton = findViewById(R.id.loginButton)
        usernameEditText = findViewById(R.id.userNameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        progressBar = findViewById(R.id.progressBar)

        loginButton.setOnClickListener {
            viewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.loginLiveData.observe(this, Observer {
            it.consume {
                if (this) {
                    ColorsActivity.start(this@LoginActivity)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            loginButton.isEnabled = !it
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}