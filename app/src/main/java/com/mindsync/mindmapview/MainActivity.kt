package com.mindsync.mindmapview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mindsync.library.MindMapManager
import com.mindsync.library.data.Tree
import com.mindsync.mindmapview.databinding.ActivityMainBinding

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

        button2.setOnClickListener {
            mindMapView.editNodeText("1111")
        }

        button3.setOnClickListener {
            mindMapView.fitScreen()
        }
    }
}