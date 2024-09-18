package com.dreamsoftware.nimbustv.ui.utils

import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum

val String.Companion.EMPTY: String
    get() = ""

val Char.Companion.SPACE: Char
    get() = ' '

fun AvatarTypeEnum.toDrawableResource(): Int =
    when(this) {
        AvatarTypeEnum.BOY -> R.drawable.profile_avatar_boy
        AvatarTypeEnum.GIRL -> R.drawable.profile_avatar_girl
        AvatarTypeEnum.WOMAN -> R.drawable.profile_avatar_woman
        AvatarTypeEnum.MAN -> R.drawable.profile_avatar_man
    }