package cn.lxw.superdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * *******************************
 * 猿代码: Lxw
 * Email: longxuewei@nineton.cn
 * 时间轴：2018-12-28 15:06
 * *******************************
 *
 * 备注：透明度触摸进度条
 *
 */
class NineTonProgressBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint = Paint()

    /** 拇指触摸点为半径为11 */
    private var mThumbRadius = 11

    /** 默认高度 */
    private var mHeight = mThumbRadius * 2

    /** 默认宽度 */
    private var mWidth = 500

    /** 三条线的宽度 */
    private val mLineWidth = 2f

    /** 触摸圆点 */
    private var mThumbRect: RectF = RectF()

    /** 是否改变进度值 */
    private var isChangeProgress = false

    /** 监听器 */
    var onProgressChangeListener: OnProgressChangeListener? = null

    /** 上次进度 */
    private var oldProgressRate: Float = 50f

    private var downX = 0f
    private var moveX = 0f


    init {
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mLineWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        refreshThumbAxis()
    }

    /**
     * 刷新拇指坐标参数
     */
    private fun refreshThumbAxis() {
        mThumbRect.left = width / 2f - mThumbRadius
        mThumbRect.top = height / 2f - mThumbRadius
        mThumbRect.right = mThumbRect.left + mThumbRadius * 2
        mThumbRect.bottom = height / 2f + mThumbRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(mThumbRadius.toFloat(), height.toFloat() / 2 - 5, mThumbRadius.toFloat(), height.toFloat() / 2 + 5, mPaint)
        canvas.drawLine(mThumbRadius.toFloat(), height.toFloat() / 2, width.toFloat() - mThumbRadius, height.toFloat() / 2, mPaint)
        canvas.drawLine(width.toFloat() - mLineWidth / 2 - mThumbRadius, height.toFloat() / 2 - 5, width.toFloat() - mLineWidth / 2 - mThumbRadius, height.toFloat() / 2 + 5, mPaint)
        canvas.drawOval(mThumbRect, mPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                moveX = event.x
                isChangeProgress = mThumbRect.contains(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isChangeProgress) {
                    val xOffset = event.x - moveX
                    mThumbRect.left = mThumbRect.left + xOffset
                    mThumbRect.right = mThumbRect.left + mThumbRadius * 2

                    //判断左侧越界
                    if (mThumbRect.left <= 0f) {
                        mThumbRect.left = 0f
                        mThumbRect.right = mThumbRect.left + mThumbRadius * 2
                    }

                    if (mThumbRect.right >= width) {
                        mThumbRect.right = width.toFloat()
                        mThumbRect.left = mThumbRect.right - mThumbRadius * 2
                    }
                    moveX = event.x
                    //总长度
                    val totalLength = width - mThumbRadius * 2
                    val currProgress = mThumbRect.left
                    val progressRate = currProgress / totalLength * 100
                    if (progressRate != oldProgressRate) {
                        onProgressChangeListener?.onProgress(progressRate.toInt())
                        oldProgressRate = progressRate
                    }
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }


    /**
     * 设置进度：
     * [progress]：进度，最小0，最大：100
     */
    fun setProgress(progress: Int) {
        var realProgress = progress
        if (realProgress < 0)
            realProgress = 0
        if (realProgress > 100)
            realProgress = 100
        val settingProgress = width.toFloat() / 100 * realProgress
        mThumbRect.left = settingProgress - mThumbRadius
        if (mThumbRect.left < 0f) {
            mThumbRect.left = 0f
            mThumbRect.right = mThumbRect.left + mThumbRadius * 2
        }
        mThumbRect.right = mThumbRect.left + mThumbRadius * 2
        if (mThumbRect.right > width) {
            mThumbRect.right = width.toFloat()
            mThumbRect.left = mThumbRect.right - mThumbRadius * 2
        }
        oldProgressRate = realProgress.toFloat()
        invalidate()
    }


    /**
     * 获取进度
     */
    fun getProgress(): Int = oldProgressRate.toInt()


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
     * 进度回调
     */
    interface OnProgressChangeListener {
        fun onProgress(progress: Int)
    }
}