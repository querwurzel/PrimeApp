package com.wilke.android.helloworld.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HelloModel : ViewModel() {

    var name = MutableLiveData<String>("World")

}