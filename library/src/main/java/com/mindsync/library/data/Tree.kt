package com.mindsync.library.data

import android.content.Context
import com.mindsync.library.util.Dp
import com.mindsync.library.util.ExceptionMessage
import com.mindsync.library.util.NodeGenerator

class Tree<T> {
    private var _nodes: MutableMap<String, NodeData<*>>
    val nodes get() = _nodes.toMap()

    constructor(context: Context) {
        _nodes = mutableMapOf()
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val centerX = screenWidth / displayMetrics.density / 2
        val centerY = screenHeight / displayMetrics.density / 2
        _nodes[ROOT_ID] =
            CircleNodeData(
                id = ROOT_ID,
                parentId = null,
                path =
                    CirclePath(
                        Dp(centerX),
                        Dp(centerY),
                        Dp(50f),
                    ),
                "root",
                listOf(),
            ) as NodeData<T>
    }

    constructor(nodes: Map<String, NodeData<*>>) {
        this._nodes = nodes.toMutableMap()
    }

    fun copy(nodes: Map<String, NodeData<T>>): Tree<T> {
        return Tree(nodes)
    }

    fun getRootNode(): CircleNodeData {
        _nodes[ROOT_ID]
            ?: throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_ROOT_NODE_NOT_EXIST.message)
        return _nodes[ROOT_ID] as CircleNodeData
    }

    fun setRootNode(root: NodeData<*>) {
        _nodes[ROOT_ID] = root
    }

    fun setNode(
        id: String,
        nodeData: NodeData<*>,
    ) {
        _nodes[id] = nodeData
    }

    fun getNode(id: String?): NodeData<*> {
        return _nodes[id]
            ?: throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_INVALID_NODE_ID.message)
    }

    fun addNode(
        targetId: String,
        parentId: String?,
        content: String,
    ) {
        if (_nodes[targetId] != null) throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_DUPLICATED_NODE.message)
        if (parentId == null) throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_PARENT_NODE_NOT_EXIST.message)
        val newNode =
            NodeGenerator.makeNode(RectangleNodeData::class, description = content, parentId = parentId)
                .copy(id = targetId, parentId = parentId)
        val parentNode =
            _nodes[parentId] ?: throw IllegalArgumentException(
                ExceptionMessage.ERROR_MESSAGE_PARENT_NODE_NOT_EXIST.message,
            )
        val newChildren = parentNode.children.toMutableList()
        newChildren.add(targetId)
        val newParent = when (parentNode) {
            is CircleNodeData -> parentNode.copy(children = newChildren)
            is RectangleNodeData -> parentNode.copy(children = newChildren)
        }
        _nodes[parentId] = newParent as NodeData<T>
        _nodes[targetId] = newNode as NodeData<T>
    }

    fun attachNode(
        targetId: String,
        parentId: String?,
    ) {
        if (targetId == ROOT_ID || parentId == null) return
        val targetNode = nodes[targetId] ?: return

        val newTargetNode =
            when (targetNode) {
                is CircleNodeData -> targetNode.copy(parentId = parentId)
                is RectangleNodeData -> targetNode.copy(parentId = parentId)
            }
        val parentNode = nodes[parentId] ?: return
        val newNodes = parentNode.children.toMutableList()
        newNodes.add(targetId)
        val newParentNode =
            when (parentNode) {
                is CircleNodeData -> parentNode.copy(children = newNodes)
                is RectangleNodeData -> parentNode.copy(children = newNodes)
            }
        _nodes[targetId] = newTargetNode as NodeData<T>
        _nodes[parentId] = newParentNode as NodeData<T>
    }

    fun removeNode(targetId: String) {
        if (targetId == ROOT_ID) throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_ROOT_CANT_REMOVE.message)
        val targetNode =
            _nodes[targetId]
                ?: throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_TARGET_NODE_NOT_EXIST.message)
        targetNode.parentId?.let { parentId ->
            val parentNode =
                _nodes[parentId] ?: throw IllegalArgumentException(
                    ExceptionMessage.ERROR_MESSAGE_PARENT_NODE_NOT_EXIST.message,
                )
            val newChildren = parentNode.children.filter { id -> id != targetId }
            val newParent =
                when (parentNode) {
                    is CircleNodeData -> parentNode.copy(children = newChildren)
                    is RectangleNodeData -> parentNode.copy(children = newChildren)
                }
            _nodes[parentId] = newParent as NodeData<T>
        } ?: throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_ROOT_CANT_REMOVE.message)
    }

    fun updateNode(
        targetId: String,
        description: String,
        children: List<String> = emptyList(),
        dx: Dp = Dp(0f),
        dy: Dp = Dp(0f),
    ) {
        val targetNode =
            _nodes[targetId]
                ?: throw IllegalArgumentException(ExceptionMessage.ERROR_MESSAGE_TARGET_NODE_NOT_EXIST.message)
        val newTargetNode =
            when (targetNode) {
                is CircleNodeData -> targetNode.copy(description = description)
                is RectangleNodeData ->
                    targetNode.copy(
                        path =
                            targetNode.path.copy(
                                centerX = dx,
                                centerY = dy,
                            ),
                        description = description,
                        children = if (children.isEmpty()) targetNode.children else children,
                    )
            }
        _nodes[targetId] = newTargetNode
    }

    fun doPreorderTraversal(
        nodeData: NodeData<*> = getRootNode(),
        action: (nodeData: NodeData<*>) -> Unit,
    ) {
        action.invoke(nodeData)
        nodeData.children.forEach { childId ->
            doPreorderTraversal(getNode(childId), action)
        }
    }

    fun doPreorderTraversal(
        nodeData: NodeData<*> = getRootNode(),
        depth: Int = 0,
        action: (nodeData: NodeData<*>, depth: Int) -> Unit,
    ) {
        action.invoke(nodeData, depth)
        nodeData.children.forEach { childId ->
            doPreorderTraversal(getNode(childId), depth + 1, action)
        }
    }

    companion object {
        private const val ROOT_ID = "root"
    }
}
