package cn.lxw.superdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cn.lxw.superdemo.widget.NineTonProgressBar
import kotlinx.android.synthetic.main.activity_nineton_progress_bar.*

/**
 * *******************************
 * 猿代码: Lxw
 * Email: longxuewei@nineton.cn
 * 时间轴：2018-12-28 10:07
 * *******************************
 *
 * 备注：
 *
 */
class NineTonProgressBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nineton_progress_bar)

        mProgress.onProgressChangeListener = object : NineTonProgressBar.OnProgressChangeListener {
            override fun onProgress(progress: Int) {
                Log.d("TAG", "进度：$progress")
            }
        }
        mAddBt.setOnClickListener {
            mProgress.setProgress(mProgress.getProgress() - 1)
        }
    }

}