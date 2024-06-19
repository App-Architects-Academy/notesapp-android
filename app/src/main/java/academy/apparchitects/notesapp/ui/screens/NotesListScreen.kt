package academy.apparchitects.notesapp.ui.screens

import academy.apparchitects.notesapp.model.Note
import academy.apparchitects.notesapp.presentation.noteslist.NotesListStates
import academy.apparchitects.notesapp.presentation.noteslist.NotesListVM
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotesListScreen(
    viewModel: NotesListVM,
    onNoteClick: (String) -> Unit,
    onBookMarkChange: (note: Note) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var noteToEdit by remember { mutableStateOf<Note?>(null) }

//    LaunchedEffect(key1 = Unit) {
//        notesListVM.fetchNotes()
//    }

    when (val currentState = state) {
        is NotesListStates.Success -> {
            NotesList(
                currentState.otherNotes,
                onBookMarkChange,
                onDeleteClick,
                onNoteClick,
                onEditClick = {
                    noteToEdit = it
                }
            )
        }

        is NotesListStates.Error -> {
            Text(
                text = currentState.errorDetails,
                color = MaterialTheme.colorScheme.error
            )
        }

        is NotesListStates.Idle -> {
            Text(text = "Nothing happening")
        }

        is NotesListStates.Loading -> {
            CircularProgressIndicator()
        }
    }
    noteToEdit?.let { note ->
        EditNoteDialog(
            note = note,
            onDismissClick = { noteToEdit = null },
            onSaveClick = { updatedNote ->
                // Handle save logic, e.g., updating the note in the ViewModel
               // viewModel.updateNote(updatedNote)
                noteToEdit = null
            }
        )
    }
}

@Composable
fun NotesList(
    notes: List<Note>,
    onBookMarkChange: (note: Note) -> Unit,
    onDeleteClick: (String) -> Unit,
    onNoteClick: (String) -> Unit,
    onEditClick: (note: Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid (
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        item(span = { GridItemSpan(2) }) { // Make the header span across 2 columns
            Text(
                "Notes",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Divider()
        }
        itemsIndexed(notes) { index, note ->
            NoteCard(note, onBookMarkChange, onDeleteClick, onNoteClick, onEditClick)
        }
    }
}

@Composable
fun NoteCard(
    note: Note,
    onBookMarkChange: (note: Note) -> Unit,
    onDeleteClick: (String) -> Unit,
    onNoteClick: (String) -> Unit,
    onEditClick: (note: Note) -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    val icon = if (note.isFavorite) Icons.Default.FavoriteBorder
    else Icons.Default.Favorite
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        onClick = { onNoteClick.invoke(note.id.toString()) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title ?: "Nothing to note",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = { onEditClick(note) },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = note.note,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onDeleteClick(note.id.toString()) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { onBookMarkChange(note) }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NoteListScreenPreview() {
//    NotesListScreen(
//        onNoteClick = {},
//        onDeleteClick = {},
//        onBookMarkChange = {},
//        viewModel =
//    )
//}

val notes = listOf(
    Note(
        title = "Room DB",
        note = "Wrapper around SQLite. Use Realm",
        createdOn = System.currentTimeMillis(),
        editedOn = System.currentTimeMillis(),
        isFavorite = false
    ),
    Note(
        title = "Jetpack Compose",
        note = "Wake up and learn JC and revise every day",
        createdOn = System.currentTimeMillis(),
        editedOn = System.currentTimeMillis(),
        isFavorite = true
    )
)