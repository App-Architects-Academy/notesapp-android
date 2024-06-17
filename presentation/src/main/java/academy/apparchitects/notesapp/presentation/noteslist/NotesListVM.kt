package academy.apparchitects.notesapp.presentation.noteslist

import academy.apparchitects.notesapp.data.Note
import academy.apparchitects.notesapp.presentation.base.BaseViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.UUID

class NotesListVM : BaseViewModel<NotesListStates>() {
    private val _state: MutableStateFlow<NotesListStates> = MutableStateFlow(NotesListStates.Idle)

    override val state: StateFlow<NotesListStates> = _state

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        _state.value =
            NotesListStates.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            try {
                val notesList = (1..10).toList().map {
                    Note(
                        id = UUID.randomUUID(),
                        title = "Note $it",
                        note = "Some note $it",
                        createdOn = Clock.System.now()
                    )
                }
                val favNotes = (1..3).toList().map {
                    Note(
                        id = UUID.randomUUID(),
                        title = "Fav Note $it",
                        note = "Some note $it",
                        createdOn = Clock.System.now()
                    )
                }
                _state.value =
                    NotesListStates.Success(
                        favNotes, notesList
                    )
                println("Notes fetched successfully: favNotes=${favNotes.map { it.id }}, otherNotes=${notesList.map { it.id }}")
            } catch (t: Throwable) {
                _state.value = NotesListStates.Error(t.message ?: "Not yet implemented")
            }

        }
    }

    fun getNoteById(noteId: String): Note? {
        val currentState = _state.value
        if (currentState is NotesListStates.Success) {
            println("Current favNotes: ${currentState.favNotes.map { it.id }}")
            println("Current otherNotes: ${currentState.otherNotes.map { it.id }}")
            val favNote = currentState.favNotes.find { it.id.toString() == noteId }
            val otherNote = currentState.otherNotes.find { it.id.toString() == noteId }
            println("Finding Note ID: $noteId")
            println("Fav Note Found: $favNote")
            println("Other Note Found: $otherNote")
            return favNote ?: otherNote
        }
        return null

    }
}