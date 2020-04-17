package com.lightrain.android.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.lightrain.android.R
import kotlinx.android.synthetic.main.view_choose_area_list.view.*

class ChooseAreaAdapter(var list: List<String>) :BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        if (convertView==null){
            view=View.inflate(parent?.context,R.layout.view_choose_area_list,null)
            view?.viewChooseAreaList?.text=list[position]
            return view
        }else{
            convertView.viewChooseAreaList?.text=list[position]
            return convertView
        }


    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

}