/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMViewModel2 /* or Manager */ @Inject constructor() {
    var activityContext: Context? = null
    fun setup(context:Context) {
        activityContext = context
    }
}