package com.guowei.colorsapp.ui.colors

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guowei.colorsapp.R
import com.guowei.colorsapp.ui.common.activity.BaseActivity
import com.guowei.colorsapp.ui.common.viewmodel.ViewModelFactory
import javax.inject.Inject

class ColorsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ColorsViewModel


    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors)

        injector.inject(this)

        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)
        progressBar = findViewById(R.id.progressBar)
        rootView = window.decorView.rootView

        viewModel = ViewModelProvider(this, viewModelFactory).get(ColorsViewModel::class.java)

        viewModel.currentColorLiveData.observe(this, Observer {
            rootView.setBackgroundColor(Color.parseColor(it))
        })

        viewModel.isLoadingLiveData.observe(this, Observer {
            prevButton.isEnabled = !it
            nextButton.isEnabled = !it
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ColorsActivity::class.java)
            context.startActivity(intent)
        }
    }
}