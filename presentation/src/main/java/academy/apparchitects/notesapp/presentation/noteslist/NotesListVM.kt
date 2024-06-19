package academy.apparchitects.notesapp.presentation.noteslist

import academy.apparchitects.notesapp.model.Note
import academy.apparchitects.notesapp.repository.NoteRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

var count = 0
class NotesListVM(): ViewModel() {

    private val _state: MutableStateFlow<NotesListStates<List<Note>>> = MutableStateFlow(NotesListStates.Loading)
    val state: StateFlow<NotesListStates<List<Note>>> = _state

    init {
        Timber.d("fetch notes called ${count++}")
        fetchNotes()
    }

    fun fetchNotes() {
        _state.value =
            NotesListStates.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            try {
                val notesList = (1..10).toList().map {
                    Note(
                        title = "Room DB",
                        note = "SQLite Wrapper",
                        createdOn = System.currentTimeMillis()
                    )
                }
                val favNotes = (1..3).toList().map {
                    Note(
                        title = "Jetpack Compose",
                        note = "Declarative and Unidirectional approach to android",
                        createdOn = System.currentTimeMillis()
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