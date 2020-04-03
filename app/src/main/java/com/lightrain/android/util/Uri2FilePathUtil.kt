package com.lightrain.android.util

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import java.lang.Exception


class Uri2FilePathUtil{

    fun getFilePathByUri(context: Context,uri: Uri): String? {
        var path:String?=null
        //以file://开头的
        if (ContentResolver.SCHEME_FILE == uri.scheme){
            path=uri.path
            return path
        }

        //以content://开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.scheme)&&Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            val cursor=context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA),null,null,null)
            cursor?.let {
                if (cursor.moveToNext()){
                    val columnIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    if (columnIndex>-1){
                        path=cursor.getString(columnIndex)
                    }
                }
                cursor.close()
            }
            return path
        }

        //4.4及之后的 是以content://开头的,比如content://com.android.providers.media.documents/document/image%3A20139
        if (ContentResolver.SCHEME_CONTENT == uri.scheme &&Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            if (DocumentsContract.isDocumentUri(context,uri)){
                if (isExternalStorageDocument(uri)){
                    //ExternalStorageProvider
                    val docId=DocumentsContract.getDocumentId(uri)
                    val split=docId.split(":")
                    val type=split[0]
                    if ("primary".equals(type,true)){
                        path=Environment.getExternalStorageDirectory().toString() +"/"+split[1] //未知可行
                        return path
                    }
                }else {
                    //mediaProvider
                    val docId=DocumentsContract.getDocumentId(uri)
                    val split=docId.split(":")
                    val type=split[0]
                    var contentUri:Uri?=null
                    if ("image"==type){
                        contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }else if("video"==type){
                        contentUri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }else if("audio"==type){
                        contentUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection="_id=?"
                    val selectionArgs= arrayOf(split[1])
                    path= contentUri?.let { getDataColumn(context, it,selection,selectionArgs) }
                    return path
                }
            }
        }
        return null
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri,
        selection: String,
        selectionArgs: Array<String>
    ): String? {
        var cursor:Cursor?=null
        val column="_data"
        val projection= arrayOf(column)
        try {
            cursor=context.contentResolver.query(uri,projection,selection,selectionArgs,null)
            if (cursor!=null&&cursor.moveToNext()){
                val column_index=cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        }finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}