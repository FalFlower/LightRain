package com.lightrain.android.model

import android.os.Parcel
import android.os.Parcelable

data class VideoBean(var videoId:String, var username:String, var videoTitle:String,
                     var videoBrief:String, var videoScoreAva:Float, var videoTime:Int,
                     var videoPrice:Float, var videoLearningNum:Int, var videoIcon:String,
                     var videoUrl:String, var videoLabel:String)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoId)
        parcel.writeString(username)
        parcel.writeString(videoTitle)
        parcel.writeString(videoBrief)
        parcel.writeFloat(videoScoreAva)
        parcel.writeInt(videoTime)
        parcel.writeFloat(videoPrice)
        parcel.writeInt(videoLearningNum)
        parcel.writeString(videoIcon)
        parcel.writeString(videoUrl)
        parcel.writeString(videoLabel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoBean> {
        override fun createFromParcel(parcel: Parcel): VideoBean {
            return VideoBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoBean?> {
            return arrayOfNulls(size)
        }
    }


}