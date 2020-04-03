package com.lightrain.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.adapter.ChooseAreaAdapter
import com.lightrain.android.model.weather.City
import com.lightrain.android.model.weather.County
import com.lightrain.android.model.weather.Province
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.ui.activity.EditUserInfoLocationActivity
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_choose_area.*
import kotlinx.android.synthetic.main.fragment_choose_area.view.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class ChooseAreaFragment : Fragment(), ResponseHandler {
    companion object{
        val LEVEL_PROVINCE=0
        val LEVEL_CITY=1
        val LEVEL_COUNTY=2
    }

    private val dataList=ArrayList<String>()
    private var adapter:ChooseAreaAdapter?=null
    private var provinceList:List<Province>?=null
    private var cityList:List<City>?=null
    private var countyList:List<County>?=null

    private var currentLevel=0 //当前选中的级别
    private var selectProvince:Province?=null
    private var selectCity:City?=null

    private var userInfoLocation=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=View.inflate(context, R.layout.fragment_choose_area,null)
        adapter= ChooseAreaAdapter(dataList)
        view.chooseAreaListView.adapter=adapter
        initToolbar(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        chooseAreaListView.setOnItemClickListener { parent, view, position, id ->
            when(currentLevel){
                LEVEL_PROVINCE->{
                    selectProvince= provinceList?.get(position)
                    queryCities()
                }
                LEVEL_CITY->{
                    selectCity=cityList?.get(position)
                    queryCounties()
                }
                LEVEL_COUNTY->{
                    userInfoLocation+=countyList?.get(position)?.countyName
                    updateData()
                    activity?.finish()
                }
                else->{}
            }
        }
        queryProvinces()
    }
    //更新地区
    private fun updateData() {
        val userInfo=LightRainApplication.userInfoBean
//        userInfo?.let {
//
//        }
        toast("修改成功 :$userInfoLocation")
    }

    //查询所有的区
    private fun queryCounties() {
        currentLevel= LEVEL_COUNTY
        userInfoLocation += selectCity?.cityName+" "
        selectCity?.let {
            HttpManager.manager.sendRequestByGet(URLProviderUtils.getCounty(it.provinceId,it.cityCode),
                ResponseStatus.COUNTY,this)
        }

    }
    //查询所有的市
    private fun queryCities() {
        currentLevel= LEVEL_CITY
        userInfoLocation= selectProvince?.provinceName.toString()+" "
        selectProvince?.provinceCode?.let { URLProviderUtils.getCities(it) }?.
        let { HttpManager.manager.sendRequestByGet(it,ResponseStatus.CITY,this) }

    }
    //查询所有的省
    private fun queryProvinces() {
        currentLevel= LEVEL_PROVINCE
        HttpManager.manager.
            sendRequestByGet(URLProviderUtils.getProvinces(),ResponseStatus.PROVINCE,this)
    }
    private fun initToolbar(view: View) {
        view.chooseAreaToolbar.title="地区"
        EditUserInfoLocationActivity.app?.let {
        it.setSupportActionBar(view.chooseAreaToolbar)
        it.supportActionBar?.setDisplayShowHomeEnabled(true)
            view.chooseAreaToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_nobar)
            view.chooseAreaToolbar.setNavigationOnClickListener {
                when(currentLevel){
                    LEVEL_COUNTY->{ queryCities() }
                    LEVEL_CITY->{ queryProvinces() }
                    LEVEL_PROVINCE->{activity?.finish()}
                }
            }
        }
    }

    override fun onError(msg: String?) {
        context?.toast("$msg")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        result?.let {
            when(status){
                ResponseStatus.PROVINCE->{
                    provinceList=JsonUtil.getProvinceResponse(it)
                    runOnUiThread {
                        dataList.clear()
                        provinceList!!.forEach {
                            dataList.add(it.provinceName)
                        }
                        adapter?.notifyDataSetChanged()
                        chooseAreaListView.setSelection(0)
                    }

                }
                ResponseStatus.CITY->{cityList=
                    selectProvince?.provinceCode?.let { it1 -> JsonUtil.getCityResponse(it, it1) }
                    runOnUiThread{
                        dataList.clear()
                        cityList!!.forEach {
                            dataList.add(it.cityName)
                        }
                        adapter?.notifyDataSetChanged()
                        chooseAreaListView.setSelection(0)
                    }
                }
                ResponseStatus.COUNTY->{countyList=
                    selectCity?.cityCode?.let { it1 -> JsonUtil.getCountyResponse(it, it1) }
                    runOnUiThread{
                        dataList.clear()
                        countyList!!.forEach {
                            dataList.add(it.countyName)
                        }
                        adapter?.notifyDataSetChanged()
                        chooseAreaListView.setSelection(0)
                    }

                }
                else -> {}
            }
        }

    }




}