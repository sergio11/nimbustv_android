package com.dreamsoftware.nimbustv.domain.model

data class ChannelCategoryBO(val label: String?, val value: String) {
    companion object {
        val All: ChannelCategoryBO = ChannelCategoryBO("All", "")
    }
}