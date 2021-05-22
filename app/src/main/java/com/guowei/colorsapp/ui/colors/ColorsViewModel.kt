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

    override fun init(savedStateHandle: SavedStateHandle) {
        _uiModelLiveData = savedStateHandle.getLiveData(
            CURRENT_COLOR_LIVEDATA,
            ColorsUiModel(null, null, null, true)
        )

        Single.zip(
            colorsUseCase.getOrCreate(),
            colorsUseCase.getColorSet(),
            BiFunction { current: String, colorSet: List<String> ->
                ColorsUiModel(
                    currentColor = current,
                    chosenColor = current,
                    colorSet = colorSet,
                    isLoading = false
                )
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _uiModelLiveData.value = _uiModelLiveData.value?.copy(isLoading = true)
            }
            .doFinally {
                _uiModelLiveData.value = _uiModelLiveData.value?.copy(isLoading = false)
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
                .doOnSubscribe {
                    _uiModelLiveData.value = _uiModelLiveData.value?.copy(isLoading = true)
                }
                .doFinally {
                    _uiModelLiveData.value = _uiModelLiveData.value?.copy(isLoading = false)
                }
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
    }
}