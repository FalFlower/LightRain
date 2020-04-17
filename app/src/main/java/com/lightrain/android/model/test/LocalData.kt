package com.lightrain.android.model.test

import com.lightrain.android.R
import com.lightrain.android.model.VideoBean
import com.lightrain.android.util.URLProviderUtils
import kotlin.random.Random

object LocalData {

    //获取视频轮播图数据
    fun getBannerData():List<VideoBean>{
        val dataList=ArrayList<VideoBean>()
        val videoList= getVideoData()
        val iconList=URLProviderUtils.getBannerImg()
        val l= iconList.indices
        for (i in l){
            videoList[i].videoIcon=iconList[i]
            dataList.add(videoList[i])
        }
        return dataList
    }
    //获取视频测试展示数据
    fun getVideoTestData():List<VideoBean>{
        val dataList=ArrayList<VideoBean>()
        val videoList= getVideoData()
        val iconList=URLProviderUtils.getVideoIcon()
        val l=getRandom(10)
        for (i in l){
            videoList[i!!].videoIcon=iconList[i]
            dataList.add(videoList[i])
        }
        return dataList
    }

    fun getRandomIcon():Int{
        val iconList= arrayOf(R.mipmap.icon_test_1,R.mipmap.icon_test_2,R.mipmap.icon_test_3,
            R.mipmap.icon_test_4,R.mipmap.icon_test_5,R.mipmap.icon_test_6,
            R.mipmap.icon_test_7,R.mipmap.icon_test_8,R.mipmap.icon_test_9,R.mipmap.icon_test_10)
        return iconList[Random.nextInt(10)]
    }

    private fun getRandom(max:Int): Array<Int?> {
        //生成指定范围的随机整形数组
        val n= 0 until max
        val l= arrayOfNulls<Int>(max)
        for (i in n) {
            l[i]=i
        }
        for (i in n) {
            val r=Random.nextInt(max)
            val change=l[i]
            l[i]=l[r]
            l[r]=change
        }
        return l
    }

    private fun getVideoData():List<VideoBean>{
        val videoList=ArrayList<VideoBean>()
        videoList.add(VideoBean("1586223285659148302","10000000006","做情绪的主人-七年级上","如何理解情绪的“双刃剑”影响? 做情绪的主人",10.0f,2520,9.9f,0,"http://zt-data.test.upcdn.net/cover/politics/politics_grade_seven_up2.png","http://zt-data.test.upcdn.net/video/politics/politics_grade_seven_up2.mp4","政治;七年级"))
        videoList.add(VideoBean("1586223295636224917","10000000006","人生当自强-七年级下","了解自强的含义及其重要性，知道自立自强是一种优秀的品质；知道对待生活需要自强不息的精神",10.0f,2199,9.9f,0,"http://zt-data.test.upcdn.net/cover/politics/politics_grade_seven_down1.png","http://zt-data.test.upcdn.net/video/politics/politics_grade_seven_down1.mp4","政治;七年级"))
        videoList.add(VideoBean("1586223322079609524","10000000006","国家的主人、广泛的权利-八年级下","人民是国家的主人，享有宪法赋予的管理国家的权力",10.0f,2266,19.9f,0,"http://zt-data.test.upcdn.net/cover/politics/politics_grade_eight_down1.png","http://zt-data.test.upcdn.net/video/politics/politics_grade_eight_down1.mp4","政治;八年级"))
        videoList.add(VideoBean("1586223959249856890","10000000004","人类活动对生物圈的影响-七年级下","分析人类活动对生态环境的影响，探究环境污染对生物的影响，探讨人口增长及其对环境的影响。",10.0f,2644,9.9f,0,"http://zt-data.test.upcdn.net/cover/biology/biology_grade_eight_down1.png","http://zt-data.test.upcdn.net/video/biology/biology_grade_eight_down1.mp4","生物;七年级"))
        videoList.add(VideoBean("1586223980859180364","10000000004","人体对外界环境的感知-七年级下","学习了有关人体的营养、消化、体内物质的运输和废物的排出等知识之后，向同学介绍的人体对外界环境的感知中眼与视觉的知识，从现象到本质，层层深入，既是知识的介绍，又是很好的一次情感、态度、价值观的教育，培养学生注意用眼卫生，关爱盲人的情感。",10.0f,2721,9.9f,0,"http://zt-data.test.upcdn.net/cover/biology/biology_grade_eight_down2.png","http://zt-data.test.upcdn.net/video/biology/biology_grade_eight_down2.mp4","生物;七年级"))
        videoList.add(VideoBean("1586224086674337850","10000000007","人民解放战争的胜利-八年级上","怎样认识人民解放战争是历史的选择，人民的选择？",10.0f,2079,0f,0,"http://zt-data.test.upcdn.net/cover/history/history_grade_eight_down2.png","http://zt-data.test.upcdn.net/video/history/history_grade_eight_up1.mp4","历史;八年级"))
        videoList.add(VideoBean("1586224101012767942","10000000007","殖民扩张与殖民地人民的抗争-九年级上","殖民扩张和掠夺是英国最早成为资本主义工业强国的重 要条件之一",10.0f,1736,0f,0,"http://zt-data.test.upcdn.net/cover/history/history_grade_nine_up1.png","http://zt-data.test.upcdn.net/video/history/history_grade_nine_up1.mp4","历史;九年级"))
        videoList.add(VideoBean("1586224177153418081","10000000009","人口与人种-七年级上","运用地图、资料，说出世界人口增长和分布的特点",10.0f,2047,0f,0,"http://zt-data.test.upcdn.net/cover/geography/geography_grade_seven_up1.png","http://zt-data.test.upcdn.net/video/geography/geography_grade_seven_up1.mp4","地理;七年级"))
        videoList.add(VideoBean("1586224041279299175","10000000004","传染病和免疫(上)-八年级下","“传染病”与“免疫”: 特点 预防措施 传染病流行的环节 病原体 传染病 传染源 免疫(抗原抗体) 流行性感冒流行的三个基本环节",10.0f,1370,19.9f,0,"http://zt-data.test.upcdn.net/cover/biology/biology_grade_nine_down1.png","http://zt-data.test.upcdn.net/video/biology/biology_grade_nine_down1.mp4","生物;八年级"))
        videoList.add(VideoBean("1586224049237480217","10000000004","传染病和免疫(下)-八年级下","“传染病”与“免疫”: 特点 预防措施 传染病流行的环节 病原体 传染病 传染源 免疫(抗原抗体) 流行性感冒流行的三个基本环节",10.0f,1594,19.9f,0,"http://zt-data.test.upcdn.net/cover/biology/biology_grade_nine_down2.png","http://zt-data.test.upcdn.net/video/biology/biology_grade_nine_down2.mp4","生物;八年级"))
        return videoList
    }

}