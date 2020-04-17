package com.lightrain.android.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lightrain.android.R
import com.lightrain.android.adapter.ClassifyLeftViewAdapter
import com.lightrain.android.adapter.ClassifyRightViewAdapter
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.presenter.impl.ClassificationPresenterImpl
import com.lightrain.android.view.ClassificationView
import com.lightrain.android.widget.SearchBarView
import kotlinx.android.synthetic.main.fragment_find.*

class ClassificationFragment :BaseFragment(), ClassificationView {
    private val presenter by lazy { ClassificationPresenterImpl(this) }
    private var leftAdapter:ClassifyLeftViewAdapter?=null
    private var rightAdapter:ClassifyRightViewAdapter?=null
    override fun intView(): View? {
        return View.inflate(context,R.layout.fragment_find,null)
    }

    override fun initData() {
        classifySearchBar.setData(SearchBarView.STATUS_CLASSIFY)
        presenter.loadData()
    }

    /*
    * 接受处理P层返回数据
    * */
    override fun onError(msg: String?) {
        println("ClassificationFragment error:$msg")
    }

    override fun onSuccess(status: Int, msg: String?) {
        bindView()
    }

    private fun bindView() {
        val titleList= arrayOf("作文","语文","数学","英语","生物","化学","物理","政治","历史","地理")
        val linearLayoutManager=LinearLayoutManager(context)
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
        classifyLeftView.layoutManager=linearLayoutManager
        leftAdapter= ClassifyLeftViewAdapter(titleList)
        leftAdapter!!.setSetOnItemClickListener(object :ClassifyLeftViewAdapter.SetOnItemClickListener{
            override fun onItemClick(position: Int) {
                setRightRecycleView(position,titleList[position])
                rightAdapter?.notifyDataSetChanged()
            }

        })
        classifyLeftView.adapter=leftAdapter

    }

    private fun setRightRecycleView(position: Int, s: String) {
        val manager=GridLayoutManager(context,3)
        classifyRightView.layoutManager=manager
        rightAdapter=ClassifyRightViewAdapter(position,s)
        classifyRightView.adapter=rightAdapter
    }


}