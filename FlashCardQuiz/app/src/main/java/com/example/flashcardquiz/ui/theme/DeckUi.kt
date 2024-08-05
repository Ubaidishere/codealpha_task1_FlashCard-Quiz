package com.example.flashcardquiz.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardquiz.Database.CardsDatabase.CardsViewModel
import com.example.flashcardquiz.Database.DecksData
import com.example.flashcardquiz.Database.deckViewModel
import com.example.flashcardquiz.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.Serializable


@Serializable
object DeckUi

@Composable
fun myScaffold(navHostController: NavHostController,myViewModel: deckViewModel,myViewModel2:CardsViewModel,modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var deck by remember { mutableStateOf("") }
    var check1 by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = ButtonColor,
                onClick = {showDialog = !showDialog }) {
                Icon(Icons.Default.Add, contentDescription = "", tint = Color.White)
                if (showDialog){
                    AlertDialog(
                        containerColor = Color.White,
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Add Deck", color = Color.Black) },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = deck,
                                    onValueChange = { deck = it },
                                    Modifier.padding(top = 4.dp),
                                    singleLine = true,
                                    label = {
                                        Text(text = "Deck Name")
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Blue,
                                        unfocusedTextColor = Color.Black,
                                        focusedTextColor = Color.Black,
                                        cursorColor = Color.Blue,
                                        errorTextColor = Color.Red
                                    ),
                                    isError = check1
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (deck == ""){
                                    check1 = true
                                }
                                else{
                                    myViewModel.addDeck(addDeck = DecksData(deck = deck))
                                    deck = ""
                                    check1 = false
                                    showDialog = false
                                }
                            },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (check1) Color.Red else ButtonColor,
                                    contentColor =  Color.White
                                )){
                                Text(text = "Submit")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    check1 = false
                                    deck = ""
                                    showDialog = false
                                },colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                )){
                                Text(text = "Cancel")
                            }
                        }
                    )

                }
            }
        }
    ){padding->
        DeckUi(navHostController,myViewModel = myViewModel,myViewModel2, modifier = Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckUi(navHostController: NavHostController, myViewModel: deckViewModel,myViewModel2:CardsViewModel, modifier: Modifier = Modifier) {

    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    var activity by remember { mutableStateOf(false) }
    val list by myViewModel.getDeck.observeAsState(emptyList())
    val search by myViewModel.searchResult(query).observeAsState(emptyList())


    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .padding(20.dp)) {
                Image(painter = painterResource(id = R.drawable.logo2), contentDescription = "logo")
                Text(text = "D E C K S", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp))
            }
            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 10.dp), contentAlignment = Alignment.TopCenter){
                DockedSearchBar(query = query,
                    onQueryChange = {
                        query = it },
                    onSearch = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    },
                    active = activity,
                    onActiveChange = {activity = it},
                    placeholder = { Text(text = "Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = { IconButton(onClick = {if (query.isNotEmpty())query = "" else activity = false }) {if (activity) Icon(
                        Icons.Default.Close, contentDescription = "Close")
                    }
                    }) {
                        LazyColumn {
                            itemsIndexed(if(query.isEmpty()) list else search){ index, item ->
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .clickable {
                                    navHostController.navigate(CardsUi(item.id,item.deck))
                                }){
                                Row {
                                Icon(Icons.Default.Search, contentDescription = "Icon", modifier = Modifier.size(30.dp))
                                Text(text = item.deck, fontSize = 24.sp, modifier = Modifier.padding(start = 10.dp))
                            }}
                            }
                        }
                }

            }

            if (list.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Deck Found", color = Color.White)
                }
            }else {

                list.let {
                    LazyColumn {
                        itemsIndexed(list) { index, item ->
                            dataUi(navHostController,myViewModel,myViewModel2, item)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun dataUi(navHostController: NavHostController,myViewModel: deckViewModel,myViewModel2:CardsViewModel, item: DecksData) {
    val cardCount by myViewModel2.getCardCountForDeck(item.id).observeAsState(0)
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(Color.White)
        .clickable { navHostController.navigate(CardsUi(item.id, item.deck)) }
    ){
        IconButton(onClick = { myViewModel.removeDeck(item.id) }, Modifier.align(Alignment.CenterEnd)) {
            Icon( Icons.Default.Delete ,contentDescription = "Delete", tint = Color.Red)

        }
        Column {
            Text(text = item.deck, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier = Modifier.padding(top = 20.dp, start = 40.dp))
            Text(text = "Total Cards: $cardCount", fontWeight = FontWeight.SemiBold, fontSize = 12.sp,modifier = Modifier.padding(bottom = 20.dp, start = 40.dp))
        }
    }
}
