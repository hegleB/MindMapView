package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNode
import com.mindsync.library.data.RectangleNode
import com.mindsync.library.util.Dp
import com.mindsync.library.util.NodeGenerator


class AddNodeCommand(
    private val mindMapManager: MindMapManager,
    private val description: String,
    private val parentId: String,
) : MindMapCommand {
    override fun execute() {
        val newNode = NodeGenerator.makeNode(description, parentId)
        val parent = mindMapManager.getTree().getNode(newNode.parentId)
        val parentX = parent.path.centerX
        val parentY = parent.path.centerY
        val width = when (parent) {
            is CircleNode -> parent.path.radius
            is RectangleNode -> parent.path.width
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