package com.loosu.soplayer.widget.videoview.controller.gesture;

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
    protected void showBtnPauseOrResume() {
        updateBtnPlay();
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mBtnPauseOrResume.getAlpha(), 1),
        };
        ObjectAnimator btnPauseOrResumeShowAnimator = ObjectAnimator.ofPropertyValuesHolder(mBtnPauseOrResume, holders);
        btnPauseOrResumeShowAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBtnPauseOrResume.setVisibility(VISIBLE);
            }
        });
        btnPauseOrResumeShowAnimator.start();
    }

    @Override
    protected void hideBtnPauseOrResume() {
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mBtnPauseOrResume.getAlpha(), 0),
        };
        ObjectAnimator btnPauseOrResumeHideAnimator = ObjectAnimator.ofPropertyValuesHolder(mBtnPauseOrResume, holders);
        btnPauseOrResumeHideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBtnPauseOrResume.setVisibility(GONE);
            }
        });
        btnPauseOrResumeHideAnimator.start();
    }

    @Override
    protected void showLayoutTop() {
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutTop.getAlpha(), 1),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mLayoutTop.getTranslationY(), 0),
        };
        ObjectAnimator layoutTopShowAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutTop, holders);
        layoutTopShowAnimator.start();
    }

    @Override
    protected void hideLayoutTop() {
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutTop.getAlpha(), 0),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mLayoutTop.getTranslationY(), -mLayoutTop.getHeight()),
        };
        ObjectAnimator layoutTopHideAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutTop, holders);
        layoutTopHideAnimator.start();
    }

    @Override
    protected void showLayoutBottom() {
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutBottom.getAlpha(), 1),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mLayoutBottom.getTranslationY(), 0),
        };
        ObjectAnimator layoutBottomShowAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutBottom, holders);
        layoutBottomShowAnimator.start();
    }

    @Override
    protected void hideLayoutBottom() {
        PropertyValuesHolder[] holders = {
                PropertyValuesHolder.ofFloat(View.ALPHA, mLayoutBottom.getAlpha(), 0),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mLayoutBottom.getTranslationY(), mLayoutBottom.getHeight()),
        };
        ObjectAnimator layoutBottomHideAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayoutBottom, holders);
        layoutBottomHideAnimator.start();
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
