package com.lightrain.android.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.lightrain.android.R
import com.lightrain.android.model.ResponseException
import com.lightrain.android.model.UserInfoBean
import com.lightrain.android.net.HttpManager
import com.lightrain.android.net.ResponseHandler
import com.lightrain.android.net.ResponseStatus
import com.lightrain.android.util.JsonUtil
import com.lightrain.android.util.URLProviderUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.RequestBody


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}
