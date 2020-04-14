package com.lightrain.android.util

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.lightrain.android.LightRainApplication
import com.upyun.library.common.Params
import com.upyun.library.common.UploadEngine
import com.upyun.library.listener.UpCompleteListener
import com.upyun.library.listener.UpProgressListener
import com.upyun.library.utils.UpYunUtils
import java.io.File
import java.io.IOException
import java.net.URI


class PicChooseUtil {
    companion object{
        val BUCKET="zt-data"
        val SAVE_KEY="/uploads/${LightRainApplication.userInfoBean?.username}/icon/{year}{mon}{day}/{random32}"
        val RETURN_URL = "httpbin.org/post"
        val DOMAIN_NAME="http://zt-data.test.upcdn.net/"
    }
    private var fragment:Fragment?=null
    private var activity:Activity?=null

    private var cameraFileUri: Uri?=null
    private var cropUri: Uri? = null
    private val FROM_CAMERA = 2
    private val FROM_ALBUM = 1
    private val CROP = 0

    constructor(activity: Activity){this.activity=activity}
    constructor(fragment: Fragment){
        this.activity=fragment.activity
        this.fragment=fragment
    }

    fun showPicChooseDialog(){
        val items= arrayOf("相机","相册")
        val dialog= activity?.let { AlertDialog.Builder(it) }
        dialog?.let {
            it.setTitle("修改头像")
            it.setItems(items
            ) { _, which ->
                when(which){
                    0->{takePicFromCamera()}//从相机选择
                    1->{takePicFromAlbum()}//从相册选择
                }

            }
            it.show()
        }

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FROM_CAMERA) {
            //从相机选择返回。
            if (resultCode == Activity.RESULT_OK) {
                cameraFileUri?.let {upload(it)}
                //startCrop(cameraFileUri)
            }
        } else if (requestCode ==FROM_ALBUM) {
            //从相册选择返回。
            if (resultCode == Activity.RESULT_OK) {
                val uri = data?.data
                uri?.let {upload(it)}
                //startCrop(uri)
            }
        } else if (requestCode == CROP) {
            //裁剪结束
            if (resultCode == Activity.RESULT_OK) {
                //上传到服务器保存起来
                cropUri?.let { upload(it) }
            }
        }
    }

    interface OnChooseResultListener {
        fun onSuccess(url: String?)
        fun onFail(msg: String?)
    }

    private var mOnChooserResultListener: OnChooseResultListener? = null

    fun setOnChooseResultListener(l: OnChooseResultListener?) {
        mOnChooserResultListener = l
    }


    //上传到服务器保存起来
    private fun upload(uri: Uri) {
        val path=activity?.applicationContext?.let { Uri2FilePathUtil().getFilePathByUri(it,uri)}
        val temp=File(path);
        val paramsMap= HashMap<String,Any>()
        paramsMap[Params.BUCKET] = BUCKET//上传空间名
        paramsMap[Params.SAVE_KEY] = SAVE_KEY//保存路径
        paramsMap[Params.CONTENT_MD5] = UpYunUtils.md5Hex(temp)
        paramsMap[Params.RETURN_URL] = RETURN_URL
        //进度回调，可为空
        val progressListener = UpProgressListener { bytesWrite, contentLength ->
//                bp_form.setProgress((100 * bytesWrite / contentLength).toInt())
//                tv_form.setText((100 * bytesWrite) / contentLength + "%");
//                Log.e(TAG, 100 * bytesWrite / contentLength.toString() + "%")
//                Log.e(TAG, "$bytesWrite::$contentLength")
        }
        //结束回调，不可为空
        val completeListener = UpCompleteListener { isSuccess, response, error ->
                try {
                    var result: String? = null
                    if (response != null) {
                        result = response.body?.string()
                        mOnChooserResultListener?.onSuccess(result)
                    } else if (error != null) {
                        result = error.toString()
                        mOnChooserResultListener?.onFail(result)
                    }
                    println("upLoad isSuccess:$isSuccess result:$result")

                } catch (e: IOException) {
                    e.printStackTrace()
                    mOnChooserResultListener?.onFail(e.message)
                }
        }

        //表单上传（本地签名方式）
        UploadEngine.getInstance().formUpload(temp, paramsMap,
            "zt847269989",
            UpYunUtils.md5("dQnEAE9Nv4pU9FvQvzSjfRDA3H8eUCg8"),
            completeListener, progressListener);
    }

    //从相册选择
    private fun takePicFromAlbum() {
        val picIntent=Intent("android.intent.action.GET_CONTENT")
        picIntent.type = "image/*"
        if (fragment==null){
            activity?.startActivityForResult(picIntent,FROM_ALBUM)
        }else{
            fragment!!.startActivityForResult(picIntent,FROM_ALBUM)
        }
    }
    //从相机选择
    private fun takePicFromCamera() {
        cameraFileUri=createAlbumUri()
        val intentCamera=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val currentApiVersion=android.os.Build.VERSION.SDK_INT
        if (currentApiVersion<24){
            //如果当前系统版本低于7.0
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,cameraFileUri)
        }else{
            //系统版本高于7.0
            val contentValues=ContentValues(1)
            contentValues.put(MediaStore.Images.Media.DATA,cameraFileUri?.path)
            val uri=getImageContentUri(cameraFileUri)
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        }
        if (fragment==null){
            activity?.startActivityForResult(intentCamera,FROM_CAMERA)
        }else{
            fragment!!.startActivityForResult(intentCamera,FROM_CAMERA)
        }
    }
    /**
     * 转换 content:// uri
     */
    private fun getImageContentUri(uri: Uri?): Uri {
        val filePath=uri?.path
        val cursor=activity?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA+"=?",
            arrayOf(filePath),null
        )
        if (cursor!=null&&cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri=Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri,""+id)
        }else{
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, filePath)
            return this.activity?.contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )!!
        }
    }

    private fun createAlbumUri(): Uri? {
        val dirPath=Environment.getExternalStorageDirectory().toString()+"/"+activity?.application?.packageName
        val file=File(dirPath)
        if (!file.exists()){
            file.mkdir()
        }

        var id=""
        LightRainApplication.userInfoBean?.let {
            id=it.username
        }
        val fileName= "$id.jpg"
        val picFile=File(dirPath,fileName)
        if (picFile.exists()){
            picFile.delete()
        }
        return Uri.fromFile(picFile)
    }

    //开始裁切
//    private fun startCrop(uri: Uri?) {
//        cropUri = createCropUri()
//        val intent = Intent("com.android.camera.action.CROP")
//        intent.putExtra("crop", "true")
//        intent.putExtra("aspectX", 300)
//        intent.putExtra("aspectY", 300)
//        intent.putExtra("outputX", 300)
//        intent.putExtra("outputY", 300)
//        intent.putExtra("return-data", false)
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
//
//        val currentApiVersion = Build.VERSION.SDK_INT
//        if (currentApiVersion < 24) {
//            //小于7.0的版本
//            intent.setDataAndType(uri, "image/*")
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
//            if (fragment == null) {
//                activity?.startActivityForResult(intent, CROP)
//            } else {
//                fragment!!.startActivityForResult(intent, CROP)
//            }
//        } else {
//            //大于7.0的版本
//            val scheme = uri!!.scheme
//            if (scheme == "content") {
//                intent.setDataAndType(uri, "image/*")
//            } else {
//                val contentUri = getImageContentUri(uri)
//                intent.setDataAndType(contentUri, "image/*")
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
//            if (fragment == null) {
//                activity?.startActivityForResult(intent, CROP)
//            } else {
//                fragment!!.startActivityForResult(intent, CROP)
//            }
//        }
//    }
    //创建裁切的URI
//    private fun createCropUri(): Uri? {
//        val dirPath = Environment.getExternalStorageDirectory()
//            .toString() + "/" + activity?.application?.applicationInfo?.packageName
//        println("!!!!createCropUri dirPath:$dirPath")
//        val dir = File(dirPath)
//        if (!dir.exists()) {
//            dir.mkdirs()
//        }
//
//        var id = ""
//        LightRainApplication.userInfoBean?.let {
//            id = it.username
//        }
//
//        val fileName = id + "_crop.jpg"
//        val picFile = File(dirPath, fileName)
//        println("!!!!createCropUri dirPath:$dirPath fileName:$fileName")
//        if (picFile.exists()) {
//            picFile.delete()
//        }
//        try {
//            picFile.createNewFile()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        println("!!!!createCropUri picFile:$dirPath")
//        return Uri.fromFile(picFile)
//    }


}