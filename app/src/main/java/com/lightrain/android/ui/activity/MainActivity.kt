package com.lightrain.android.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.contains

import com.lightrain.android.R
import com.lightrain.android.base.BaseActivity
import com.lightrain.android.base.BaseFragment
import com.lightrain.android.ui.fragment.InfoFragment
import com.lightrain.android.util.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    companion object{
        var app:MainActivity?=null
    }

    override fun getLayoutId(): Int {
        app=this
        return R.layout.activity_main
    }

    override fun initData() {
        applyPermission()
    }

    override fun initListener() {
        //设置Tab监听
        mainBottomBar.setOnTabSelectListener {
            //it代表参数taiId
            val transaction=supportFragmentManager.beginTransaction()
            FragmentUtil.fragmentUtil.getFragment(it)?.let { fragment ->
                transaction.replace(R.id.mainContainer,
                    fragment,it.toString())
                transaction.commit()
            }

        }

    }
    //动态申请权限
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    )
    private val REQUEST_PERMISSION_CODE = 1 //请求状态码
    private fun applyPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }


}
