/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities.listener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelfImplementListenerViewModel @Inject constructor() {

    var onDidSomethingListener: OnDidSomethingListener? = null

    fun setup(listener: OnDidSomethingListener) {
        onDidSomethingListener = listener
    }
}