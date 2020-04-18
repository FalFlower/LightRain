package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.model.test.LocalData
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StartActivityUtil
import kotlinx.android.synthetic.main.view_classify_right.view.*

class ClassifyRightViewAdapter(private var status:Int,private var title:String):
    RecyclerView.Adapter<ClassifyRightViewAdapter.ClassifyRightViewHolder>() {
    var labelList:Array<String>?=null
    var context: Context?=null

    companion object{
        private const val STATUS_COMPOSITION=0
        private const val STATUS_CHINESE=1
        private const val STATUS_MATH=2
        private const val STATUS_ENGLISH=3
        private const val STATUS_BIOLOGY=4
        private const val STATUS_CHEMISTRY=5
        private const val STATUS_PHYSICS=6
        private const val STATUS_POLITICS=7
        private const val STATUS_HISTORY=8
        private const val STATUS_GEOGRAPHY=9
    }

    class ClassifyRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifyRightViewHolder {
        context=parent.context
        val view= LayoutInflater.from(context).inflate(R.layout.view_classify_right,null)
        return ClassifyRightViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when(status){
            STATUS_COMPOSITION->6
            STATUS_CHINESE->6
            STATUS_MATH->6
            STATUS_ENGLISH->6
            STATUS_BIOLOGY->4
            STATUS_CHEMISTRY->2
            STATUS_PHYSICS->3
            STATUS_POLITICS->5
            STATUS_HISTORY->6
            STATUS_GEOGRAPHY->4
            else -> 6
        }
    }

    override fun onBindViewHolder(holder: ClassifyRightViewHolder, position: Int) {
        setLabel()
        context?.let { ImageUtil.loadImage(it,holder.itemView.classifyRightViewImg,LocalData.getRandomIcon()) }
        labelList?.let {
            holder.itemView.classifyRightViewTitle.text=it[position]
        }

        holder.itemView.classifyRightViewLayout.setOnClickListener {
            //跳转到详情分类展示界面
            context?.let { it1 -> labelList?.let { list ->
                StartActivityUtil.toSearchInfoActivity(it1,title, "$title;${list[position]}")
            } }
        }
    }

    private fun setLabel(){
        val TWO_LIST= arrayOf("九年级上","九年级下")
        val THREE_LIST= arrayOf("八年级上","八年级下","九年级")
        val FOUR_LIST= arrayOf("七年级上","七年级下","八年级上","八年级下")
        val FIVE_LIST= arrayOf("七年级上","七年级下","八年级上","八年级下","九年级")
        val FOUR_LIST_H= arrayOf("八年级上","八年级下","九年级上","九年级下")
        val SIX_LIST= arrayOf("七年级上","七年级下","八年级上","八年级下","九年级上","九年级下")

        labelList= when(status){
            STATUS_COMPOSITION->{SIX_LIST}
            STATUS_CHINESE->{SIX_LIST}
            STATUS_MATH->{SIX_LIST}
            STATUS_ENGLISH->{SIX_LIST}
            STATUS_BIOLOGY->{FOUR_LIST_H}
            STATUS_CHEMISTRY->{TWO_LIST}
            STATUS_PHYSICS->{THREE_LIST}
            STATUS_POLITICS->{FIVE_LIST}
            STATUS_HISTORY->{SIX_LIST}
            STATUS_GEOGRAPHY->{FOUR_LIST}
            else -> SIX_LIST
        }
    }
}