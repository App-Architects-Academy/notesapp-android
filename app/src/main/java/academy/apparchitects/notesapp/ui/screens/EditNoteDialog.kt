package academy.apparchitects.notesapp.ui.screens

import academy.apparchitects.notesapp.model.Note
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun EditNoteDialog(
    note: Note,
    onDismissClick: () -> Unit,
    onSaveClick: (Note) -> Unit
) {

    var title by remember { mutableStateOf(note.title ?: "") }
    var content by remember { mutableStateOf(note.note) }
    var createdOn by remember { mutableStateOf(note.createdOn) }

    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text("Edit Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = { Text("title") }
                )
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveClick(
                        note.copy(
                            title = title,
                            note = content,
                            createdOn = createdOn,
                            editedOn = System.currentTimeMillis()
                        )
                    )
                    onDismissClick()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissClick
            ) {
                Text("Dismiss")
            }
        }
    )
}