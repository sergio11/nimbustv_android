package com.dreamsoftware.nimbustv.data.database.entity

interface IEntity<KEY> {
    val id: KEY
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TableName(val value: String)