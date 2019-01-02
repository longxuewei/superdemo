package cn.lxw.superdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.lxw.superdemo.widget.MediaController
import com.pili.pldroid.player.widget.PLVideoView
import kotlinx.android.synthetic.main.activity_qiniu_player.*

/**
 * *******************************
 * 猿代码: Lxw
 * Email: longxuewei@nineton.cn
 * 时间轴：2018-12-26 14:26
 * *******************************
 *
 * 备注：
 *
 */
class QiniuPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qiniu_player)
        mPlayerPtv.setMediaController(MediaController(this))
        mPlayerPtv.setBufferingIndicator(mBufferIndicatorIv)
        mPlayerPtv.displayAspectRatio = PLVideoView.ASPECT_RATIO_4_3
        mPlayerPtv.setVideoPath("/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1545697716322.mp4")
        mPlayerPtv.setOnPreparedListener {
            mPlayerPtv.start()
        }
    }
}