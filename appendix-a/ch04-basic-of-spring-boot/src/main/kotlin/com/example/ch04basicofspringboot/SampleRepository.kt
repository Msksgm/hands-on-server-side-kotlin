package com.example.ch04basicofspringboot

import org.springframework.stereotype.Repository

interface SampleRepository {
    // DB から Person を取得した想定。サンプルアプリでは固定の値を返す
    fun getPersonFromDb(): PersonFromDb
}

// DI のためのアノテーション
@Repository
class SampleRepositoryImpl : SampleRepository {
    override fun getPersonFromDb(): PersonFromDb {
        return PersonFromDb("Alice", 23)
    }
}

data class PersonFromDb(val name: String, val age: Int)
