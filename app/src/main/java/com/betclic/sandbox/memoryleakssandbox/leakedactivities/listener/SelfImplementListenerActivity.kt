/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */
package com.betclic.sandbox.memoryleakssandbox.leakedactivities.listener

import android.os.Bundle
import com.betclic.sandbox.memoryleakssandbox.R
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.UseMemoryActivity
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.staticref.StaticRefActivity
import kotlinx.android.synthetic.main.activity_one_button.*
import javax.inject.Inject


class SelfImplementListenerActivity : UseMemoryActivity(), OnDidSomethingListener {

    @Inject
    lateinit var viewModel: SelfImplementListenerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)

        val listener: OnDidSomethingListener = this // Here you "hide" the activity/context behind a simple interface.
        viewModel.setup(listener)
    }

    override fun onDidSomething(id: Int) {
    }
}

