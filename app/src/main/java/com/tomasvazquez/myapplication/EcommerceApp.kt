package com.tomasvazquez.myapplication

import android.app.Application
import com.tomasvazquez.myapplication.di.initDI

class EcommerceApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}