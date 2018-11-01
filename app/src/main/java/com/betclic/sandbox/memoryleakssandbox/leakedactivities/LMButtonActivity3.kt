/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities

import android.app.Activity
import android.os.Bundle
import com.betclic.sandbox.memoryleakssandbox.R
import javax.inject.Inject

class LMButtonActivity3 : Activity() {
    @Inject
    lateinit var viewModel: LMViewModel3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_button)
        viewModel.setup(this)
    }
}