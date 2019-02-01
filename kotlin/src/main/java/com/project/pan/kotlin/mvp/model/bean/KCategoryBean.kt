package com.project.pan.kotlin.mvp.model.bean

import java.io.Serializable

/**
 * Created by xuhao on 2017/11/29.
 * desc:分类 Bean
 */
 data class KCategoryBean(
    val alias: Any,
    val bgColor: String,
    val bgPicture: String,
    val defaultAuthorId: Int,
    val description: String,
    val headerImage: String,
    val id: Int,
    val name: String
) :Serializable