package bcom.mindsync.mindmap.util

import java.util.UUID

object IdGenerator {
    fun makeRandomNodeId() = UUID.randomUUID().toString()
}
