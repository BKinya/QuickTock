package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.views.util.launchTimerScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


//@RunWith(RobolectricTestRunner::class)
//@Config(application = TestApplication::class)
//class SetTimerTest {
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()
//    /**
//     * IDLE => Send setTimer event.. verify the UI
//     * Then type the numbers... click a button then verify the numbers
//     */
//
// @Test
// fun test1(){
//     // will checking the state automatically happen.. we'll see
//     launchTimerScreen(composeTestRule){
//
//     }
// }
//
//}