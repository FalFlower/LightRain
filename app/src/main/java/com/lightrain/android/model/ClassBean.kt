package com.lightrain.android.model

import android.os.Parcel
import android.os.Parcelable

data class ClassBean(var classId:String,var teacherId:String,var classTitle:String,
                     var classBrief:String,var classEvaluate:Double,var classTime:Int,
                     var classPrice:Double,var classLearningNum:Int,var classPic:String,var classVideoUrl:String)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(classId)
        parcel.writeString(teacherId)
        parcel.writeString(classTitle)
        parcel.writeString(classBrief)
        parcel.writeDouble(classEvaluate)
        parcel.writeInt(classTime)
        parcel.writeDouble(classPrice)
        parcel.writeInt(classLearningNum)
        parcel.writeString(classPic)
        parcel.writeString(classVideoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClassBean> {
        override fun createFromParcel(parcel: Parcel): ClassBean {
            return ClassBean(parcel)
        }

        override fun newArray(size: Int): Array<ClassBean?> {
            return arrayOfNulls(size)
        }
    }


}