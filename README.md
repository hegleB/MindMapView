# MindMapView

You can easily draw mind maps. It allows for node creation, deletion, as well as zooming and moving.

```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
  }
}
```

```
dependencies {
  implementation 'com.github.hegleB:MindMapView:0.1.0'
}
```

## Funtions
- zoom, move
- Fix your window
- remove nodes
- add nodes
- edit node text

|Add|Remove|Edit|Move|Zoom and Fit Window|
|---|---|---|---|---|
|![add](https://github.com/hegleB/MindMapView/assets/39490416/e8bf4cc8-253a-4a7b-9270-9048959cb2f2)|![remove](https://github.com/hegleB/MindMapView/assets/39490416/83283bdc-f704-4f89-8af4-27c2d5c8a771)|![edit](https://github.com/hegleB/MindMapView/assets/39490416/da752563-a85d-440a-948a-4e625fedfed1)|![move](https://github.com/hegleB/MindMapView/assets/39490416/5e226f2e-2420-4356-8473-7f9be5af2471)|![zoom_and_fit](https://github.com/hegleB/MindMapView/assets/39490416/1241982a-b7b9-499c-8b6b-37141bfa61d0)|

## Step
1. Define Node Classes
```kotlin
sealed class Node(
    open val id: String,
    open val parentId: String?,
    open val path: NodePath,
    open val description: String,
    open val children: List<String>,
) {
    abstract fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node
}

data class CircleNode(
    override val id: String,
    override val parentId: String?,
    override val path: CirclePath = CirclePath(Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
) : Node(id, parentId, path, description, children) {
    override fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}

data class RectangleNode(
    override val id: String,
    override val parentId: String,
    override val path: RectanglePath = RectanglePath(Dp(0f), Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
) : Node(id, parentId, path, description, children) {
    override fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}
```

2. Initialize the Mind Map View
```kotlin
    val tree = Tree<Node>(this)
        binding.mindMapView.setTree(tree)
        binding.mindMapView.initialize()
```

3. Implement Node Click Listener
```kotlin
binding.mindMapView.setNodeClickListener(object : NodeClickListener {
    override fun onClickListener(node: NodeData<*>?) {
        val selectedNode = createNode(node)
        ...
    }
})
```

4. Create Nodes Based on User Interaction
```kotlin
fun createNode(node: NodeData<*>?): Node? {
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
```

5. Additional View Manipulations
```
binding.mindMapView.addNode(description)
binding.mindMapView.editNodeText(description)
binding.mindMapView.fitScreen()
```
