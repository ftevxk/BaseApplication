package com.ftevxk.core.extension

import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableBoolean

/**
 * 反射获取私有字段
 */
inline fun <reified T> getDeclaredField(obj: Any, name: String): T {
    return obj.javaClass.getDeclaredField(name).run {
        isAccessible = true
        get(obj) as T
    }
}

/**
 * 执行延时任务
 * @return 调用 ObservableBoolean.set(true) 可取消当前延迟任务
 */
fun runDelayed(delayMillis: Long, run: () -> Unit): ObservableBoolean {
    var handler: Handler? = Handler(Looper.getMainLooper())
    val runnable = Runnable {
        run.invoke()
        handler = null
    }
    handler?.postDelayed(runnable, delayMillis)
    return object : ObservableBoolean(false) {
        override fun set(value: Boolean) {
            super.set(value)
            if (value) {
                handler?.removeCallbacks(runnable)
                handler = null
            }
        }
    }
}