package com.mindsync.library.util

import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.CirclePath
import com.mindsync.library.data.NodeData
import com.mindsync.library.data.RectangleNodeData
import com.mindsync.library.data.RectanglePath
import kotlin.reflect.KClass


object NodeGenerator {
    fun <N : NodeData<*>> makeNode(
        nodeClass: KClass<N>,
        description: String,
        parentId: String
    ): N {
        val id = IdGenerator.makeRandomNodeId()

        return when (nodeClass) {
            CircleNodeData::class -> CircleNodeData(
                id = id,
                parentId = parentId,
                path = CirclePath(Dp(0f), Dp(0f), Dp(50f)),  // 예시로 CirclePath 설정
                description = description,
                children = listOf()
            ) as N
            RectangleNodeData::class -> RectangleNodeData(
                id = id,
                parentId = parentId,
                path = RectanglePath(Dp(0f), Dp(0f), Dp(100f), Dp(50f)),  // 예시로 RectanglePath 설정
                description = description,
                children = listOf()
            ) as N
            else -> throw IllegalArgumentException("Unsupported Node type")
        }
    }
}
