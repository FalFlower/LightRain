package com.lightrain.android.model

import java.util.*

class ResultResponse<T> {
    var code:Int=0
    var msg:String=""
    var data: T?=null

}