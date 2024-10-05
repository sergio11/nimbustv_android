package com.dreamsoftware.nimbustv.domain.extensions

private const val SECURE_PIN_LENGTH = 6
private const val MIN_ALIAS_LENGTH = 5

fun Int.isSecurePinValid() = toString().length == SECURE_PIN_LENGTH

fun Int.isSecurePinNotValid() = !isSecurePinValid()

fun String.isProfileAliasValid() = length >= MIN_ALIAS_LENGTH

fun String.isProfileAliasNotValid() = !isProfileAliasValid()