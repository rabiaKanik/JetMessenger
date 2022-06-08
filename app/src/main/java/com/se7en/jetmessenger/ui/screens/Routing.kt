package com.se7en.jetmessenger.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.ui.graphics.vector.VectorAsset

sealed class Routing(val route: String, val label: String) {

    object Main : Routing(route = "main", label = "Main") {

        val bottomNavRoutings = listOf(BottomNav.Chats)

        sealed class BottomNav(
            route: String,
            label: String,
            val icon: VectorAsset,
            val actions: List<ToolbarAction>,
        ): Routing(route, label) {
            object Chats : BottomNav(
                route = "chats",
                label = "Chats",
                Icons.Rounded.Chat,
                listOf(ToolbarAction.Camera, ToolbarAction.NewMessage)
            )
        }
    }

    object Conversation : Routing(route = "conversation", label = "Conversation") {
        object Info

        val actions: List<ToolbarAction> = listOf(
            ToolbarAction.VoiceCall,
            ToolbarAction.VideoCall,
            ToolbarAction.Info
        )
    }

    object Search : Routing(route = "search", label = "Search")
    object Settings : Routing(route = "settings", label = "Settings")
}

sealed class ToolbarAction(val icon: VectorAsset) {
    object Camera : ToolbarAction(Icons.Filled.CameraAlt)
    object NewMessage : ToolbarAction(Icons.Filled.Edit)
    object AddContacts : ToolbarAction(Icons.Filled.Contacts)
    object VoiceCall : ToolbarAction(Icons.Filled.Call)
    object VideoCall : ToolbarAction(Icons.Filled.VideoCall)
    object Info : ToolbarAction(Icons.Filled.Info)
}