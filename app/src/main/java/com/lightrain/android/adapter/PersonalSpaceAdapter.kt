package com.lightrain.android.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.model.ClassBean
import com.lightrain.android.ui.activity.VideoPlayActivity
import com.lightrain.android.util.ClassBeanUtil
import com.lightrain.android.view.PersonalSpaceListView
import org.jetbrains.anko.startActivity

class PersonalSpaceAdapter(private val list:List<ClassBean>) :RecyclerView.Adapter<PersonalSpaceAdapter.PersonalSpaceHolder>(){
    var view:PersonalSpaceListView?=null
    var context:Context?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalSpaceHolder {
        context=parent.context
        view=PersonalSpaceListView(context)
        return PersonalSpaceHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PersonalSpaceHolder, position: Int) {
        view?.let {
            val data=list[position]
            it.setData(data.classPic,data.classTitle,data.classLearningNum,data.classPrice)
            it.setOnClickListener {
                //跳转到视频播放界面
                context?.let { it1 -> ClassBeanUtil.toVideoPlayActivity(it1,list[position]) }
            }
        }

    }

    class PersonalSpaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}