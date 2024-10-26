package com.dreamsoftware.nimbustv.data.preferences.dto

import com.dreamsoftware.nimbustv.ui.utils.EMPTY

data class UserPreferencesDTO(
    val enableSearch: Boolean = true,
    val epgViewMode: String = String.EMPTY,
)
