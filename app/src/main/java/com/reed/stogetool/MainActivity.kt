package com.reed.stogetool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.reed.stogetool.ui.theme.StogeToolTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {

    private val stockViewModel by viewModels<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockViewModel.viewModelScope.launch(Dispatchers.IO) {
            stockViewModel.init()
            stockViewModel.viewModelScope.launch(Dispatchers.Main) {
                setContent {
                    StogeToolTheme {
                        MyApp(stockViewModel)
                    }
                }
            }
        }

    }
}

@Composable
fun MyApp(stockViewModel: StockViewModel) {
    val items by stockViewModel.allItems.observeAsState(listOf())
    Stocks(items, { stockViewModel.addItem(it) }, { stockViewModel.removeItem(it) })
}

@Composable
private fun Stocks(
    items: List<StockItem> = listOf(),
    onAdd: (item: StockItem) -> Unit,
    onRemove: (item: StockItem) -> Unit
) {
    val (price, onPriceChange) = rememberSaveable { mutableStateOf("") }
    val (name, onNameChange) = rememberSaveable { mutableStateOf("") }

    val firstFact = 0.95
    val secondFact = 0.90
    val thirdFact = 0.85
    val forthFact = 0.80
    val fifthFact = 0.75
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("名字") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                )
                TextField(
                    value = price,
                    onValueChange = onPriceChange,
                    label = { Text("价格") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = {
                val origin = price.toDoubleOrNull()
                if (origin != null) {
                    onAdd(
                        StockItem(
                            origin = price,
                            name = name,
                            first = "第一次补仓:${String.format("%.2f", origin * firstFact)}",
                            second = "第二次补仓:${String.format("%.2f", origin * secondFact)}",
                            third = "第三次补仓:${String.format("%.2f", origin * thirdFact)}",
                            fourth = "第四次补仓:${String.format("%.2f", origin * forthFact)}",
                            fifth = "第五次补仓:${String.format("%.2f", origin * fifthFact)}",
                        )
                    )
                }

            }) {
                Text("添加")
            }
        }
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = items) { item ->
                CardItem(item = item, onRemove)
            }
        }
    }

}

@Composable
private fun CardItem(item: StockItem, onRemove: (item: StockItem) -> Unit) {

    val expanded = rememberSaveable { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.name)
                Text(item.first)
                Text(item.second)
                Text(item.third)
                Text(item.fourth)
                Text(item.fifth)
            }
            OutlinedButton(
                onClick = { onRemove(item) }
            ) {
                Text("删除")
            }
        }
    }
}