package com.dreamsoftware.nimbustv.domain.exception

open class DomainRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class RepositoryOperationException(message: String? = null, cause: Throwable? = null) : DomainRepositoryException(message, cause)

class InvalidDataException(errors: Map<String, String>, message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

class UserProfilesLimitReachedException(val maxProfilesLimit: Int, message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)