/*
 * This file is taken directly from the m3u-parser project.
 * 
 * Original work: https://github.com/bjoernpetersen/m3u-parser
 * Licensed under the MIT License (see LICENSE-M3UPARSER for details).
 */
package com.dreamsoftware.nimbustv.framework.m3u.model

/**
 * Additional key-value data for an M3U entry. Basically just a wrapper around a regular Map with
 * some convenience accessors for commonly-used keys.
 */
class M3uMetadata(private val data: Map<String, String>) : Map<String, String> by data {
    /**
     * Gets a value for commonly used logo keys. If this is present, it's usually a URL.
     *
     * The returned value will never be a blank string.
     */
    val logo: String?
        get() = data["logo"].notBlankOrNull() ?: data["tvg-logo"].notBlankOrNull()

    @Suppress("UndocumentedPublicClass")
    companion object {
        /**
         * Obtain an empty instance of M3uMetadata.
         */
        @JvmStatic
        fun empty() = M3uMetadata(emptyMap())
    }
}

private fun String?.notBlankOrNull(): String? = this?.ifBlank { null }
