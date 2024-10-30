package com.dreamsoftware.nimbustv.ui.utils

import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.nimbustv.domain.model.EpgScheduleBO
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

val String.Companion.EMPTY: String
    get() = ""

val Char.Companion.SPACE: Char
    get() = ' '

fun AvatarTypeEnum.toDrawableResource(): Int =
    when (this) {
        AvatarTypeEnum.BOY -> R.drawable.profile_avatar_boy
        AvatarTypeEnum.GIRL -> R.drawable.profile_avatar_girl
        AvatarTypeEnum.WOMAN -> R.drawable.profile_avatar_woman
        AvatarTypeEnum.MAN -> R.drawable.profile_avatar_man
    }

fun Date.toDateFormatted(): String = toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

fun LocalDateTime.toTimeFormatted(): String = format(DateTimeFormatter.ofPattern("HH:mm"))

fun EpgScheduleBO.toScheduleFormatted() =
    "${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
            endTime.format(DateTimeFormatter.ofPattern("HH:mm"))