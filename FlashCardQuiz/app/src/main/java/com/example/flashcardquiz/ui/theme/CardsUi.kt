package com.example.flashcardquiz.ui.theme

import android.app.Dialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardquiz.Database.CardsDatabase.CardsData
import com.example.flashcardquiz.Database.CardsDatabase.CardsViewModel
import com.example.flashcardquiz.Quiz.Name
import com.example.flashcardquiz.R
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
data class CardsUi(
    val id :Int,
    val name :String,
)

@Composable
fun CardsScaffold(navHostController: NavHostController,myViewModel:CardsViewModel,Deckid:Int,deckName:String) {
    var showDialog by remember { mutableStateOf(false) }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var check1 by remember { mutableStateOf(false) }
    var check2 by remember { mutableStateOf(false) }
    val cards by myViewModel.getCardCountForDeck(Deckid).observeAsState()
    val context = LocalContext.current

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
                        title = { Text(text = "Add Card", color = Color.Black) },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = question,
                                    onValueChange = { question = it },
                                    Modifier.padding(top = 4.dp),
                                    singleLine = true,
                                    label = {
                                        Text(text = "Question")
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
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = answer,
                                    onValueChange = { answer = it },
                                    Modifier.padding(top = 4.dp),
                                    singleLine = true,
                                    label = {
                                        Text(text = "Answer")
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Blue,
                                        unfocusedTextColor = Color.Black,
                                        focusedTextColor = Color.Black,
                                        cursorColor = Color.Blue,
                                        errorTextColor = Color.Red
                                    ),
                                    isError = check2
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (question == ""){
                                    check1 = true
                                }else if (answer == ""){
                                    check2 = true
                                }
                                else{
                                    myViewModel.addCard(CardsData(question = question, answer = answer, deckId = Deckid))
                                    question = ""
                                    answer = ""
                                    check1 = false
                                    check2 = false
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
                                    showDialog = false
                                    check1 = false
                                    check2 = false
                                    question = ""
                                    answer = ""
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
        },
        bottomBar = {
            NavigationBar(containerColor = BackgroundColor) {
                Row {

                    Button(onClick = { if(cards!! > 0) navHostController.navigate(Name(deckName, deckId = Deckid)) else Toast.makeText(
                        context,
                        "No card Found",
                        Toast.LENGTH_SHORT
                    ).show() },
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 5.dp, start = 10.dp, top = 10.dp, bottom = 10.dp)) {
                        Text(text = "Quiz")
                    }

                    Button(onClick = { showDialog = !showDialog },
                        Modifier
                            .fillMaxWidth(1f)
                            .padding(end = 10.dp, start = 5.dp, top = 10.dp, bottom = 10.dp)) {
                        Text(text = "Add Card")
                    }
                }
            }
        }
    ) {paddingValues ->
        cardUi(navHostController,myViewModel,Modifier.padding(paddingValues), deckid = Deckid)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cardUi(navHostController: NavHostController, myViewModel: CardsViewModel, modifier: Modifier = Modifier, deckid: Int) {
    var query by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var activity by remember { mutableStateOf(false) }
    val list by myViewModel.getCardsByDeck(deckid).observeAsState(emptyList())
    val search by myViewModel.searchResult(query,deckid).observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(true) }


    if (openDialog){
        AlertDialog(
            onDismissRequest = { openDialog = false
                question = ""
                answer = ""},
            title = { Text(text = "Enter Question and Answer") },
            text = {
                Column {
                    Text(text = "Question: $question")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Answer: $answer")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog = false
                        question = ""
                        answer = ""
                    }
                ) {
                    Text("Dismiss")
                }
            },
        )
    }

    LaunchedEffect(list) {
        delay(500)
        isLoading = false
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)
    ) {
        IconButton(onClick = { navHostController.navigate(DeckUi) }, Modifier.padding(8.dp)) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(50.dp))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(20.dp)) {
                Image(painter = painterResource(id = R.drawable.logo2), contentDescription = "logo")
                Text(text = "C A R D S", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp))
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), contentAlignment = Alignment.TopCenter) {
                DockedSearchBar(query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    },
                    active = activity,
                    onActiveChange = { activity = it },
                    placeholder = { Text(text = "Search Cards from Questions") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        IconButton(onClick = { if (query.isNotEmpty()) query = "" else activity = false }) {
                            if (activity) Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }) {
                    LazyColumn {
                        itemsIndexed(if(query.isEmpty()) list else search){ index, item ->
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .clickable {
                                    openDialog = true
                                    question = item.question
                                    answer = item.answer
                                }){
                                Row {
                                    Icon(Icons.Default.Search, contentDescription = "Icon", modifier = Modifier.size(30.dp))
                                    Text(text = item.question, fontSize = 24.sp, modifier = Modifier.padding(start = 10.dp))
                                }}
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                if (list.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Card Found", color = Color.White)
                    }
                } else {
                    list.let {
                        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                            itemsIndexed(list) { _, item ->
                                cardDataUi(myViewModel, item)
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun cardDataUi(myViewModel: CardsViewModel,item:CardsData,) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }
    if (openDialog){
        AlertDialog(
            onDismissRequest = { openDialog = false
                question = ""
                answer = ""},
            icon = {
                Row {
                    Image(painter = painterResource(id = R.drawable.logo2), contentDescription = "logo")
                    Text(text = "C A R D", fontWeight = FontWeight.Bold, fontSize = 24.sp) }

            },
            text = {
                Column {
                    Text(text = "Question: $question", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Answer: $answer", fontSize = 14.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog = false
                        question = ""
                        answer = ""
                    }
                ) {
                    Text("Done")
                }
            },
        )
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(Color.White)
        .clickable {
            openDialog = true
            question = item.question
            answer = item.answer
        }
    ){
        Row {
            Column(Modifier.fillMaxSize(0.7f)) {
                Text(text = item.question, fontWeight = FontWeight.Bold, fontSize = 16.sp,modifier = Modifier.padding(top = 20.dp, start = 40.dp))
                Text(text = item.answer, fontWeight = FontWeight.SemiBold, fontSize = 12.sp,modifier = Modifier.padding(bottom = 20.dp, start = 40.dp))
            }
            IconButton(onClick = { myViewModel.removeCard(item.id) },Modifier.align(Alignment.CenterVertically)) {
                Icon( Icons.Default.Delete ,contentDescription = "Delete", tint = Color.Red)

            }
        }

    }
}
