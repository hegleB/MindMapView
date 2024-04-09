package com.mindsync.mindmap.util

import bcom.mindsync.mindmap.util.IdGenerator
import com.mindsync.mindmap.data.RectangleNode
import com.mindsync.mindmap.data.RectanglePath

object NodeGenerator {
    fun makeNode(
        description: String,
        parentId: String,
    ) = RectangleNode(
        id = IdGenerator.makeRandomNodeId(),
        parentId = parentId,
        path =
            RectanglePath(
                Dp(0f),
                Dp(0f),
                Dp(0f),
                Dp(0f),
            ),
        description = description,
        listOf(),
    )
}
