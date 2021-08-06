package com.example.statehoisting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.statehoisting.data.PeopleDataSource
import com.example.statehoisting.model.Person

class PeopleViewModel : ViewModel() {
    private var _people = MutableLiveData(listOf<Person>())
    val people: LiveData<List<Person>>
        get() = _people

    init {
        _people.value = PeopleDataSource().loadPeople()
    }

    fun addPerson(person: Person) {
        _people.value = _people.value!! + listOf(person)
    }

    fun removePerson(person: Person) {
        _people.value = _people.value!!.toMutableList().also {
            it.remove(person)
        }
    }
}