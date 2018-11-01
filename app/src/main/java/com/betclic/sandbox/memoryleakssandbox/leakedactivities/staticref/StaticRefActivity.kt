/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities.staticref

import android.os.Bundle
import android.widget.Button
import com.betclic.sandbox.memoryleakssandbox.R
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.UseMemoryActivity
import kotlinx.android.synthetic.main.activity_one_button.*

class StaticRefActivity : UseMemoryActivity() {

    companion object {
        // companion object = static class = GC root
        private var lastButtonUsed: Button? = null // Button contains context of the activity (like all View subclasses)
        // Only the latest activity will be leaked here
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        lastButtonUsed = button // Assigning the activity context to a static ref!
    }
}