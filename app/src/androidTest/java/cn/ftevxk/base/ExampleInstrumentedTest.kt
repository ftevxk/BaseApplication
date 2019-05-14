package cn.ftevxk.base

import androidx.test.rule.ActivityTestRule
import cn.ftevxk.base.extension.getSpValue
import cn.ftevxk.base.extension.putSpValue
import cn.ftevxk.base.extension.removeSpKey
import com.pawegio.kandroid.d
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private val activity by lazy { activityTestRule.activity }

    /**
     * SharedPreferences数据测试
     */
    @Test
    fun testSpValue() {
        testRemoveSpValue()
        testPutSpValue()
        testGetSpValue()
    }

    private fun testPutSpValue() {
        activity.putSpValue(
            "111" to 123456.0,
            "222" to mutableListOf(true, 0.11f, 0.22, "666", 111, 222L),
            "333" to mutableMapOf(true to 0.11f, "666" to 111)
        )
        d("putSpValue Success")
    }

    private fun testGetSpValue() {
        val key111 = activity.getSpValue("111", 0.0)
        val key222 = activity.getSpValue("222", mutableListOf<Any>("empty"))
        val key333 = activity.getSpValue("333", mutableMapOf<Any, Any>("empty" to ""))

        val logStr = StringBuilder()
        logStr.append(" \n")
        logStr.append("================ getSpValue(111) Success ================\n")
        logStr.append("type = ${key111.javaClass.simpleName}, value = $key111\n")
        logStr.append("================ getSpValue(222) Success ================\n")
        key222.forEach { logStr.append("type = ${it.javaClass.simpleName}, value = $it\n") }
        logStr.append("================ getSpValue(333) Success ================\n")
        key333.forEach {
            logStr.append("keyType = ${it.key.javaClass.simpleName}, keyValue = ${it.key}\n")
            logStr.append("valueType = ${it.value.javaClass.simpleName}, valueValue = ${it.value}\n\n")
        }
        d(logStr.toString())
    }

    private fun testRemoveSpValue() {
        val key111 = activity.removeSpKey("111")
        val key222 = activity.removeSpKey("222")
        val key333 = activity.removeSpKey("333")
        d("removeSpKey key(111) = $key111, key(222) = $key222, key(333) = $key333")
    }
}
