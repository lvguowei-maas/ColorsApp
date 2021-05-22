package com.guowei.colorsapp.ui.colors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.guowei.colorsapp.ui.common.viewmodel.SavedStateViewModel
import com.guowei.colorsapp.usecase.ColorsUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ColorsViewModel @Inject constructor(
    private val colorsUseCase: ColorsUseCase
) : SavedStateViewModel() {

    private lateinit var _uiModelLiveData: MutableLiveData<ColorsUiModel>
    val uiModelLiveData: LiveData<ColorsUiModel> get() = _uiModelLiveData

    private lateinit var _isLoadingLiveData: MutableLiveData<Boolean>
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    override fun init(savedStateHandle: SavedStateHandle) {
        _uiModelLiveData = savedStateHandle.getLiveData(CURRENT_COLOR_LIVEDATA)
        _isLoadingLiveData = savedStateHandle.getLiveData(IS_LOADING_LIVEDATA)

        Single.zip(
            colorsUseCase.getOrCreate(),
            colorsUseCase.getColorSet(),
            BiFunction { current: String, colorSet: List<String> ->
                ColorsUiModel(
                    current,
                    current,
                    colorSet
                )
            })
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
                    _uiModelLiveData.value = it
                },
                {
                    Log.e("test", "TODO load current color error", it)
                }
            ).addToDisposable()
    }

    fun updateColor() {
        _uiModelLiveData.value?.chosenColor?.let {
            colorsUseCase.update(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoadingLiveData.value = true }
                .doFinally { _isLoadingLiveData.value = false }
                .subscribe({ updatedColor ->
                    _uiModelLiveData.value = _uiModelLiveData.value!!.copy(
                        currentColor = updatedColor
                    )
                }, {
                    // TODO handle error
                })

        }
    }

    fun previous() {
        _uiModelLiveData.value?.previous?.let {
            _uiModelLiveData.value = it
        }
    }

    fun next() {
        _uiModelLiveData.value?.next?.let {
            _uiModelLiveData.value = it
        }
    }

    companion object {
        private const val CURRENT_COLOR_LIVEDATA = "current_color"
        private const val IS_LOADING_LIVEDATA = "is_loading"
    }
}