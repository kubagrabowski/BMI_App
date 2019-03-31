package com.example.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

@Test
fun mainActivityTest() {
    val massEditText = onView(withId(R.id.editmasa))
    massEditText.perform(scrollTo(), replaceText("65"), closeSoftKeyboard())

    val heightEditText = onView(withId(R.id.editwzrost))
    heightEditText.perform(scrollTo(), replaceText("170"), closeSoftKeyboard())

    val countButton = onView(withId(R.id.policzbmi))
    countButton.perform(click())

    val bmiTextView = onView(withId(R.id.wylbmi))
    bmiTextView.check(matches(withText("22.49")))
}

@Test
fun mainActivityTestVisible() {
    onView(allOf(withId(R.id.editmasa), isDisplayed()))
    onView(allOf(withId(R.id.editwzrost), isDisplayed()))
    onView(allOf(withId(R.id.cmoster), isDisplayed()))
    val massText = onView(allOf(withId(R.id.masa), isDisplayed()))
    val heightText = onView(allOf(withId(R.id.wzrost), isDisplayed()))
    onView(allOf(withId(R.id.wylbmi), isDisplayed()))
    onView(allOf(withId(R.id.infobut), isDisplayed()))
    onView(allOf(withId(R.id.dodatkowy), isDisplayed()))
    onView(allOf(withId(R.id.policzbmi), isDisplayed()))

    massText.check(matches(withText("Mass[kg]\t")))
    heightText.check(matches(withText("Height[cm]\t")))

}

}