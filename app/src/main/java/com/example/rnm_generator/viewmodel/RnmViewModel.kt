package com.example.rnm_generator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rnm_generator.domain.RnmNumber

class RnmViewModel(application: Application) : AndroidViewModel(application) {
    val rnmLiveData = MutableLiveData<String>()
    fun generateRnm(inn: String, factoryNumber: String) {
        rnmLiveData.postValue(RnmNumber.calculate(inn, factoryNumber))
    }
}
