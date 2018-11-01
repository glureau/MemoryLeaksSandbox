/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities.viewtreeobserver

import android.os.Bundle
import com.betclic.sandbox.memoryleakssandbox.R
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.UseMemoryActivity
import kotlinx.android.synthetic.main.activity_one_button.*

class ViewTreeObserverGlobalLayoutActivity : UseMemoryActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        button.viewTreeObserver.addOnGlobalLayoutListener {
            // Register an inner anonymous class, containing the context, to a static
            /* this. */updateView(33)
        }
    }

    fun updateView(fooBarCount: Int) {
        button.text = if (fooBarCount % 2 == 0) "even" else "odd"
    }
}