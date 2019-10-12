package com.sputnik.customviewtask

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt


//created by Ilmir Shagabiev 2019-10-10

class GraphCustomView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint(ANTI_ALIAS_FLAG)
    private var chartData: List<Float> = listOf()
    private var scaledData: MutableList<Float> = mutableListOf()
    private var spaceBetweenValues = floatToPixel(30f)

    fun setValues(values: List<Float>) {
        chartData = values
        scaledData = scaleValues()
        invalidate()
    }

    fun setSpaceBetweenValues(space: Int) {
        spaceBetweenValues = floatToPixel(space.toFloat())
    }

    init {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.style = Paint.Style.FILL
        paint.maskFilter = BlurMaskFilter(
            1f, BlurMaskFilter.Blur.INNER
        )

        if (attrs != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphCustomView)
            setSpaceBetweenValues(
                typedArray.getDimension(R.styleable.GraphCustomView_spaceBetweenValues, spaceBetweenValues).roundToInt()
            )
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 400 + paddingLeft + paddingRight
        val desiredHeight = 800 + paddingTop + paddingBottom
        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var startX = 0f
        val startY =
            resources.displayMetrics.heightPixels / 2

        val initX = startX
        val initY = startY

        val rectHeight = 30
        scaledData.forEachIndexed { i, it ->
            paint.color = Color.BLACK
            startX = initX + spaceBetweenValues * (i + 1)
            canvas?.drawRect(
                (startX - rectHeight),
                initY.toFloat(),
                (startX + rectHeight),
                (initY - spaceBetweenValues * (it + 1)),
                paint
            )
        }
    }

    private fun measureDimension(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> if (contentSize < specSize) contentSize else specSize
            else -> contentSize// MeasureSpec.UNSPECIFIED
        }
    }

    private fun scaleValues(): MutableList<Float> {
        val scaledValues = mutableListOf<Float>()
        var count = 0f
        for (i in chartData)
            count += i
        for (i in chartData.indices) {
            scaledValues.add(chartData[i] / count * 10)
        }
        return scaledValues
    }

    private fun floatToPixel(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

}