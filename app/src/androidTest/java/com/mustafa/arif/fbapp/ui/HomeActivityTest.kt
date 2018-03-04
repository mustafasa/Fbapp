package com.mustafa.arif.fbapp.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.mustafa.arif.fbapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Created by musta on 3/3/2018.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    private var homeActivity: HomeActivity? = null

    @get:Rule
    var intentsTestRule: IntentsTestRule<HomeActivity> = IntentsTestRule(
            HomeActivity::class.java)

    @Before
    fun setUp() {
        homeActivity = intentsTestRule.getActivity()
    }

    @Test
    fun test_ConfigureEulaTextView(){
        onView(withId(R.id.recycleView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.floatingPostBtn))
                .check(matches(isDisplayed()));
    }

}



