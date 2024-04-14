package com.mindsync.library.util

import java.util.UUID

object IdGenerator {
    fun makeRandomNodeId() = UUID.randomUUID().toString()
}
