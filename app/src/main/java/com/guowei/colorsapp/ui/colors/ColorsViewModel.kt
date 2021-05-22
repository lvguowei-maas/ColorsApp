package com.guowei.colorsapp.ui.colors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.guowei.colorsapp.ui.common.viewmodel.SavedStateViewModel
import com.guowei.colorsapp.usecase.ColorsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ColorsViewModel @Inject constructor(
    private val colorsUseCase: ColorsUseCase
) : SavedStateViewModel() {

    private lateinit var _currentColorLiveData: MutableLiveData<String>
    val currentColorLiveData: LiveData<String> get() = _currentColorLiveData

    private lateinit var _isLoadingLiveData: MutableLiveData<Boolean>
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    override fun init(savedStateHandle: SavedStateHandle) {
        _currentColorLiveData = savedStateHandle.getLiveData(CURRENT_COLOR_LIVEDATA)
        _isLoadingLiveData = savedStateHandle.getLiveData(IS_LOADING_LIVEDATA)

        colorsUseCase.getOrCreate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoadingLiveData.value = true
            }
            .doFinally {
                _isLoadingLiveData.value = false
            }
            .subscribe(
                {
                    _currentColorLiveData.value = it
                },
                {
                    Log.e("test", "load current color error", it)
                }
            ).addToDisposable()

    }

    companion object {
        private const val CURRENT_COLOR_LIVEDATA = "current_color"
        private const val IS_LOADING_LIVEDATA = "is_loading"
    }
}