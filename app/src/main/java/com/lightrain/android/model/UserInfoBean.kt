package com.lightrain.android.model

import android.os.Parcel
import android.os.Parcelable

data class UserInfoBean(var username:String,var password:String,var nickname:String,var userIcon:String,
                        var userGender:Int,var userBirthday:String,var userAge:Int,
                        var userSchool:String,var userStatus:Int,var userSign:String,var userLocation:String,
                        var userIdentity:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(nickname)
        parcel.writeString(userIcon)
        parcel.writeInt(userGender)
        parcel.writeString(userBirthday)
        parcel.writeInt(userAge)
        parcel.writeString(userSchool)
        parcel.writeInt(userStatus)
        parcel.writeString(userSign)
        parcel.writeString(userLocation)
        parcel.writeInt(userIdentity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoBean> {
        override fun createFromParcel(parcel: Parcel): UserInfoBean {
            return UserInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoBean?> {
            return arrayOfNulls(size)
        }
    }

}