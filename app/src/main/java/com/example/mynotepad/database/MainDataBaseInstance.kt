package com.example.mynotepad.database

import android.app.Application

class MainDataBaseInstance : Application() {
    val mainDataBaseInstance by lazy { MainDataBase.getMainDataBase(this) }
}
