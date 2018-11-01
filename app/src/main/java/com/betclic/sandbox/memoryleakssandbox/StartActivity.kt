package com.betclic.sandbox.memoryleakssandbox

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
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
        addCase(SelfImplementListenerActivity::class.java, "Activity implements listener-like interface")
        addCase(ViewTreeObserverGlobalLayoutActivity::class.java, "Use of getViewTreeGlobalLayout()")

        forceGC.setOnClickListener { fullGC() }

        // Start clean
        fullGC()

        usageStart.text = "Mem usage at start: ..."

        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .compose(bindToLifecycle())
            .subscribe { usageStart.text = "Mem usage at start: ${nativeMemoryUsage()}" }
    }

    private fun fullGC() {
        System.gc()
        System.gc()
        Runtime.getRuntime().gc()
        Runtime.getRuntime().gc()
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
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val approximatedPerAppMaxRamMb = activityManager.memoryClass.toFloat()
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)
        val deviceRamUsage = (memInfo.totalMem - memInfo.availMem) / memInfo.totalMem.toFloat()
        Log.e(
            "FOO",
            "totalMem=" + memInfo.totalMem + " availMem=" + memInfo.availMem + " lowMemory=" + memInfo.lowMemory
        )
        val usage = nativeHeapSize - nativeHeapFreeSize
        return String.format(
            "%.2fMb / %.2fMb (%.2f%% - %.2f%%)",
            usage / 1048576f,
            approximatedPerAppMaxRamMb,
            ((usage / 1048576f) / approximatedPerAppMaxRamMb) * 100f,
            deviceRamUsage * 100
        )
    }
}

fun Button.startActivityOnClick(context: Context, clazz: Class<out Activity>) {
    setOnClickListener { context.startActivity(Intent(context, clazz)) }
}
