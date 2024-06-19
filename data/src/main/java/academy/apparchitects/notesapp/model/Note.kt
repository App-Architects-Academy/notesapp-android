package academy.apparchitects.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: Int = 0,
    val title: String? = null,
    val note: String,
    val createdOn: Long,
    val editedOn: Long? = null,
    val isFavorite: Boolean = false
)
