package com.betclic.sandbox.memoryleakssandbox

import com.betclic.sandbox.memoryleakssandbox.leakedactivities.daggersingleton.SingletonViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun providesSingletonViewModel(): SingletonViewModel
}