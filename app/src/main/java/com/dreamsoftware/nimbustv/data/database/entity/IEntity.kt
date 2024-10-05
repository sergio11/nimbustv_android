package com.dreamsoftware.nimbustv.data.database.entity

interface IEntity {
    val id: Long
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TableName(val value: String)