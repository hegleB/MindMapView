package com.mindsync.mindmapview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mindsync.library.MindMapManager
import com.mindsync.library.NodeClickListener
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.NodeData
import com.mindsync.library.data.RectangleNodeData
import com.mindsync.library.data.Tree
import com.mindsync.mindmapview.databinding.ActivityMainBinding
import com.mindsync.mindmapview.model.CircleNode
import com.mindsync.mindmapview.model.CirclePath
import com.mindsync.mindmapview.model.Node
import com.mindsync.mindmapview.model.RectangleNode
import com.mindsync.mindmapview.model.RectanglePath

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MindMapViewModel>()
    private lateinit var manager: MindMapManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setBinding()
        init()
        setClickEvent()
    }
    private fun setBinding() {
        binding.vm = viewModel
    }

    private fun init() {
        val tree = Tree<Node>(this)
        binding.mindMapView.setTree(tree)
        binding.mindMapView.initialize()
        manager = binding.mindMapView.getMindMapManager()
    }

    private fun showDialog(
        operationType: String,
        selectedNode: Node,
    ) {
        val description = if (operationType == "add") "" else selectedNode.description
        val editDescriptionDialog = EditDescriptionDialog()
        editDescriptionDialog.setDescription(description)
        editDescriptionDialog.setSubmitListener { description ->
            when (operationType) {
                "add" -> {
                    binding.mindMapView.addNode(description)
                }

                "update" -> {
                    binding.mindMapView.editNodeText(description)
                }

                else -> return@setSubmitListener
            }
        }
        editDescriptionDialog.show(
            this.supportFragmentManager,
            "EditDescriptionDialog",
        )
    }

    private fun setClickEvent() {
        with(binding) {
            imgbtnMindMapAdd.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    showDialog("add", selectNode)
                }
            }
            imgbtnMindMapEdit.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    showDialog("update", selectNode)
                }
            }
            imgbtnMindMapRemove.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    mindMapView.removeNode()
                }
            }

            imgbtnMindMapFit.setOnClickListener {
                mindMapView.fitScreen()
            }

            mindMapView.setNodeClickListener(object : NodeClickListener {
                override fun onClickListener(node: NodeData<*>?) {
                    val selectedNode = createNode(node)
                    viewModel.setSelectedNode(selectedNode)
                }
            })
        }
    }

    private fun createNode(node: NodeData<*>?): Node? {
        return when (node) {
            is CircleNodeData -> CircleNode(
                node.id,
                node.parentId,
                CirclePath(
                    Dp(node.path.centerX.dpVal),
                    Dp(node.path.centerY.dpVal),
                    Dp(node.path.radius.dpVal)
                ),
                node.description,
                node.children
            )

            is RectangleNodeData -> RectangleNode(
                node.id,
                node.parentId,
                RectanglePath(
                    Dp(node.path.centerX.dpVal),
                    Dp(node.path.centerY.dpVal),
                    Dp(node.path.width.dpVal),
                    Dp(node.path.height.dpVal)
                ),
                node.description,
                node.children
            )

            else -> null
        }
    }
}