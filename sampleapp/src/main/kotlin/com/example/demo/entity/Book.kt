package com.example.demo.entity

import org.springframework.data.annotation.Id

data class Book(
    @Id
    val id: Long,
    val name: String
) {
    constructor(name: String) : this(0, name)
}
