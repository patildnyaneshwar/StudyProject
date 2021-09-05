package com.project.study.ui.photos

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.project.study.R
import com.project.study.ui.PhotosActivity
import org.hamcrest.CoreMatchers.not

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosFragmentTest {

    @get:Rule
    var activityRule = ActivityTestRule(PhotosActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun getRVItemCount(): Int {
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
        return recyclerView.adapter?.itemCount ?: 0
    }

    @Test
    fun isRecyclerViewLoadItemsSuccessfully_performItemClickAndShowToast() {
        if (getRVItemCount() > 0) {
            onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

            onView(withText(R.string.item_clicked))
                .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        }
    }
}