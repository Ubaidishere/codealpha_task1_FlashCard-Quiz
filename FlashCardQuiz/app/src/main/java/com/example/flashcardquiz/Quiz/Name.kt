package com.example.flashcardquiz.Quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flashcardquiz.ui.theme.BackgroundColor
import com.example.flashcardquiz.ui.theme.ButtonColor
import com.example.flashcardquiz.ui.theme.DeckUi
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val deckName:String,
    val deckId: Int,
)

@Composable
fun name(navHostController: NavHostController,deckName: String,deckId:Int) {
    var name by remember { mutableStateOf("") }
    var check by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor), contentAlignment = Alignment.Center){
        Box(modifier = Modifier.fillMaxSize()){
            IconButton(onClick = {navHostController.navigate(DeckUi)},Modifier.padding(8.dp)) {
                Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White,modifier=Modifier.size(50.dp))
            }
        }
        Column {
            Text(
                text = "$deckName Quiz",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp)
                )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Welcome", fontWeight = FontWeight.Bold,modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text(text = "Please Enter your name",modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it},
                        Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        label = {
                            Text(text = "Enter Your Name")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Blue,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            cursorColor = Color.Blue,
                            errorTextColor = Color.Red
                        ),
                        isError = check)
                    Button(
                        colors = ButtonDefaults.buttonColors(if (check) Color.Red else ButtonColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            if(name.isEmpty()){
                                check = true
                            }else{
                                navHostController.navigate(QuizUi(name,deckId))
                            }
                        }){
                        Text(text = "Next")
                    }
                }
            }
        }

}
}