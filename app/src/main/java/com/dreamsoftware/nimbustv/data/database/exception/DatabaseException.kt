package com.dreamsoftware.nimbustv.data.database.exception

open class DatabaseException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class RecordNotFoundException(message: String? = null, cause: Throwable? = null): DatabaseException(message, cause)
class AccessDatabaseException(message: String? = null, cause: Throwable? = null): DatabaseException(message, cause)