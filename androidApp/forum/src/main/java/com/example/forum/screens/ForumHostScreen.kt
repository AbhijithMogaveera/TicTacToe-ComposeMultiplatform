package com.example.forum.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.forum.components.Post
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ForumHostScreen(
    onPostCreationCLick: () -> Unit = {},
    viewModelAuth: com.abhijith.auth.viewmodel.ViewModelAuth = koinViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Post")
                },
                actions = {
                    IconButton(onClick = onPostCreationCLick) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Create new post"
                        )
                    }
                    IconButton(onClick = {
                        viewModelAuth.logout()
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        val listState = rememberLazyListState()
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                content = {
                    items(count = 100) {
                        Post()
                    }
                },
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = listState
            )
        }
    }
}