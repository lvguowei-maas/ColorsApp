package com.guowei.colorsapp.ui.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guowei.colorsapp.R
import com.guowei.colorsapp.ui.common.activity.BaseActivity
import com.guowei.colorsapp.ui.common.viewmodel.ViewModelFactory
import com.guowei.colorsapp.ui.login.LoginActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        injector.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        viewModel.isLoggedInLiveData.observe(this, Observer {
            it.consume {
                if (this) {
                    // TODO
                } else {
                    LoginActivity.start(this@SplashActivity)
                    finish()
                }
            }
        })
    }
}