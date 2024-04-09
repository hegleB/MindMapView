package com.mindsync.mindmap.command

import com.mindsync.mindmap.MindMapManager
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node
import com.mindsync.mindmap.data.RectangleNode

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