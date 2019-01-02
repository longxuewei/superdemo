package cn.lxw.superdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.lxw.superdemo.widget.SeekColorPickerView
import kotlinx.android.synthetic.main.activity_seek_color_picker_view.*

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
class SeekColorPickerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seek_color_picker_view)
        mScpv.mColorChangeListener = object : SeekColorPickerView.OnColorChangeListener {
            override fun onChange(color: Int) {
                mColorIv.setBackgroundColor(color)
            }
        }
    }

}