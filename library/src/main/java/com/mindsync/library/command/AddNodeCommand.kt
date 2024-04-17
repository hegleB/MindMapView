package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.RectangleNodeData
import com.mindsync.library.util.Dp
import com.mindsync.library.util.NodeGenerator


class AddNodeCommand(
    private val mindMapManager: MindMapManager,
    private val description: String,
    private val parentId: String,
) : MindMapCommand {
    override fun execute() {
        val newNode = NodeGenerator.makeNode(RectangleNodeData::class, description, parentId)
        val parent = mindMapManager.getTree().getNode(newNode.parentId)
        val parentX = parent.path.centerX
        val parentY = parent.path.centerY
        val width = when (parent) {
            is CircleNodeData -> parent.path.radius
            is RectangleNodeData -> parent.path.width
        }
        val updatedNode =  newNode.copy(
            path = newNode.path.copy(
                centerX = parentX + width + Dp(20f),
                centerY = parentY
            )
        )
        mindMapManager.addNode(updatedNode, description)
    }
}