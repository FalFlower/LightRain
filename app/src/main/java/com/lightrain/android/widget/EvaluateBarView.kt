package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.lightrain.android.R
import com.lightrain.android.util.ImageUtil
import kotlinx.android.synthetic.main.view_evaluate_bar.view.*

class EvaluateBarView :RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        View.inflate(context, R.layout.view_evaluate_bar,this)
    }

    fun setScore(context: Context,score:Float){
        videoEvaluateScore.text=score.toString()
        when (score) {
            10f->{//五星
            }
            in 9f .. 10f -> {
                //四星半
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_half)
            }
            in 8f .. 9f -> {
                //四星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
            }
            in 7f .. 8f -> {
                //三星半
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_half)
            }
            in 6f .. 7f -> {
                //三星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
            }
            in 5f .. 6f -> {
                //两星半
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_half)
            }
            in 4f .. 5f -> {
                //两星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_null)
            }
            in 3f .. 4f -> {
                //一星半
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar2,R.mipmap.ic_star_half)
            }
            in 2f .. 3f -> {
                //一星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar2,R.mipmap.ic_star_null)
            }
            in 1f .. 2f -> {
                //半星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar2,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar1,R.mipmap.ic_star_half)
            }
            in 0f .. 1f -> {
                //0星
                ImageUtil.loadImage(context,videoEvaluateStar5,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar4,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar3,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar2,R.mipmap.ic_star_null)
                ImageUtil.loadImage(context,videoEvaluateStar1,R.mipmap.ic_star_null)
            }
        }
    }
}