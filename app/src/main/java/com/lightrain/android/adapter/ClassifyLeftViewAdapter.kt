package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.view_classify_left.view.*
import org.jetbrains.anko.sdk27.coroutines.onFocusChange
import org.jetbrains.anko.textColor

class ClassifyLeftViewAdapter(private var list:Array<String>):
    RecyclerView.Adapter<ClassifyLeftViewAdapter.ClassifyLeftViewHolder>() {
    companion object{
        private var upHolder:ClassifyLeftViewHolder?=null
    }
    var context:Context?=null
    private var setOnItemClickListener:SetOnItemClickListener?=null

    fun setSetOnItemClickListener(setOnItemClickListener : SetOnItemClickListener){
        this.setOnItemClickListener=setOnItemClickListener
    }
    //自定义接口
    interface SetOnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ClassifyLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyLeftViewHolder {
        context=parent.context
        val view=LayoutInflater.from(context).inflate(R.layout.view_classify_left,null)
        return ClassifyLeftViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ClassifyLeftViewHolder, position: Int) {
        holder.itemView.classifyLeftViewSelectTip.visibility= INVISIBLE
        holder.itemView.classifyLeftViewTitle.text=list[position]
        holder.itemView.classifyLeftViewItem.setOnClickListener {
            upHolder?.let {
                it.itemView.classifyLeftViewSelectTip.visibility= INVISIBLE
                context?.resources?.getColor(R.color.colorText)?.let { it1 ->
                    it.itemView.classifyLeftViewTitle.setTextColor(
                        it1
                    )
                }
            }
            upHolder=holder
            setOnItemClickListener?.onItemClick(position)
            holder.itemView.classifyLeftViewSelectTip.visibility= VISIBLE
            context?.resources?.getColor(R.color.colorPrimary)?.let { it1 ->
                holder.itemView.classifyLeftViewTitle.setTextColor(
                    it1
                )
            }
        }
    }


}