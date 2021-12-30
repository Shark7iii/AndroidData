package com.jnu.booklistmainactivity.Test;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.jnu.booklistmainactivity.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TeacherActivityTest {

    @Rule
    public ActivityTestRule<TeacherActivity> mActivityTestRule = new ActivityTestRule<>(TeacherActivity.class);

    @Test
    public void teacherActivityTest() {
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.teacher_listView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        linearLayout.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.teacher_large_imageView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.teacher_desc_textView), withText("张海霞，北京大学教授国际大学生iCAN创新创业大赛发起人、" +
                                "主席北京大学信息科学技术学院教授全球华人微纳米分子系统学会秘书长IEEE NTC 北京分会主席。"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("张海霞，北京大学教授国际大学生iCAN创新创业大赛发起人、" +
                "主席北京大学信息科学技术学院教授全球华人微纳米分子系统学会秘书长IEEE NTC 北京分会主席。")));

        pressBack();

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.teacher_listView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.teacher_large_imageView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.teacher_desc_textView), withText("陈江，北京大学信息科学技术学院副教授，高等学校电路和信号系统教学与教材研究会常务理事，中国通信理论与信号处理委员会委员。"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("陈江，北京大学信息科学技术学院副教授，高等学校电路和信号系统教学与教材研究会常务理事，中国通信理论与信号处理委员会委员。")));

        pressBack();

        DataInteraction linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.teacher_listView),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(2);
        linearLayout3.perform(click());

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.teacher_large_imageView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.teacher_desc_textView), withText("叶蔚，北京大学软件工程国家工程研究中心副研究员，创办了技术学习服务平台天码营(http://tianmaying.com)与软件开发协同工具Onboard(http://onboard.cn)。"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("叶蔚，北京大学软件工程国家工程研究中心副研究员，创办了技术学习服务平台天码营(http://tianmaying.com)与软件开发协同工具Onboard(http://onboard.cn)。")));

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
