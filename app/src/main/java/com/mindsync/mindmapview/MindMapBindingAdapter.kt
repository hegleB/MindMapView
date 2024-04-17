package com.mindsync.mindmapview

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.mindsync.mindmapview.model.CircleNode
import com.mindsync.mindmapview.model.Node
import com.mindsync.mindmapview.model.RectangleNode


@BindingAdapter("app:removeBtn")
fun ImageButton.setEnabled(selectedNode: Node?) {
    this.isEnabled =
        when (selectedNode) {
            is CircleNode -> false
            is RectangleNode -> true
            else -> false
        }
}
