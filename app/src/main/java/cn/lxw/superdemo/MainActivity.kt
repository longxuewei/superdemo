package cn.lxw.superdemo

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtils.permission(PermissionConstants.STORAGE).callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {

                }

                override fun onDenied() {
                    finish()
                }
            }).rationale {
                ToastUtils.showLong("我们需要相关的权限才能继续工作，请在设置中打开。")
            }.request()
        }
    }
}
