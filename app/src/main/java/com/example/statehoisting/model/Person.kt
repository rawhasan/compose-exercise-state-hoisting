package com.example.statehoisting.model

import java.util.*

data class Person(
    val name: String,
    val age: Int,
    val id: UUID = UUID.randomUUID()
)
