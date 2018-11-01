package com.betclic.sandbox.memoryleakssandbox.leakedactivities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import com.betclic.sandbox.memoryleakssandbox.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_one_button.*
import java.util.*


open class UseMemoryActivity : RxAppCompatActivity() {

    private var bitmaps: MutableList<Bitmap> = mutableListOf()
    private var byteArrays: MutableList<ByteArray> = mutableListOf()
    private var fragments: MutableList<List<Fragment>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Single.fromCallable {
            loadStuffInMemory(20)
        }
            .subscribeOn(Schedulers.io())
            .compose(bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::memoryLoaded)
    }

    protected open fun memoryLoaded(bitmapCount: Int) {
        button.text = "Memory loaded"
    }


    /**
     * Load heap memory
     */
    private fun loadStuffInMemory(itemCount: Int): Int {
        for (i in 0..itemCount) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
            bitmaps.add(bitmap)
            fragments.add(Collections.nCopies(100, Fragment()))
            byteArrays.add(ByteArray(1024 * 1024))
        }
        return 0
    }
}