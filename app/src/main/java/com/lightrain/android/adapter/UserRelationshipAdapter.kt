package com.lightrain.android.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.ui.activity.PersonalSpaceActivity
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StartActivityUtil
import com.lightrain.android.widget.UserRelationshipListView
import kotlinx.android.synthetic.main.view_user_relationship_list.view.*

class UserRelationshipAdapter(private val list: List<UserInfoBean>):
    RecyclerView.Adapter<UserRelationshipAdapter.UserRelationshipHolder>() {
    var context: Context?=null
    var view: UserRelationshipListView?=null
    class UserRelationshipHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRelationshipHolder {
        context=parent.context
        view= UserRelationshipListView(context)
        return UserRelationshipHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserRelationshipHolder, position: Int) {
        context?.let {
            ImageUtil.loadImageRoundedCorners(it,holder.itemView.urListViewIcon,list[position].userIcon)
            //根据用户等级来加载图标(低->高 黄、蓝、粉、红)
            ImageUtil.loadImage(it,holder.itemView.urListViewLabelImg,R.mipmap.ic_fire_red)
            holder.itemView.urListViewLayout.setOnClickListener {it1->
                //跳转到个人详细界面
                StartActivityUtil.toPersonalSpaceActivity(it,list[position])
            }
        }
        holder.itemView.urListViewNickname.text=list[position].nickname
        holder.itemView.urListViewSign.text=list[position].userSign

    }
}