package com.mustafa.arif.fbapp.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.UiThreadTestRule
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.mustafa.arif.fbapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.mustafa.arif.fbapp.backend.model.Data
import com.mustafa.arif.fbapp.recycler.RecyclerAdapter
import org.hamcrest.Matchers.not
import java.util.ArrayList


/**
 * Created by musta on 3/3/2018.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    private var homeActivity: HomeActivity? = null

    @get:Rule
    var intentsTestRule: IntentsTestRule<HomeActivity> = IntentsTestRule(
            HomeActivity::class.java)
    @get:Rule
    var uiThreadTestRule: UiThreadTestRule = UiThreadTestRule()

    @Before
    fun setUp() {
        homeActivity = intentsTestRule.getActivity()
    }

    @Test
    fun test_ConfigureEulaTextView() {
        onView(withId(R.id.recycleView))
                .check(matches(isDisplayed()));

    }

    @Test
    fun test_CheckFloatBtn() {
        uiThreadTestRule.runOnUiThread { homeActivity?.showFloatingUpdateBtn(true) }
        onView(withId(R.id.floatingPostBtn))
                .check(matches(isDisplayed()))
    }


    @Test
    fun test_progressBar() {
        uiThreadTestRule.runOnUiThread { homeActivity?.showProgressBar(true) }
        onView(withId(R.id.progress_bar))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test_progressBar_hide() {
        uiThreadTestRule.runOnUiThread { homeActivity?.showProgressBar(false) }
        onView(withId(R.id.progress_bar))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_customDialogBox() {
        uiThreadTestRule.runOnUiThread { homeActivity?.customDialogBox() }
        onView(withId(R.id.postEditText))
                .check(matches(isDisplayed()))
        onView(withId(R.id.postButton))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test_recycler() {
        val data: ArrayList<Data> = ArrayList<Data>()
        var recyclerAdapter = RecyclerAdapter()
        val tempData = Data()
        tempData.setMessage("-1")
        data!!.add(tempData)
        uiThreadTestRule.runOnUiThread { homeActivity?.setRecycleAdapter(recyclerAdapter,data) }
        onView(withId(R.id.recycleView))
                .check(matches(isDisplayed()))

    }

    @Test
    fun test_recycler_updateRecyclerAdapter() {
        val data: ArrayList<Data> = ArrayList<Data>()
        var recyclerAdapter = RecyclerAdapter()
        val tempData = Data()
        tempData.setMessage("-1")
        data!!.add(tempData)
        uiThreadTestRule.runOnUiThread { homeActivity?.updateRecyclerAdapter(recyclerAdapter,data) }
        onView(withId(R.id.recycleView))
                .check(matches(isDisplayed()))

    }


}



