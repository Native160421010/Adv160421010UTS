package com.wildfire.adv160421010uts.model

data class News(
    var id:Int?,
    var title:String?,
    var desc:String?,
    var creator:String?,
    var content: List<List<String>>?
)

data class User(
    var nama:String?,
    var front_Name:String?,
    var back_Name:String?,
    var password:String?,
)
