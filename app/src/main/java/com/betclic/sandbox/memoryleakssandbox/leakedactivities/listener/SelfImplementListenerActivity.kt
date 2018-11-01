/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */
package com.betclic.sandbox.memoryleakssandbox.leakedactivities.listener

import android.os.Bundle
import com.betclic.sandbox.memoryleakssandbox.R
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.UseMemoryActivity


class SelfImplementListenerActivity : UseMemoryActivity(), OnDidSomethingListener {

    private val manager: SelfImplementListenerManager = SelfImplementListenerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)

        val listener: OnDidSomethingListener = this // Here you "hide" the activity/context behind a simple interface.
        manager.setup(listener)
    }

    override fun onDidSomething(id: Int) {
    }
}

