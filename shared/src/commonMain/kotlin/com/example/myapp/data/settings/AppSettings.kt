package com.example.myapp.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.example.myapp.domain.model.SetupStep

class AppSettings(private val settings: Settings) {

    var agreedToTerms: Boolean
        get() = settings.getBoolean(KEY_AGREED_TO_TERMS, false)
        set(value) { settings[KEY_AGREED_TO_TERMS] = value }

    var setupStep: SetupStep
        get() = SetupStep.valueOf(
            settings.getString(KEY_SETUP_STEP, SetupStep.NONE.name)
        )
        set(value) { settings[KEY_SETUP_STEP] = value.name }

    var authToken: String?
        get() = settings.getStringOrNull(KEY_AUTH_TOKEN)
        set(value) {
            if (value != null) settings[KEY_AUTH_TOKEN] = value
            else settings.remove(KEY_AUTH_TOKEN)
        }

    fun clear() {
        settings.clear()
    }

    companion object {
        private const val KEY_AGREED_TO_TERMS = "agreed_to_terms"
        private const val KEY_SETUP_STEP = "setup_step"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
}
