package com.betclic.sandbox.memoryleakssandbox.leakedactivities.daggersingleton

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingletonViewModel @Inject constructor() {
    var activityContext: Context? = null
    fun setup(context: Context) {
        activityContext = context
    }
}