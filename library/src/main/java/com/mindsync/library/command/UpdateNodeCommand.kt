package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNode
import com.mindsync.library.data.Node
import com.mindsync.library.data.RectangleNode


class UpdateNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: Node,
    private val description: String,
) : MindMapCommand {
    override fun execute() {
        val selectedNode = when (node) {
            is CircleNode -> node.copy(description = description)
            is RectangleNode -> node.copy(description = description)
        }
        mindMapManager.update(selectedNode)
    }
}