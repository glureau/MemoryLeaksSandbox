package com.betclic.sandbox.memoryleakssandbox

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.widget.Button
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.listener.SelfImplementListenerActivity
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.staticref.FixedStaticRefActivity
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.staticref.StaticRefActivity
import com.betclic.sandbox.memoryleakssandbox.leakedactivities.viewtreeobserver.ViewTreeObserverGlobalLayoutActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_start.*
import java.util.concurrent.TimeUnit

class StartActivity : RxAppCompatActivity() {

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        addCase(StaticRefActivity::class.java, "Static ref")
        addCase(FixedStaticRefActivity::class.java, "Static ref fixed")
        //listener_button.startActivityOnClick(this, SelfImplementListenerActivity::class.java)
        //viewTreeObserver_button.startActivityOnClick(this, ViewTreeObserverGlobalLayoutActivity::class.java)

        forceGC.setOnClickListener { System.gc();System.gc() }

        // Start clean
        System.gc()
        System.gc()

        usageStart.text = "Mem usage at start: ..."

        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .compose(bindToLifecycle())
            .subscribe { usageStart.text = "Mem usage at start: ${nativeMemoryUsage()}" }
    }

    private fun addCase(clazz: Class<out Activity>, title: String) {
        val b = Button(this)
        b.text = title
        b.startActivityOnClick(this, clazz)
        caseList.addView(b)
    }

    override fun onResume() {
        super.onResume()
        Observable.interval(0, 500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .compose(bindToLifecycle())
            .subscribe({
                usageCurrent.text = "Mem usage: ${nativeMemoryUsage()}"
            }, {
                usageCurrent.text = "Error - " + it.message
                it.printStackTrace()
            })
    }

    private fun nativeMemoryUsage(): String {
        val nativeHeapSize = Debug.getNativeHeapSize()
        val nativeHeapFreeSize = Debug.getNativeHeapFreeSize()
        val usage = nativeHeapSize - nativeHeapFreeSize
        return String.format(
            "%.2fMb / %.2fMb (%.2f%%)",
            usage / 1048576f,
            nativeHeapSize / 1048576f,
            (usage.toFloat() / nativeHeapSize) * 100f
        )
    }
}

fun Button.startActivityOnClick(context: Context, clazz: Class<out Activity>) {
    setOnClickListener { context.startActivity(Intent(context, clazz)) }
}
