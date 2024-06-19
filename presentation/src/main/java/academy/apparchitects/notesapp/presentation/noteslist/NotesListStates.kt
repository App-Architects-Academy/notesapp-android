package academy.apparchitects.notesapp.presentation.noteslist

import academy.apparchitects.notesapp.model.Note

sealed class NotesListStates<out T> {
    data class Success<T>(
        val favNotes: List<Note>,
        val otherNotes: List<Note>,
    ) : NotesListStates<T>()

    data class Error(
        val errorDetails: String
    ) : NotesListStates<Nothing>()

    data object Loading : NotesListStates<Nothing>()

    data object Idle : NotesListStates<Nothing>()
}