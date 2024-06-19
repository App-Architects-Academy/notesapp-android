package academy.apparchitects.notesapp

import android.app.Application
import timber.log.Timber

class NotesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}