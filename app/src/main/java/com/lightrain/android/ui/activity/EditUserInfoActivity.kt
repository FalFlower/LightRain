package com.lightrain.android.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.lightrain.android.LightRainApplication
import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.*
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import kotlinx.android.synthetic.main.dialog_edit.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class EditUserInfoActivity :BaseActivity(), ResponseHandler, Runnable {
    var userInfo:UserInfoBean?=LightRainApplication.userInfoBean
    var picChooseUtil:PicChooseUtil= PicChooseUtil(this)
    override fun getLayoutId(): Int {
        return R.layout.activity_edit_user_info
    }

    override fun initData() {
        initToolbar()
    }

    override fun initListener() {
        editUserInfoIconLayout.setOnClickListener { editUserIcon() }//修改头像
        editUserInfoSchool.setOnClickListener { editNickOrSignOrSchool(2) }//修改学校
        editUserInfoSign.setOnClickListener { editNickOrSignOrSchool(0) }//修改签名
        editUserInfoNickname.setOnClickListener { editNickOrSignOrSchool(1) }//修改昵称
        editUserInfoGender.setOnClickListener { editGender() }//修改性别
        editUserInfoBirthday.setOnClickListener { editUserBirthday() }//修改生日
        editUserInfoLocation.setOnClickListener { startActivity<EditUserInfoLocationActivity>() }//修改地区
        editUserInfoAccount.setOnClickListener {  }//修改密码等

    }
    //修改头像
    private fun editUserIcon(){
        picChooseUtil.showPicChooseDialog()
        picChooseUtil.setOnChooseResultListener(object :PicChooseUtil.OnChooseResultListener{
            override fun onSuccess(url: String?) {
                //更新用户信息
                url?.let {
                    val response=JsonUtil.getUploadResponse(it)
                    val iconUrl=PicChooseUtil.DOMAIN_NAME+response.url
                    userInfo?.userIcon=iconUrl
                    userInfo?.let {
                        HttpManager.manager.sendRequestByPost(URLProviderUtils.getUserInfoUpdate(),
                            UserInfoUtil.getUpdateUerInfoBody(it),ResponseStatus.UPDATE,this@EditUserInfoActivity)
                    }
                }
            }

            override fun onFail(msg: String?) {
                runOnUiThread { msg?.let { toast("上传头像失败") } }
                println("上传头像失败：$msg")
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            picChooseUtil.onActivityResult(requestCode, resultCode, data)
        }else{
            picChooseUtil.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //修改生日
    private fun editUserBirthday() {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            userInfo?.let {
                it.userBirthday="$year-${monthOfYear+1}-$dayOfMonth"
                HttpManager.manager.sendRequestByPost(URLProviderUtils.getUserInfoUpdate(),
                    UserInfoUtil.getUpdateUerInfoBody(it),
                    ResponseStatus.UPDATE,this)
            }

        }, y, m, d)

        dpd.show()
    }



    private fun initView() {
        userInfo?.let {
            ImageUtil.loadImageRoundedCorners(this,editUserInfoIcon,it.userIcon)
            if (it.userSchool == "")
                editUserInfoSchool.setData("学校","请选择")
            else
                editUserInfoSchool.setData("学校",it.userSchool)
            //需要加签名的判断
            editUserInfoSign.setData("签名","这位童鞋很懒，木有签名的说~")
            if (it.nickname != "")
                editUserInfoNickname.setData("昵称",it.nickname)
            else
                editUserInfoNickname.setData("昵称","请输入昵称")
            if (it.userGender==UserInfoUtil.USER_GENDER_WOMAN)
                editUserInfoGender.setData("性别","女")
            else
                editUserInfoGender.setData("性别","男")
            editUserInfoBirthday.setData("生日",it.userBirthday)
            editUserInfoAge.setData("年龄",it.userAge.toString())
            //需要加地区的判断
            editUserInfoLocation.setData("地区","请选择")
            editUserInfoAccount.setData("账户与安全","账号绑定管理，密码修改")

        }
    }
    //设置Toolbar
    private fun initToolbar() {
        editUserInfoToolbar.title="个人资料"
        setSupportActionBar(editUserInfoToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        editUserInfoToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_nobar)
        editUserInfoToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    //修改签名或者昵称或者学校
    private fun editNickOrSignOrSchool(i: Int) {
        val title=when(i){
            0->"编写签名"
            1->"修改昵称"
            2->"所在高校"
            else -> ""
        }
        val dialog=AlertDialog.Builder(this)
        val dialogView=LayoutInflater.from(this).inflate(R.layout.dialog_edit,null)
        userInfo?.let {
            when(i){
                0->{
                    dialogView.dialogEditText.hint="请输入签名内容(<32)"
                    //如果签名不为空签名不为空,就先把签名加载上
                    dialogView.dialogEditText.filters = arrayOf<InputFilter>(LengthFilter(32))
                }
                1->{
                    dialogView.dialogEditText.hint="请输入昵称(<8)"
                    if (it.nickname.isNotEmpty()){ dialogView.dialogEditText.setText(it.nickname)}
                    dialogView.dialogEditText.filters = arrayOf<InputFilter>(LengthFilter(8))
                }
                2->{
                    dialogView.dialogEditText.hint="请输入学校"
                    if (it.userSchool.isNotEmpty()){ dialogView.dialogEditText.setText(it.userSchool)}
                    dialogView.dialogEditText.filters = arrayOf<InputFilter>(LengthFilter(32))
                }
            }
        }
        dialog.setTitle(title)
        dialog.setView(dialogView)
        dialog.setPositiveButton("完成"
        ) { _, _ ->
            userInfo?.let {
                when(i){
                    0->it.userSign=dialogView.dialogEditText.text.toString()//更新签名
                    1->it.nickname=dialogView.dialogEditText.text.toString()
                    2->it.userSchool=dialogView.dialogEditText.text.toString()
                }
                HttpManager.manager.sendRequestByPost(URLProviderUtils.getUserInfoUpdate(),
                    UserInfoUtil.getUpdateUerInfoBody(it),ResponseStatus.UPDATE,this)
            }

        }
        dialog.show()
    }
    //修改性别
    private fun editGender() {
        val items= arrayOf("女","男")
        val dialog=AlertDialog.Builder(this)
        dialog.setTitle("性别")
        dialog.setItems(items
        ) { _, which ->
            userInfo?.let {
                when(which){
                    UserInfoUtil.USER_GENDER_WOMAN->{//修改性别为女
                        it.userGender= UserInfoUtil.USER_GENDER_WOMAN}
                    UserInfoUtil.USER_GENDER_MAN->{//修改性别为男
                        it.userGender= UserInfoUtil.USER_GENDER_MAN}
                }
                HttpManager.manager.sendRequestByPost(URLProviderUtils.getUserInfoUpdate(),
                    UserInfoUtil.getUpdateUerInfoBody(it),ResponseStatus.UPDATE,this)
            }
        }
        dialog.show()
    }

    override fun onError(msg: String?) {
        toast("修改失败：${msg}")
//        toast("修改失败：${msg?.let { JsonUtil.getResponseException(it).message }}")
    }

    override fun onSuccess(status: ResponseStatus, result: String?) {
        //更新API没有返回个人信息
        ThreadUtil.runOnMainThread(this)
    }

    override fun run() {
        toast("修改成功")
        initView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    override fun onDestroy() {
        LightRainApplication.userInfoBean=userInfo //将修改过的信息传递出去
        super.onDestroy()
    }
}