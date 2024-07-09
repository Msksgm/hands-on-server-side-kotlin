package com.example.sec03_kotlin_spring_crud_web_api

/**
 * Web API で利用するデータモデル
 *
 * Repository によるDB とのやりとり、Service を介したデータの受け渡し、Controller のレスポンスに利用する。
 *
 * @property id カスタマーID
 * @property firstName 名前
 * @property lastName 名字
 */
data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
)
