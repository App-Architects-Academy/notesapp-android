package academy.apparchitects.notesapp.ui

import academy.apparchitects.notesapp.presentation.noteslist.NotesListVM
import academy.apparchitects.notesapp.ui.screens.NotesDetailScreen
import academy.apparchitects.notesapp.ui.screens.NotesListScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable


@Composable
fun NotesAppNav(
    navController: NavHostController,
    startDestination: Destinations = Destinations.NotesList,
    viewModel: NotesListVM = viewModel()
) {
    NavHost(navController = navController, startDestination = startDestination.toString()) {
        composable(Destinations.NotesList.toString()) {
            NotesListScreen(
                viewModel,
                onNoteClick = {
                    println("Navigated to NoteDetail with ID 1: $it")
                    navController.navigate(Destinations.NoteDetail(it).toString())
                },
                onBookMarkChange = {},
                onDeleteClick = {},
            )
        }

        composable(
            route = "${Destinations.NoteDetail.route}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
            println("Navigated to NoteDetail with ID: $noteId")
            NotesDetailScreen(noteId = noteId, onBack = { navController.navigateUp() })
        }
    }
}

sealed class Destinations {

    @Serializable
    data object NotesList : Destinations() {
        override fun toString(): String {
            return "notesList"
        }
    }

    @Serializable
    data class NoteDetail(val noteId: String) : Destinations() {
        override fun toString(): String {
            return "noteDetail/$noteId"
        }

        companion object {
            const val route = "noteDetail"
        }
    }
}