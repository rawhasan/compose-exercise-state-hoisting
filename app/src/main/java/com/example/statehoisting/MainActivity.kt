package com.example.statehoisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.statehoisting.model.Person

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val peopleViewModel: PeopleViewModel by viewModels()
//      val peopleViewModel by viewModels<PeopleViewModel>() // also works

        setContent {
            PeopleActivityScreen(peopleViewModel)
        }
    }
}

@Composable
fun PeopleActivityScreen(peopleViewModel: PeopleViewModel) {
    val people: List<Person> by peopleViewModel.people.observeAsState(listOf())

    PeopleScreen(
        people = people,
        onAddPerson = { },
        onRemovePerson = { peopleViewModel.removePerson(it) },
        onSortPerson = { peopleViewModel.sortPeople(it) }
    )
}

