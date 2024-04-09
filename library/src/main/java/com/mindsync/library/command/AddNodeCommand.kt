package com.mindsync.mindmap.command

import com.mindsync.mindmap.MindMapManager
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.RectangleNode
import com.mindsync.mindmap.util.Dp
import com.mindsync.mindmap.util.NodeGenerator

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