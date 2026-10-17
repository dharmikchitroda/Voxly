package com.example.timerapp.ui.timer


import android.Manifest
import android.util.Log
import android.widget.GridLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.timerapp.R
import com.example.timerapp.ui.timer.components.Bottomsheet
import com.example.timerapp.ui.timer.components.CustomDurationbtn
import com.example.timerapp.ui.timer.components.PresetChip
import com.example.timerapp.ui.timer.components.SoundChip
import com.example.timerapp.ui.timer.components.TimeChooserDialog
import com.example.timerapp.ui.timer.components.Topbar
import com.example.timerapp.util.SounPoolManager
import com.example.timerapp.util.TtsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val viewmodel: TimerViewmodel = viewModel()
    val time by viewmodel.time.collectAsState()
    val Intialtime by viewmodel.Intialtime.collectAsState()
    val TimerRuning by viewmodel.TimerRuning.collectAsState()
    val listofchips by viewmodel.ChipsList.collectAsState()
    val soundOn by viewmodel.soundOn.collectAsState()
    val DefultSoundIntial by viewmodel.DefultSoundIntial.collectAsState()
    val selectedIndex by viewmodel.selectedIndex.collectAsState()
    val IsEnableVoiceAnnoucment by viewmodel.IsEnableVoiceAnnoucment.collectAsState()


    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val ttsManager = remember { TtsManager(context) }

    val mediaManager = remember(DefultSoundIntial) {
        SounPoolManager(context, DefultSoundIntial)
    }

    DisposableEffect(ttsManager) {
        onDispose {
            ttsManager.Shutdown()
        }
    }

    DisposableEffect(mediaManager) {

        onDispose { mediaManager.release() }
    }


    LaunchedEffect(time) {

        // completion sound
        if (time == 0 && soundOn) mediaManager.play()

        // voice countdown
        val speakFrom = when (selectedIndex) {
            1 -> 5
            2 -> 10
            else -> 0   // Off
        }

        if (speakFrom > 0 && TimerRuning && time in 1..speakFrom) {
            ttsManager.Speak(time.toString())
        }

        if (IsEnableVoiceAnnoucment && time == 10) {
            ttsManager.Speak("last 10 second")
        }
    }


    Scaffold(
        topBar = {
            Topbar() {

                showBottomSheet = !showBottomSheet

            }
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Round text timer
            Box(
                Modifier
                    .size(300.dp),
                Alignment.Center,
            ) {
                CircularProgressIndicator(
                    progress = { viewmodel.progressinf() },
                    modifier = Modifier.size(300.dp),
                    color = if (viewmodel.progressinf() > 0.75f) colorResource(R.color.Secondry)
                    else colorResource(R.color.Primary),
                    strokeWidth = 15.dp,
                    strokeCap = StrokeCap.Round,
                    gapSize = 0.dp,
                    trackColor = Color.Black.copy(alpha = 0.08f),
                )

                Text(
                    viewmodel.TimerFormatter(time),
                    fontSize = 105.sp,
                    fontFamily = FontFamily(Font(R.font.ds_digit)),
                    modifier = Modifier.padding(30.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
// sound btn
            SoundChip(
                isOn = soundOn,
                onClick = { viewmodel.SetsoundOnOff() })

            Spacer(Modifier.height(25.dp))

// start btn
            Button(
                {
                    viewmodel.Start()
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.Primary)
                )
            ) {

                val play = painterResource(R.drawable.outline_play_arrow_24)
                val pause = painterResource(R.drawable.outline_pause_24)

                Icon(
                    painter = if (TimerRuning) pause else play,
                    contentDescription = null,
                    Modifier.size(20.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(if (TimerRuning) "Stop" else "Start", fontSize = 16.sp)

            }

            Spacer(Modifier.height(5.dp))

// reset btn
            OutlinedButton(
                {
                    viewmodel.stop()
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                border = BorderStroke(1.dp, colorResource(R.color.Primary)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_refresh_24),
                    contentDescription = null,
                    Modifier.size(20.dp),
                    tint = Color(0xFF191C1E)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Reset",
                    fontSize = 16.sp,
//                  color = Color(0xFF191C1E)
                    color = Color(0xFF191C1E)
                )
            }

// text
            Text(
                "QUICK PRESETS",
                Modifier
                    .align(Alignment.Start)
                    .padding(top = 40.dp),
                fontWeight = FontWeight.Bold

            )
            Spacer(Modifier.height(8.dp))

// small capsules
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listofchips.forEach {
                    PresetChip(
                        label = it.label,
                        {
                            viewmodel.setime(it.seconds)
                        },
                        modifier = Modifier.weight(1f),
                        isActive = it.seconds == Intialtime
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

// Custom Duration button
            CustomDurationbtn {
                showDialog = true
            }
        }

        if (showDialog) {
            TimeChooserDialog(viewmodel = viewmodel) {
                showDialog = false
            }
        }

        if (showBottomSheet) {
            Bottomsheet() {
                showBottomSheet = false
            }
        }
    }
}


@Preview("Light", showBackground = true, showSystemUi = true, device = Devices.PIXEL_7A)
@Composable
private fun preview() {

    TimerScreen()
}

// navigation
/*
data class BottomItem(val route: String, val label: String, val icon: ImageVector)

val items = listOf(
    BottomItem("home", "Home", Icons.Default.Home),
    BottomItem("profile", "Profile", Icons.Default.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = { TopAppBar(title = { Text("My App") }) },
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)   // very important
        ) {
            composable("home") { Text("Home Screen") }
            composable("profile") { Text("Profile Screen") }
        }
    }
} */