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

    private var _isCurrentOrderAscending = true

    init {
        _people.value = PeopleDataSource().loadPeople().sortedBy { it.age }
    }

    fun addPerson(person: Person) {
        _people.value = _people.value!! + listOf(person)
        sortPeople(_isCurrentOrderAscending)
    }

    fun removePerson(person: Person) {
        _people.value = _people.value!!.toMutableList().also {
            it.remove(person)
        }
    }

    fun sortPeople(ascendingOrder: Boolean) {
        if (ascendingOrder)
            _people.value = _people.value?.sortedBy { it.age }
        else
            _people.value = _people.value?.sortedByDescending { it.age }

        _isCurrentOrderAscending = ascendingOrder
    }
}