package mobile.app.android.myavuzbahceci.weatherforecast.framework.datasource.datastore

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AndroidUiDispatcher.Companion.Main
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mobile.app.android.myavuzbahceci.weatherforecast.framework.presentation.BaseApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataStore
@Inject
constructor(app: BaseApplication){

    private val datastore: DataStore<Preferences> = app.createDataStore(name = "settings")

    private val scope = CoroutineScope(Main)

    init {
        observeDataStore()
    }

    val isDark = mutableStateOf(false)

    fun themeToggle(){
        scope.launch {
            datastore.edit { preferences ->
                val current = preferences[DARK_THEME_KEY]?: false
                preferences[DARK_THEME_KEY] = !current
            }
        }
    }

    private fun observeDataStore(){
        datastore.data.onEach { preferences ->
            preferences[DARK_THEME_KEY]?.let { isDarkTheme ->
                isDark.value = isDarkTheme
            }
        }.launchIn(scope)
    }

    companion object{
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }
}