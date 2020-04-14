package com.lightrain.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.adapter.UserRelationshipAdapter
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.model.UserRelationshipBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.ThreadUtil
import com.lightrain.android.util.URLProviderUtils
import com.lightrain.android.util.UserInfoUtil
import kotlinx.android.synthetic.main.view_user_relationship.view.*
import org.jetbrains.anko.layoutInflater

class UserRelationshipView :RelativeLayout, ResponseHandler {
    private var status= LIST_FANS
    private val userList= ArrayList<UserInfoBean>()
    private var userRelationship:UserRelationshipBean?=null
    private var listNum=0
    private var count=0
    companion object{
        val LIST_FANS=0
        val LIST_FOLLOWS=1
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        context.layoutInflater.inflate(R.layout.view_user_relationship,this)
    }

    fun setData(status:Int){
        this.status=status
        userRelationship=LightRainApplication.userRelationship
        loadRelationship()
    }

    override fun onError(msg: String?) {
        println("UserRelationshipListView error:$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        when(status){
            ResponseStatus.USER_INFO->{
                val userInfoBean=result?.let { JsonUtil.getResultResponse(it,UserInfoBean::class.java).data }
                if (userInfoBean != null) {
                    userList.add(userInfoBean)
                }
                count++
                ThreadUtil.runOnMainThread(Runnable {
                    loadRecycleView()//获取完用户数据，加载RecycleView
                })
            }
            else->{}
        }

    }

    private fun loadRecycleView() {
        if (count==listNum){
            val manager= LinearLayoutManager(context)
            manager.orientation= LinearLayoutManager.VERTICAL
            urListRecyclerView.layoutManager=manager
            urListRecyclerView.adapter=UserRelationshipAdapter(userList)
            urListRecyclerView.itemAnimator= DefaultItemAnimator()
        }
    }

    private fun loadRelationship() {
        var listString=""
        userRelationship?.let {
            when(this.status){
                LIST_FANS->{
                    listString=it.userFans
                }
                LIST_FOLLOWS->{
                    listString=it.userFollows
                }
            }
        }
        val list=UserInfoUtil.analysisUserRelationshipList(listString)
        listNum=list.size
        if (list.isEmpty()){
            urListRecyclerViewBac.visibility= View.GONE
        }else{//如果用户粉丝或者关注不为0，加载之
            urListNull.visibility= View.GONE
            //获取对应用户信息
            for (s in list) {
                getUserInfo(s)
            }
        }
    }

    private fun getUserInfo(s: String) {
        HttpManager.manager.
        sendRequestByGet(URLProviderUtils.getUserInfo(s),ResponseStatus.USER_INFO,this)
    }
}