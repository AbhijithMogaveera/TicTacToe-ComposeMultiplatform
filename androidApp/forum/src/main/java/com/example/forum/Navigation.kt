package com.example.forum

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.shared.hooks.AuthProtected
import com.example.forum.screens.ForumHostScreen
import com.example.forum.screens.ForumPostCreation

fun NavGraphBuilder.setUpForumNavigation(
    navController: NavController
) {
    composable("/forum") {
        AuthProtected(
            ifNotLogin = {
                navController.navigate("/auth")
            }
        ) {
            ForumHostScreen(onPostCreationCLick = {
                navController.navigate("forum/post")
            })
        }
    }

    composable("forum/post") {
        AuthProtected(
            ifNotLogin = {
                navController.navigate("/auth")
            }
        ) {
            ForumPostCreation()
        }
    }

}
