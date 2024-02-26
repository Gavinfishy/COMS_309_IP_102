package com.example.topg;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecyclerViewMatcher {

    public static Matcher<View> withRecyclerView(final int recyclerViewId) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                return recyclerView != null && recyclerView.getId() == recyclerViewId;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("No RecyclerView found with id: " + recyclerViewId);
            }
        };
    }

    public static Matcher<View> atPositionOnView(
            final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.ViewHolder viewHolder = recyclerView
                        .findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return targetView != null && targetView.getId() == targetViewId;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Could not find ViewHolder in RecyclerView");
            }
        };
    }
}
