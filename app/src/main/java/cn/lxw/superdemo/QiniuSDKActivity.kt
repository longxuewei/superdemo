package cn.lxw.superdemo

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.qiniu.pili.droid.shortvideo.PLShortVideoEditor
import com.qiniu.pili.droid.shortvideo.PLTextView
import com.qiniu.pili.droid.shortvideo.PLVideoEditSetting
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener
import kotlinx.android.synthetic.main.activity_qiniu.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * *******************************
 * 猿代码: Lxw
 * Email: longxuewei@nineton.cn
 * 时间轴：2018-12-25 13:25
 * *******************************
 *
 * 备注：
 *
 */
class QiniuSDKActivity : AppCompatActivity() {


    val TAG = "QiniuSDKActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qiniu)
        mSubTitleBt.setOnClickListener {
            val setting = PLVideoEditSetting()
            setting.sourceFilepath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1545697716322.mp4"
            setting.destFilepath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/test.mp4"
            val editor = PLShortVideoEditor(mSfv)
            editor.setVideoEditSetting(setting)

            Log.d(TAG, "完成操作")

            editor.setVideoSaveListener(object : PLVideoSaveListener {
                override fun onSaveVideoCanceled() {
                    Log.d(TAG, "onSaveVideoCanceled")
                }

                override fun onProgressUpdate(p0: Float) {
                    Log.d(TAG, "onProgressUpdate: $p0")
                }

                override fun onSaveVideoSuccess(p0: String) {
                    Log.d(TAG, "onSaveVideoSuccess:$p0")
                }

                override fun onSaveVideoFailed(p0: Int) {
                    Log.d(TAG, "onSaveVideoFailed:$p0")
                }

            })

            val tv = PLTextView(this)
            tv.setText("哈哈哈")
        }
    }


    class MyRenderer : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            gl.glViewport(0, 0, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        }

    }
}