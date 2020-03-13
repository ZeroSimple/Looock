package com.zerosimple.looock

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.zerosimple.looock.SecondActivity


class MainActivity : Activity() {
    companion object {
        const val CMD_STAT_SUCCESS = 0
    }

    private fun  toast(msg: String) {
        Toast.makeText(this, msg,  Toast.LENGTH_SHORT).show();
    }

    override fun onStart() {
        super.onStart()

        try {
            val cmd = arrayOf("su","-c","input keyevent KEYCODE_POWER")
            val stat = Runtime.getRuntime().exec(cmd).waitFor()
            if (CMD_STAT_SUCCESS != stat) {
                this.toast( "你可能拒绝了本应用使用ROOT权限")
                Log.i("LockScreen","锁屏失败（错误码：$stat）")

                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("LockScreen","锁屏成功")
            }
        } catch (e: Exception) {
            this.toast( "你可能没有ROOT权限")
            Log.w("NotRoot","你可能没有ROOT权限")
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)

        } finally {
            this.finish()
            Log.i("Exit","正常退出")
        }
    }
}
