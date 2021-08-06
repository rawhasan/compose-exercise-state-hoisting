package com.example.statehoisting.data

import com.example.statehoisting.model.Person

class PeopleDataSource {
    fun loadPeople(): List<Person> {
        return listOf(
            Person("John Martin", 47),
            Person("Karen Sinniger", 56),
            Person("Steve Oswald", 61),
            Person("Florina Mendis", 37),
            Person("Jackie Smith", 41)
        )
    }
}