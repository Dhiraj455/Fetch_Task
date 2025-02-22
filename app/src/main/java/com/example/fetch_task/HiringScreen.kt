package com.example.fetch_task

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fetch_task.data.Hiring

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiringScreen(viewModel: HiringViewModel) {
    val groupedItems by viewModel.groupedItems.observeAsState(emptyMap())
    val selectedListId by viewModel.selectedListId.observeAsState(1)

    Scaffold( topBar = {
        Surface(
            color = Color(0xFF8230F5),
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        "Fetch List",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
            )
        }
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategorySelector(groupedItems.keys.toList(), selectedListId) { viewModel.setSelectedListId(it) }
            ListScreen(groupedItems[selectedListId] ?: emptyList())
        }
    }
}

@Composable
fun ListScreen(items: List<Hiring>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        itemsIndexed(items) { index, item ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = item.name ?: "Unknown",
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySelector(listIds: List<Int>, selectedListId: Int, onListIdSelected: (Int) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        items(listIds.size) { index ->
            val listId = listIds[index]
            Button(
                onClick = { onListIdSelected(listId) },
                modifier = Modifier.padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (listId == selectedListId) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("List ID: $listId")
            }
        }
    }
}