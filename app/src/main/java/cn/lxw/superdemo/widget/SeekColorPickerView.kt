package cn.lxw.superdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * *******************************
 * 猿代码: Lxw
 * Email: longxuewei@nineton.cn
 * 时间轴：2018-12-28 9:37
 * *******************************
 *
 * 备注：滑动选择颜色的View
 *
 */
class SeekColorPickerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mColorPaint = Paint()

    private val mWidth: Int = 600

    private val mHeight: Int = 80
    /** 颜色封装 */
    private var mColorObj = mutableListOf<ColorObj>()

    /** 游标 */
    private var mColorCursor: RectF = RectF()

    /** 游标的触摸接受器 */
    private var mColorCursorTouchReceiver: RectF = RectF()

    /** 色块平均宽度 */
    private var avgWidth = 0f

    @ColorInt
    private var oldColor = -1

    var mColorChangeListener: OnColorChangeListener? = null

    /** 默认颜色值 */
    private var mDefColorValues = mutableListOf(
            Color.parseColor("#F6F6F6"),
            Color.parseColor("#090909"),
            Color.parseColor("#EA4028"),
            Color.parseColor("#EBB451"),
            Color.parseColor("#F2E43E"),
            Color.parseColor("#BDF14B"),
            Color.parseColor("#3EA927"),
            Color.parseColor("#56C7F5"),
            Color.parseColor("#3575F6"),
            Color.parseColor("#1036C2"),
            Color.parseColor("#8C58F5"),
            Color.parseColor("#F395F5"),
            Color.parseColor("#EF6A89"),
            Color.parseColor("#EC37B8")
    )


    private var downX = 0f
    private var downY = 0f
    private var moveX = 0f
    private var moveY = 0f
    private var isChangeColor = false

    init {
        mColorPaint.isAntiAlias = true
    }


    /**
     * 初始化默认色块
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        refreshColorAxis()
    }


    /**
     * 刷新颜色坐标
     */
    private fun refreshColorAxis() {
        avgWidth = width.toFloat() / mDefColorValues.size
        mDefColorValues.forEachIndexed { index, i ->
            mColorObj.add(ColorObj(i, RectF(avgWidth * index, 20f, avgWidth * index + avgWidth + 1, height - 2f)))
        }
        mColorCursor = RectF(mDefColorValues.size / 2f * avgWidth, 2f, mDefColorValues.size / 2 * avgWidth + 8f, height.toFloat())
        refreshColorCursorTouchReceiverAxis()
    }

    /**
     * 刷新游标的接受触摸的坐标
     */
    private fun refreshColorCursorTouchReceiverAxis() {
        mColorCursorTouchReceiver = RectF(mColorCursor.left - avgWidth, 0f, mColorCursor.right + avgWidth, height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mColorObj.forEach {
            mColorPaint.color = it.color
            canvas.drawRect(it.rectF, mColorPaint)
        }
        mColorPaint.color = Color.WHITE
        canvas.drawRoundRect(mColorCursor, 5f, 5f, mColorPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                moveX = event.x
                moveY = event.y
                isChangeColor = mColorCursorTouchReceiver.contains(downX, downY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isChangeColor) {
                    val xOffset = event.x - moveX
                    val colorCursorWidth = mColorCursor.width()

                    mColorCursor.left = mColorCursor.left + xOffset
                    mColorCursor.right = mColorCursor.left + colorCursorWidth

                    if (mColorCursor.left <= 0f) {
                        mColorCursor.left = 0f
                        mColorCursor.right = mColorCursor.left + colorCursorWidth
                    }

                    if (mColorCursor.right >= width.toFloat()) {
                        mColorCursor.right = width.toFloat()
                        mColorCursor.left = mColorCursor.right - colorCursorWidth
                    }

                    //判断回调
                    val currentColor = mColorObj.filter { it.rectF.contains(mColorCursor.left, mColorCursor.top + 2, mColorCursor.right, mColorCursor.bottom - 2) }
                    if (currentColor.isNotEmpty() && mColorChangeListener != null && currentColor[0].color != oldColor) {
                        mColorChangeListener!!.onChange(currentColor[0].color)
                    }

                    //刷新坐标
                    refreshColorCursorTouchReceiverAxis()
                    postInvalidate()
                    moveX = event.x
                }
            }
        }
        return super.onTouchEvent(event)
    }


    /**
     * 设置颜色值
     * [colors]：颜色值
     */
    fun setColors(colors: MutableList<Int>) {
        mDefColorValues = colors
        refreshColorAxis()
        postInvalidate()
    }


    /**
     * warp_content support
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量宽高
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //获取宽高模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //宽度设定，默认的宽度+边框的宽度+
        var realWidth = widthSize
        var realHeight = heightSize

        //warp_content 模式
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            realWidth = mWidth
            realHeight = mHeight
        } else if (widthMode == MeasureSpec.AT_MOST) {
            realWidth = mWidth
            realHeight = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            realHeight = mHeight
            realWidth = widthSize
        }
        setMeasuredDimension(realWidth, realHeight)
    }

    /**
     * 色块对象
     * [color]:颜色值
     * [rectF]:色块位置
     */
    inner class ColorObj(@ColorInt var color: Int, var rectF: RectF)


    /**
     * 颜色监听器
     */
    interface OnColorChangeListener {
        fun onChange(@ColorInt color: Int)
    }
}

