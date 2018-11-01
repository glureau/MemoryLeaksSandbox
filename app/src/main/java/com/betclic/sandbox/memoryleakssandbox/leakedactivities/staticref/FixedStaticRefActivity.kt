/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities.staticref

import android.os.Bundle
import android.widget.Button
import com.betclic.sandbox.memoryleakssandbox.R
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.UseMemoryActivity
import kotlinx.android.synthetic.main.activity_one_button.*
import java.lang.ref.WeakReference

class FixedStaticRefActivity : UseMemoryActivity() {

    companion object {
        // Here we use a WeakRef so the view can be GCed.
        // As a result, the third-part code using this pointer should manage nullability
        private var lastButtonUsed: WeakReference<Button?> = WeakReference(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        lastButtonUsed = WeakReference(button)
    }
}