package com.lightrain.android.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.model.VideoBean
import com.lightrain.android.util.StartActivityUtil
import com.lightrain.android.widget.PersonalSpaceListView

class PersonalSpaceAdapter(private val list:List<VideoBean>) :RecyclerView.Adapter<PersonalSpaceAdapter.PersonalSpaceHolder>(){
    var view: PersonalSpaceListView?=null
    var context:Context?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalSpaceHolder {
        context=parent.context
        view= PersonalSpaceListView(context)
        return PersonalSpaceHolder(view!!)
    }

    override fun getItemCount(): Int {
        println("!!!!!!!!!!!!!!!!!!getItemCount${if (list.isEmpty()) 1 else list.size}")
        return if (list.isEmpty()) 1 else list.size
    }

    override fun onBindViewHolder(holder: PersonalSpaceHolder, position: Int) {
        view?.let {
            if (list.isNotEmpty()){
                val data=list[position]
                it.setData(data.videoIcon,data.videoTitle,data.videoLearningNum,data.videoPrice)
                it.setOnClickListener {
                    //跳转到视频播放界面
                    context?.let { it1 -> StartActivityUtil.toVideoPlayActivity(it1,list[position]) }
                }
            }else{
                println("!!!!!!!!!!!!!!!!!!onBindViewHolder")
                it.setNullData()
            }

        }



    }

    class PersonalSpaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}