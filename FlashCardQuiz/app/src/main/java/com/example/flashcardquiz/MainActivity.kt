package com.example.flashcardquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.flashcardquiz.Database.CardsDatabase.CardsViewModel
import com.example.flashcardquiz.Database.DecksData
import com.example.flashcardquiz.Database.deckViewModel
import com.example.flashcardquiz.Quiz.Name
import com.example.flashcardquiz.Quiz.QuizUi
import com.example.flashcardquiz.Quiz.name
import com.example.flashcardquiz.Quiz.quizUi
import com.example.flashcardquiz.ui.theme.BackgroundColor
import com.example.flashcardquiz.ui.theme.ButtonColor
import com.example.flashcardquiz.ui.theme.CardsScaffold
import com.example.flashcardquiz.ui.theme.CardsUi
import com.example.flashcardquiz.ui.theme.DeckUi
import com.example.flashcardquiz.ui.theme.FlashCardQuizTheme
import com.example.flashcardquiz.ui.theme.SplashScreen
import com.example.flashcardquiz.ui.theme.myScaffold
import com.example.flashcardquiz.ui.theme.splashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashCardQuizTheme {
                    val myViewModel = ViewModelProvider(this)[deckViewModel::class.java]
                val myViewModel2 = ViewModelProvider(this)[CardsViewModel::class.java]
                    val navHostController = rememberNavController()
                    NavHost(navController = navHostController, startDestination = SplashScreen) {
                        composable<SplashScreen> { com.example.flashcardquiz.ui.theme.splashScreen(navHostController) }
                        composable<DeckUi> { myScaffold(navHostController,myViewModel,myViewModel2) }
                        composable<CardsUi> {
                            val data = it.toRoute<CardsUi>()
                            CardsScaffold(
                            navHostController = navHostController,
                            myViewModel = myViewModel2,Deckid = data.id, deckName = data.name
                        ) }
                        composable<Name> {
                            val data = it.toRoute<Name>()
                            name(navHostController = navHostController, deckName = data.deckName, deckId = data.deckId) }
                        composable<QuizUi> {
                            val data = it.toRoute<QuizUi>()
                            quizUi(name = data.name,myViewModel2,data.deckid,navHostController)}
                    }
                }
        }
    }
}