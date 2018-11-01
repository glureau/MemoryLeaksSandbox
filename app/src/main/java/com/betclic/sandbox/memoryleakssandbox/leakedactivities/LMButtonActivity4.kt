/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.betclic.sandbox.memoryleakssandbox.R
import kotlinx.android.synthetic.main.activity_one_button.*
import javax.inject.Inject

class LMButtonActivity4 : Activity() {
    @Inject
    lateinit var viewModel: LMViewModel4

    private var fooBarCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        viewModel.setup(View.OnClickListener {
            /*this.*/fooBarCount++
            /*this.*/updateView(fooBarCount)
        })
    }

    fun updateView(fooBarCount: Int) {
        button.text = if (fooBarCount % 2 == 0) "even" else "odd"
    }
}