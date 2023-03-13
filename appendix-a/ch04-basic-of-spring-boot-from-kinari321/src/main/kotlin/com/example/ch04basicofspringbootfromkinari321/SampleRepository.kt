package com.example.ch04basicofspringbootfromkinari321

import org.springframework.stereotype.Repository

interface SampleRepository {
    fun getPersonFromDb(): PersonFromDb
}

@Repository
class SampleRepositoryImpl : SampleRepository {
    override fun getPersonFromDb(): PersonFromDb {
        return PersonFromDb("Alice", 23)
    }
}

data class PersonFromDb(val name: String, val age: Int)
