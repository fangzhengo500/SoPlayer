package com.loosu.soplayer.widget.videoview.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class AnimationGestureController extends AbsGestureController {

    private static final long ANIMATION_DURATION = 500;

    private ObjectAnimator mSeekShowAnimator;
    private ObjectAnimator mSeekHideAnimator;

    public AnimationGestureController(@NonNull Context context) {
        super(context);
    }

    @Override
    public void showSeekChange(long position, long duration) {
        cancelAnimator(mSeekHideAnimator);
        if (mSeekShowAnimator == null) {
            PropertyValuesHolder[] holders = {
                    PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutSeek.getAlpha(), 1),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, mLayoutSeek.getTranslationX(), 0),
            };
            mSeekShowAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutSeek, holders);
            mSeekShowAnimator.setDuration(ANIMATION_DURATION);
            mSeekShowAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    mSeekShowAnimator = null;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mSeekShowAnimator = null;
                }
            });
            mSeekShowAnimator.start();
        }
        super.showSeekChange(position, duration);

    }

    @Override
    public void hideSeekChange() {
        cancelAnimator(mSeekShowAnimator);
        if (mSeekHideAnimator == null) {
            PropertyValuesHolder[] holders = {
                    PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutSeek.getAlpha(), 0),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, mLayoutSeek.getTranslationX(), -20),
            };
            mSeekHideAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutSeek, holders);
            mSeekHideAnimator.setDuration(ANIMATION_DURATION);
            mSeekHideAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    mSeekHideAnimator = null;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mLayoutSeek.setVisibility(GONE);
                    mSeekHideAnimator = null;
                }
            });
            mSeekHideAnimator.start();
        }
    }

    private void cancelAnimator(Animator animator) {
        if (animator != null) {
            animator.cancel();
        }
    }
}
