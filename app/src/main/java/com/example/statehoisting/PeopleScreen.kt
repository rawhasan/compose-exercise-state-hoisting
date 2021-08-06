package com.example.statehoisting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.statehoisting.data.PeopleDataSource
import com.example.statehoisting.model.Person

@Composable
fun PeopleScreen(
    people: List<Person>,
    onAddPerson: (Person) -> Unit,
    onRemovePerson: (Person) -> Unit
) {
    Column {
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

@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    val people = PeopleDataSource().loadPeople()

    PeopleScreen(people = people, onAddPerson = {}, onRemovePerson = {})
}