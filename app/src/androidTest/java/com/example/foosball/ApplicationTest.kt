/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.foosball

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.foosball.presentation.MainActivity
import org.junit.Test

class ApplicationTest {

    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText("LEADERBOARDS")).check(matches(isDisplayed()))
        onView(withText("GAMES")).check(matches(isDisplayed()))

        onView(withText("GAMES")).perform(ViewActions.click())
        onView(withId(R.id.btnAdd)).check(matches(isDisplayed()))
        onView(withId(R.id.btnAdd)).perform(ViewActions.click())
        onView(withId(R.id.etFirstPlayer)).check(matches(isDisplayed()))
        onView(withId(R.id.etFirstPlayer)).perform(replaceText("Player1"))
        onView(withId(R.id.etSecondPlayer)).perform(replaceText("Player2"))
        onView(withId(R.id.etFirstScore)).perform(replaceText("2"))
        onView(withId(R.id.etSecondScore)).perform(replaceText("3"))
        onView(withId(R.id.btnAddResult)).perform(ViewActions.click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.game_added)))
    }
}
