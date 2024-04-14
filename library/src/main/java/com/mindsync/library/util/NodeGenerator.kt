package com.mindsync.library.util

import com.mindsync.library.data.RectangleNode
import com.mindsync.library.data.RectanglePath


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
