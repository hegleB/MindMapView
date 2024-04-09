package com.mindsync.mindmapview

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mindsync.mindmap.MindMapView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mindMapView = findViewById<MindMapView>(R.id.mindMapView)
        val button = findViewById<Button>(R.id.button)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            mindMapView.addNode("test")
        }
        button1.setOnClickListener {
            mindMapView.removeNode()
        }

        button2.setOnClickListener {
            mindMapView.editNodeText("1111")
        }

        button3.setOnClickListener {
            mindMapView.fitScreen()
        }
    }
}