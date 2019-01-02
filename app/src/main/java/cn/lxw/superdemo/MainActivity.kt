package cn.lxw.superdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mQnSDKBt.setOnClickListener(this)
        mQiniuPlayerBt.setOnClickListener(this)
        mSeekColorPickerViewBt.setOnClickListener(this)
        mNinetonProgressbarBt.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            //七牛云短视频编辑SDK
            R.id.mQnSDKBt -> {
                startActivity(Intent(this, QiniuSDKActivity::class.java))
            }
            //七牛云播放器
            R.id.mQiniuPlayerBt -> {
                startActivity(Intent(this, QiniuPlayerActivity::class.java))
            }
            R.id.mSeekColorPickerViewBt->{
                startActivity(Intent(this,SeekColorPickerViewActivity::class.java))
            }
            R.id.mNinetonProgressbarBt->{
                startActivity(Intent(this,NineTonProgressBarActivity::class.java))
            }

        }
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
