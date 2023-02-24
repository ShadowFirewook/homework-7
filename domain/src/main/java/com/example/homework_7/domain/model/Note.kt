package com.example.homework_7.domain.model

class Note(
    val id: Int = DEFAULT_ID,
    var title: String,
    var description: String,
    val createdAt: Long
) : java.io.Serializable {
    companion object{
        const val DEFAULT_ID = 0;
    }
}
