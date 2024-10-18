package com.dreamsoftware.nimbustv.domain.exception

open class DomainRepositoryException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
class RepositoryOperationException(message: String? = null, cause: Throwable? = null) : DomainRepositoryException(message, cause)

class InvalidDataException(errors: Map<String, String>, message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

// M3U
class ParsePlayListFailedException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

// Playlist
class InsertPlaylistException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class UpdatePlaylistException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetPlaylistsByProfileException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class DeletePlaylistsException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

// EPG
class SaveEpgDataException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetEpgDataException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class DeleteEpgDataException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

// Channels
class SaveChannelsException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetChannelsByPlaylistException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetChannelByIdException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class AddToFavoritesException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class RemoveFromFavoritesException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetFavoriteChannelsByProfileIdException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

//Profiles
class FetchProfilesException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class UpdateProfileException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class DeleteProfileException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class CreateProfileException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class VerifyPinException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class SelectProfileException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetProfileByIdException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class GetProfileSelectedException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class ClearProfileSelectedException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)
class UserProfilesLimitReachedException(val maxProfilesLimit: Int, message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)

// EPG
class ParseEpgFailedException(message: String? = null, cause: Throwable? = null): DomainRepositoryException(message, cause)