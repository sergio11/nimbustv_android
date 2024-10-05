package com.dreamsoftware.nimbustv.domain.validation

interface ICreateProfileRequestValidatorMessagesResolver {
    fun getInvalidPinMessage(): String
    fun getInvalidAliasMessage(): String
}