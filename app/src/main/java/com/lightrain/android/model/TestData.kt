package com.lightrain.android.model

/**
 * 用于在未开好接口时来提供模拟数据
 * */
object TestData  {

    fun getTestClassBean():List<ClassBean>{
        val list=ArrayList<ClassBean>()
        val num=1..12
        for (i in num) {
            list.add(ClassBean("100000${10+i}","183400969${10+i}",
                "人生课堂之必备课程","当你还在观望，Kotlin已席卷全球，本课程将手把手带你运用模块化思想、MVP架构、以及当下最主流的技术框架开发一款完整电商APP，让你顺利的将Kotlin应用到实际项目中，高效开发新项目，快速改造老项目，做优秀的新一代Android工程师，在Kotlin领域中占得先机，走在前沿。",9.0,
            7200+i,i*100.0,100+i*10,
                "http://zt-data.test.upcdn.net/class$i.png",
                "http://zt-data.test.upcdn.net/video_demo.mp4"))
        }
        return list
    }

}