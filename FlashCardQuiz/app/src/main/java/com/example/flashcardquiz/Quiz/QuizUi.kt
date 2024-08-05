package com.example.flashcardquiz.Quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardquiz.Database.CardsDatabase.CardsData
import com.example.flashcardquiz.Database.CardsDatabase.CardsViewModel
import com.example.flashcardquiz.ui.theme.BackgroundColor
import com.example.flashcardquiz.ui.theme.ButtonColor
import com.example.flashcardquiz.ui.theme.DeckUi
import com.example.flashcardquiz.ui.theme.GreenColor
import kotlinx.serialization.Serializable

@Serializable
data class QuizUi(
    val name : String,
    val deckid : Int,
)

@Composable
fun quizUi(name: String, myViewModel: CardsViewModel, deckId: Int,navHostController: NavHostController) {
    val items by myViewModel.getCardsByDeck(deckId = deckId).observeAsState()
    val total by myViewModel.getCardCountForDeck(deckId).observeAsState()
    var check by remember { mutableStateOf(false) }
    var alert by remember { mutableStateOf(false) }
    var correct by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)) {
        Box(modifier = Modifier.fillMaxSize()){
            IconButton(onClick = { navHostController.navigate(DeckUi) },Modifier.padding(8.dp)) {
                Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White,modifier=Modifier.size(35.dp))
            }}

        Column(
            Modifier
                .fillMaxSize()
                .padding()) {
            Row(modifier = Modifier.padding(20.dp).align(Alignment.CenterHorizontally)) {
                Image(painter = painterResource(id = com.example.flashcardquiz.R.drawable.logo2), contentDescription = "logo")
                Text(text = "Q U I Z", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp))
            }
            Divider()

            items?.let { cardItems ->
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    cardItems.forEachIndexed { index, item ->
                        QuizDataUi(index = index, total = total ?: 0, item = item, check = check){isCorrect ->
                            if (isCorrect) {
                                correct++
                            }

                        }
                        if (total?.minus(1) == index) {
                            Button(
                                colors = ButtonDefaults.buttonColors(ButtonColor),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {
                                    check = true
                                    alert = true
                                }) {
                                Text(text = "Check Result")
                            }
                        }
                    }
                }
            }
        }
        if (alert){
        AlertDialog(
            onDismissRequest = { alert = false },
            confirmButton = {  },
            icon = {
                Image(painter = painterResource(id = com.example.flashcardquiz.R.drawable.result), contentDescription = "Result" )
            },
            title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Result: $name!", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Your Score: $correct/$total",fontSize = 14.sp, color = Color.Gray)
                        Button(
                            colors = ButtonDefaults.buttonColors(ButtonColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            shape = RoundedCornerShape(5.dp),
                            onClick = {
                                alert = false
                            }){
                            Text(text = "Review Result")
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(ButtonColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            shape = RoundedCornerShape(5.dp),
                            onClick = {
                                alert = false
                                navHostController.navigate(DeckUi)
                            }){
                            Text(text = "Home")
                        }
                    }

            }
        )}
    }
}

@Composable
fun QuizDataUi(index: Int, total: Int, item: CardsData, check: Boolean,onCheck:(Boolean) -> Unit) {
    var answer by remember { mutableStateOf("") }
    var checker by remember { mutableStateOf(false) }

    if (check) {
        checker = item.answer != answer
    } else {
        checker = false
    }

    androidx.compose.runtime.LaunchedEffect(check) {
        checker = if (check) {
            item.answer != answer
        } else {
            false
        }
        onCheck(if (check) {
            item.answer == answer
        } else {
            false
        })
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Column {
            Text(text = "Question ${index + 1}/$total", color = Color.White, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonColor)
                    .padding(20.dp)) {
                Text(text = item.question, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Your Answer:", color = Color.White)
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                readOnly = check,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = if (check) { if (checker) Color.Red else GreenColor } else ButtonColor,
                    cursorColor = ButtonColor,
                    disabledBorderColor = if (check) { if (checker) Color.Red else GreenColor } else ButtonColor,
                    focusedTextColor = Color.White,
                    unfocusedContainerColor = if (check) { if (checker) Color.Red else GreenColor } else BackgroundColor,
                    focusedContainerColor = if (check) { if (checker) Color.Red else GreenColor } else BackgroundColor,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
            if (checker){
                Text(
                    text = "Answer is: ${item.answer}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 16.dp)
                )
            }
        }
    }
}

