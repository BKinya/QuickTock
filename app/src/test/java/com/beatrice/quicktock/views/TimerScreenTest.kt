package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class TimerScreenTest {

    @get: Rule
    val composeTestRule = createComposeRule()

//    private val repositoryModule = module {
//        single { FakeTimerRepository() }
//    }


    @Before
    @Throws(Exception::class)
    fun setUp(){
//        loadKoinModules(repositoryModule)
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @After
    fun tearDown(){
//        unloadKoinModules(repositoryModule)
    }


    @Test
    fun testStartCountingDown(){
        launchTimerScreen( composeTestRule = composeTestRule){
            startMainActivity()
            timerScreenIsPresent()
            clickPlayButton()
        }verify{
            countingDownScreenIsPresent() // TODO: Fix this
            playButtonNotPresent()
            pauseButtonPresent()
            stopButtonPresent()
        }

    }
}