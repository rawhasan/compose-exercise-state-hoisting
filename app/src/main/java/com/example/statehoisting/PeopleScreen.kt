package com.example.statehoisting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.statehoisting.data.PeopleDataSource
import com.example.statehoisting.model.Person

@ExperimentalComposeUiApi
@Composable
fun PeopleScreen(
    people: List<Person>,
    onAddPerson: (Person) -> Unit,
    onRemovePerson: (Person) -> Unit,
    onSortPerson: (Boolean) -> Unit
) {
    var sortAscending by remember { mutableStateOf(true) }

    var name by remember { mutableStateOf("") }
    var spaceCount by remember { mutableStateOf(0) }

    var age by remember { mutableStateOf("") }

    // experimental api
    var keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = {
                    sortAscending = !sortAscending
                    onSortPerson(sortAscending)
                }) {
                    Icon(
                        imageVector = if (sortAscending) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = name,
                onValueChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) name = it
                },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth(0.5f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )

            TextField(
                value = age,
                onValueChange = { if (it.isDigitsOnly()) age = it },
                label = { Text(text = "Age") },
                modifier = Modifier.width(80.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )

            Button(
                onClick = {
                    if (name.trim().isNotEmpty() && age.isNotEmpty() && age.toInt() in (1..130)) {
                        val newPerson = Person(name.trim(), age.toInt())
                        onAddPerson(newPerson)
                        name = ""
                        age = ""
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp)
                )
                Text(text = "Add")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(people) { person ->
                PeopleRow(person = person, onPersonClicked = { onRemovePerson(person) })
            }
        }
    }
}

@Composable
fun PeopleRow(person: Person, onPersonClicked: (Person) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clickable { onPersonClicked(person) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(end = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = person.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = person.age.toString(), style = TextStyle(color = Color.White))
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    val people = PeopleDataSource().loadPeople()

    PeopleScreen(people = people, onAddPerson = {}, onRemovePerson = {}, onSortPerson = { true })
}