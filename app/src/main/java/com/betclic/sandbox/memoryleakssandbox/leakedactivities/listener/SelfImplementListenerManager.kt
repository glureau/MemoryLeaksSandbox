/*
 * Copyright (c) 2018 Betclic. All rights reserved.
 */

package com.betclic.sandbox.memoryleakssandbox.leakedactivities.listener

// object means GC root, all variables are static variables
object SelfImplementListenerManager {

    var onDidSomethingListener: OnDidSomethingListener? = null

    fun setup(listener: OnDidSomethingListener) {
        onDidSomethingListener = listener
    }
}