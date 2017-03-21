package com.app.base.utility;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.app.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j3p0n on 12/31/2016.
 */

public class FragmentStack {

    public interface OnBackPressedHandlingFragment {
        boolean onBackPressed();
    }

    private Activity activity;
    private FragmentManager manager;
    private int containerId;

    public FragmentStack(Activity activity, FragmentManager manager, int containerId) {
        this.activity = activity;
        this.manager = manager;
        this.containerId = containerId;
    }

    /**
     * Returns the number of fragments in the stack.
     *
     * @return the number of fragments in the stack.
     */
    public int size() {
//        return getFragments().size();
        return manager.getBackStackEntryCount();
    }

    /**
     * Pushes a fragment to the top of the stack.
     */
    public void push(Fragment fragment, String tagName) {
        String tag = fragment.getClass().getSimpleName();

        if (tagName != null) {
            tag = tagName;
        }
        Fragment top = peek();
        if (top != null) {
            boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);
            if (!fragmentPopped && manager.findFragmentByTag(tag) == null) {
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .remove(top)
                        .add(containerId, fragment, tag)
                        .addToBackStack(tag)
                        .commit();
            }
        } else {
            manager.beginTransaction()
                    .add(containerId, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }

        manager.executePendingTransactions();
    }

    /**
     * Pushes a fragment to the top of the stack.
     */
    public void pushAdd(Fragment fragment, String tagName) {
        String tag = fragment.getClass().getSimpleName();

        if (tagName != null) {
            tag = tagName;
        }
        Fragment top = peek();
        if (top != null) {
            boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);
            if (!fragmentPopped && manager.findFragmentByTag(tag) == null) {
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(containerId, fragment, tag)
                        .addToBackStack(tag)
                        .commit();
            }
        } else {
            manager.beginTransaction()
                    .add(containerId, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }

        manager.executePendingTransactions();
    }

    /**
     * Pops the top item if the stack.
     * If the fragment implements {@link OnBackPressedHandlingFragment}, calls {@link OnBackPressedHandlingFragment#onBackPressed()} instead.
     * If {@link OnBackPressedHandlingFragment#onBackPressed()} returns false the fragment gets popped.
     *
     * @return true if a fragment has been popped or if {@link OnBackPressedHandlingFragment#onBackPressed()} returned true;
     */
    public boolean back() {
        Fragment top = peek();
        if (top instanceof OnBackPressedHandlingFragment) {
            if (((OnBackPressedHandlingFragment) top).onBackPressed()) {
                return true;
            }

        }
        return pop();
    }

    /**
     * Pops the topmost fragment from the stack.
     * The lowest fragment can't be popped, it can only be replaced.
     *
     * @return false if the stack can't pop or true if a top fragment has been popped.
     */
    public boolean pop() {
        if (manager.getBackStackEntryCount() == 1)
            return false;
        manager.popBackStackImmediate();
        return true;
    }

    /**
     * Replaces stack contents with just one fragment.
     */
    public void replace(Fragment fragment) {
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(containerId, fragment, indexToTag(0))
                .commit();
        manager.executePendingTransactions();
    }

    /**
     * Returns the topmost fragment in the stack.
     */
    public Fragment peek() {
        return manager.findFragmentById(containerId);
    }

    /**
     * Returns a back fragment if the fragment is of given class.
     * If such fragment does not exist and activity implements the given class then the activity will be returned.
     *
     * @param fragment     a fragment to search from.
     * @param callbackType a class of type for callback to search.
     * @param <T>          a type of callback.
     * @return a back fragment or activity.
     */
    @SuppressWarnings("unchecked")
    public <T> T findCallback(Fragment fragment, Class<T> callbackType) {

        Fragment back = getBackFragment(fragment);

        if (back != null && callbackType.isAssignableFrom(back.getClass()))
            return (T) back;

        if (callbackType.isAssignableFrom(activity.getClass()))
            return (T) activity;

        return null;
    }

    private Fragment getBackFragment(Fragment fragment) {
        List<Fragment> fragments = getFragments();
        for (int f = fragments.size() - 1; f >= 0; f--) {
            if (fragments.get(f) == fragment && f > 0)
                return fragments.get(f - 1);
        }
        return null;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>(manager.getBackStackEntryCount() + 1);
        for (int i = 0; i < manager.getBackStackEntryCount() + 1; i++) {
            Fragment fragment = manager.findFragmentByTag(indexToTag(i));
            if (fragment != null)
                fragments.add(fragment);
        }
        return fragments;
    }

    private String indexToTag(int index) {
        return Integer.toString(index);
    }
}