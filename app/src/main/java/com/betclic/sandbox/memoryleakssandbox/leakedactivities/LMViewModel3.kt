/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities
import android.content.Context
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMViewModel3 /* or Manager */ @Inject constructor() {
    fun setup(context: Context) {
        doSomethingThatTakeTime { Toast.makeText(context, "Leaked!", Toast.LENGTH_SHORT).show() }
    }

    private fun doSomethingThatTakeTime(onSuccess: () -> Unit) {
        Thread.sleep(1000)
        onSuccess()
    }
}