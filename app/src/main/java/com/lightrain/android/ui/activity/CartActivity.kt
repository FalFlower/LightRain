package com.lightrain.android.ui.activity

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.CartListAdapter
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.VideoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.CartUtil
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity :BaseActivity(), ResponseHandler {
    private val cartList by lazy { CartUtil.getCartListArray(this) }
    private var editTextStatus=false //false为默认，true为编辑状态，应当切换text
    private var selectImgStatus=false //false为默认，true为选中状态，应当切换Img
    private val videoList=ArrayList<VideoBean>()
    private var count=0
    private var size=0
    override fun getLayoutId(): Int {
        return R.layout.activity_cart
    }


    private fun loadVideoInfoData() {
        cartList?.let {
            size=it.size
            for (videoId in it) {
                HttpManager.manager.
                sendRequestByGet(URLProviderUtils.getVideoInfo(videoId),ResponseStatus.VIDEO,this)
            }
        }
    }

    override fun initListener() {
        cartBack.setOnClickListener { finish() }
        cartEdit.setOnClickListener {
            changeEditAndBtn()
        }
        cartSelectImg.setOnClickListener {
            changSelectImg()

        }
        if (cartList==null){
            //购物车为空
            cartListNull.visibility= VISIBLE
        }else{
            //购物车不为空
            loadVideoInfoData()
        }
        cartBtn.setOnClickListener {
            if (editTextStatus){
                //true为编辑状态->删除


            }else{
                //false为默认状态->去结算

            }
        }
    }

    //计算购物车中全部金额
    private fun countAllPrice() {
        var allPrice=0f
        for (videoBean in videoList) {
            allPrice+=videoBean.videoPrice
        }
        cartPriceAll.text="￥${(allPrice/1.0f).toString()}"
    }

    //设置RecycleView
    private fun initRecycleView() {
        val manager=LinearLayoutManager(this)
        manager.orientation=LinearLayoutManager.VERTICAL
        val adapter= CartListAdapter(this,videoList)
        cartRecyclerView.layoutManager=manager
        cartRecyclerView.adapter=adapter
    }

    private fun changSelectImg(){
        if (selectImgStatus){
            //true为选中状态，再次点击因该加载为默认状态
            ImageUtil.loadImage(this,cartSelectImg,R.mipmap.ic_no_select)
            selectImgStatus=false
            cartPriceAll.text="0.0"
        }else{
            //false为默认状态，再次点击因该加载为选中状态
            ImageUtil.loadImage(this,cartSelectImg,R.mipmap.ic_select_green)
            selectImgStatus=true
            countAllPrice()
        }
    }

    private fun changeEditAndBtn() {
        if (editTextStatus){
            //true为编辑状态，再次点击应该切换为非编辑状态
            cartEdit.text="编辑"
            editTextStatus=false
            cartBtn.text="去结算"

        }else{
            //false为默认状态，再次点击应该切换为编辑状态
            cartEdit.text="取消"
            editTextStatus=true
            cartBtn.text="删除"
        }
    }

    override fun onError(msg: String?) {
        println("CartActivity error:$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.VIDEO->{
                count++
                val videoInfo= result?.let { JsonUtil.getResultResponse(it,VideoBean::class.java).data }
                videoInfo?.let {
                    videoList.add(it)
                }
                if (count>=size){
                    //加载完数据
                    runOnUiThread{
                        initRecycleView()
                    }

                }
            }
            else->{}
        }
    }
}