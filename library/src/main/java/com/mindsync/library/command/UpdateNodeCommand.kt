package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.NodeData
import com.mindsync.library.data.RectangleNodeData


class UpdateNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: NodeData<*>,
    private val description: String,
) : MindMapCommand {
    override fun execute() {
        val selectedNode = when (node) {
            is CircleNodeData -> node.copy(description = description)
            is RectangleNodeData -> node.copy(description = description)
        }
        mindMapManager.update(selectedNode)
    }
}