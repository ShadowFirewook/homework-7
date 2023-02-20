package com.example.homework_7.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_7.domain.utils.Resource
import com.example.homework_7.presentation.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    protected fun <T> Flow<com.example.homework_7.domain.utils.Resource<T>>.collectFlow(_state: MutableStateFlow<UIState<T>>) {
        viewModelScope.launch(Dispatchers.IO){
            this@collectFlow.collect{
                when(it){
                    is com.example.homework_7.domain.utils.Resource.Error -> {
                        _state.value = UIState.Error(it.message!!)
                    }
                    is com.example.homework_7.domain.utils.Resource.Loading -> {
                        _state.value = UIState.Loading()
                    }
                    is com.example.homework_7.domain.utils.Resource.Success -> {
                        if (it.data != null)
                            _state.value = UIState.Success(it.data!!)
                    }
                }
            }
        }

    }

}