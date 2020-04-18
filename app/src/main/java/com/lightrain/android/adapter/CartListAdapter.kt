package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.model.VideoBean
import com.lightrain.android.util.ImageUtil
import kotlinx.android.synthetic.main.view_cart.view.*

class CartListAdapter (private val context: Context,private val list:List<VideoBean>) : RecyclerView.Adapter<CartListAdapter.CartListHolder>() {
    companion object{
        private var viewList=ArrayList<View>()
        private var selectStatusList=ArrayList<Boolean>()
        var allPrice=0f
    }
    class CartListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.view_cart,null)
        val size= list.indices
        for (i in size)
            selectStatusList.add(false)
        return CartListHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: CartListHolder, position: Int) {
        viewList.add(holder.itemView)
        holder.itemView.cartListSelect.setOnClickListener {
            setSelectView(selectStatusList[position],position)
        }
        ImageUtil.loadImageRoundedCornersDIY(context,holder.itemView.cartListIcon,list[position].videoIcon,25,10)
        holder.itemView.cartListTitle.text=list[position].videoTitle
        holder.itemView.cartListPrice.text="￥${list[position].videoPrice}"

    }

    private fun setSelectView(status: Boolean, position: Int){
        viewList[position].let {
            if (status){
                //true为选中状态，再次点击因该加载为默认状态
                ImageUtil.loadImage(context,it.cartListIcon,R.mipmap.ic_no_select)
                selectStatusList[position]=false
                allPrice-=list[position].videoPrice
            }else{
                //false为默认状态，再次点击因该加载为选中状态
                ImageUtil.loadImage(context,it.cartListIcon,R.mipmap.ic_select_green)
                selectStatusList[position]=true
                allPrice+=list[position].videoPrice
            }
        }
    }
    fun getAllSelectPrice():Float{
        return allPrice
    }
}