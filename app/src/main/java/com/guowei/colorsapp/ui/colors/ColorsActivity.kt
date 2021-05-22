package com.guowei.colorsapp.ui.colors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.guowei.colorsapp.R
import com.guowei.colorsapp.databinding.ActivityColorsBinding
import com.guowei.colorsapp.ui.common.activity.BaseActivity
import com.guowei.colorsapp.ui.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class ColorsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ColorsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ColorsViewModel::class.java)

        with(setContentView<ActivityColorsBinding>(this, R.layout.activity_colors)) {
            lifecycleOwner = this@ColorsActivity
            vm = viewModel
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ColorsActivity::class.java)
            context.startActivity(intent)
        }
    }
}