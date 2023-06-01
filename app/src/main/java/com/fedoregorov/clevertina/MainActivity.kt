package com.fedoregorov.clevertina

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : ComponentActivity() {
    private var currentScreen: Screen by mutableStateOf(Screen.Menu) // Изначально отображается Screen1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenSwitcher(currentScreen) {
                newScreen -> currentScreen = newScreen
            }

        }
    }
}

sealed class Screen {
    object Menu: Screen()
    object Game: Screen()
    object Screamer: Screen()
}

@Composable
fun ScreenSwitcher(
    currentScreen: Screen,
    navigateToScreen: (Screen) -> Unit
) {
    when (currentScreen) {
        is Screen.Menu -> MainMenu(navigateToScreen)
        is Screen.Game -> Game(navigateToScreen)
        is Screen.Screamer -> Screamer()
    }
}

@Composable
fun MainMenu(navigateToScreen: (Screen) -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val fonts = FontFamily(
        Font(R.font.segoepr, weight = FontWeight.Normal),
        Font(R.font.segoeprb, weight = FontWeight.Black)
    )

    AnimatedVisibility (
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 350, easing = LinearEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 380)))

    {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                Image(
                    painter = painterResource(R.drawable.clevertina),
                    contentDescription = "Clevertina logo",
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                Button(
                    onClick = {
                        visible = false
                        Timer().schedule(500) {
                            navigateToScreen(Screen.Game)
                        }
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008000))
                ) {
                    Text(
                        text = "Играть", fontSize = 20.sp, fontFamily = fonts,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }

    return LaunchedEffect(true) {
        visible = !visible
    }
}

@Composable
fun Game(navigateToScreen: (Screen) -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val fonts = FontFamily(
        Font(R.font.segoepr, weight = FontWeight.Normal),
        Font(R.font.segoeprb, weight = FontWeight.Black)
    )
    val context = LocalContext.current

    AnimatedVisibility (
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 350, easing = LinearEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 380)))
    {
        Column {
            val bs = 55
            var r1 by rememberSaveable{mutableStateOf("")}
            var r2 by rememberSaveable{mutableStateOf("")}
            var r3 by rememberSaveable{mutableStateOf("")}
            var r4 by rememberSaveable{mutableStateOf("")}
            var r5 by rememberSaveable{mutableStateOf("")}
            val focusManager = LocalFocusManager.current
            val df = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black, disabledIndicatorColor = Color.Black)
            val fs = TextStyle(fontSize = 17.sp, fontFamily = fonts,
                fontWeight = FontWeight.Black)

            Box (modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                Text(
                    text = "То, чего боится человек", style = TextStyle(fontSize = 23.sp,
                        fontFamily = fonts, fontWeight = FontWeight.Black),
                    modifier = Modifier.align(Alignment.BottomCenter))
            }
            Row (modifier = Modifier
                .fillMaxSize()
                .weight(0.2f), horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(value = r1, singleLine = true, onValueChange = {
                    r1 = it.take(1)
                    if (it.length > 1) focusManager.moveFocus(FocusDirection.Down)},
                    colors = df, textStyle = fs, shape = RoundedCornerShape(4.dp), modifier = Modifier
                        .size(size = bs.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                )
                OutlinedTextField(value = r2, singleLine = true, onValueChange = {
                    r2 = it.take(1)
                    if (it.length > 1) focusManager.moveFocus(FocusDirection.Down)},
                    colors = df, textStyle = fs, shape = RoundedCornerShape(4.dp), modifier = Modifier
                        .size(size = bs.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                )

                OutlinedTextField(value = r3, singleLine = true, onValueChange = {
                    r3 = it.take(1)
                    if (it.length > 1) focusManager.moveFocus(FocusDirection.Down)},
                    colors = df, textStyle = fs, shape = RoundedCornerShape(4.dp), modifier = Modifier
                        .size(size = bs.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                )

                OutlinedTextField(value = r4, singleLine = true, onValueChange = {
                    r4 = it.take(1)
                    if (it.length > 1) focusManager.moveFocus(FocusDirection.Down)},
                    colors = df, textStyle = fs, shape = RoundedCornerShape(4.dp), modifier = Modifier
                        .size(size = bs.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                )

                OutlinedTextField(value = r5, singleLine = true, onValueChange = {
                    r5 = it.take(1)
                    if (it.length > 1) focusManager.moveFocus(FocusDirection.Down)
                },
                    colors = df, textStyle = fs, shape = RoundedCornerShape(6.dp), modifier = Modifier
                        .size(size = bs.dp),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                )
            }

            Box (modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                Button(
                    onClick = {
                        if ((r1 + r2 + r3 + r4 + r5).uppercase(Locale.ROOT) == "СТРАХ") {
                             navigateToScreen(Screen.Screamer)
                        } else {
                            Toast.makeText(context, "Неверно! Попробуйте ещё раз!", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008000))
                ) {
                    Text(
                        text = "Проверка", fontSize = 20.sp, fontFamily = fonts,
                        fontWeight = FontWeight.Black
                    )
                }
            }


        }
    }

    return LaunchedEffect(true) {
        visible = !visible
    }
}

@Composable
fun Screamer() {

    val context = LocalContext.current
    val mediaPlayer = MediaPlayer.create(context, R.raw.screamer)
    var sizeState by remember { mutableStateOf(1.dp) }
    val size by animateDpAsState(targetValue = sizeState, tween(durationMillis = 90,
        easing = LinearEasing))

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            Image(
                modifier = Modifier.size(size),
                painter = painterResource(R.drawable.screamer),
                contentScale = ContentScale.Crop,
                contentDescription = "Screamer"
            )
        }

    return LaunchedEffect(true) {
        mediaPlayer.start()
        sizeState *= 1000
    }
}