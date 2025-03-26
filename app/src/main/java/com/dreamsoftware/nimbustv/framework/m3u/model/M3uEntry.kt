/*
 * This file is based on M3uEntry from the m3u-parser project.
 * 
 * Original work: https://github.com/bjoernpetersen/m3u-parser
 * Licensed under the MIT License (see LICENSE-M3UPARSER for details).
 * 
 * Modifications have been made to extend functionality and support additional use cases.
 */
package com.dreamsoftware.nimbustv.framework.m3u.model

import java.time.Duration

/**
 * Represents an entry in a `.m3u` file.
 *
 * This data class encapsulates the properties of a media entry, including its
 * location, duration, title, associated metadata, Kodi properties, and stream type.
 * It is used to represent a media item that can be streamed or played back.
 *
 * @param location The [MediaLocation] of the media item, which can represent a
 *                 URL or a local file path where the media is located.
 * @param duration The duration of the media item as a [Duration] object, or null
 *                 if the duration is not specified or unknown.
 * @param title The title of the media item as a [String], or null if no title
 *              is provided.
 * @param metadata A [M3uMetadata] instance that holds any additional key-value
 *                 metadata associated with the media item. This metadata can provide
 *                 extra information about the media, such as genre, bitrate, or
 *                 other attributes.
 * @param kodiProps A map of Kodi properties as a [Map<String, String>]. These
 *                  properties may include specific settings or configurations related
 *                  to how the media item should be handled by Kodi or compatible
 *                  players.
 * @param streamType The type of stream represented by this entry as a [StreamType].
 *                   This indicates whether the entry represents an audio-only stream
 *                   (ONLY_AUDIO) or a video stream (VIDEO).
 */
data class M3uEntry @JvmOverloads constructor(
    val location: MediaLocation,
    val duration: Duration? = null,
    val title: String? = null,
    val metadata: M3uMetadata = M3uMetadata.empty(),
    val kodiProps: Map<String, String> = emptyMap(),
    val streamType: StreamType
)
