package com.guowei.colorsapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.guowei.colorsapp.ui.common.utils.Consumable
import com.guowei.colorsapp.ui.common.utils.toConsumable
import com.guowei.colorsapp.ui.common.viewmodel.SavedStateViewModel
import com.guowei.colorsapp.usecase.LoginUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : SavedStateViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private lateinit var _loginLiveData: MutableLiveData<Consumable<Boolean>>
    val loginLiveData: LiveData<Consumable<Boolean>> get() = _loginLiveData

    override fun init(savedStateHandle: SavedStateHandle) {
        _loginLiveData = savedStateHandle.getLiveData(LOGIN_LIVEDATA)
    }

    fun login(username: String, password: String) {
        disposable.add(
            loginUseCase.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _loginLiveData.value = true.toConsumable()
                    },
                    {
                        _loginLiveData.value = false.toConsumable()
                    })
        )
    }

    companion object {
        private const val LOGIN_LIVEDATA = "login"
    }
}