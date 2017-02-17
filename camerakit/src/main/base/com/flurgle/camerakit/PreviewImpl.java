package com.flurgle.camerakit;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

public abstract class PreviewImpl {

    interface Callback {
        void onSurfaceChanged();
    }

    private Callback mCallback;

    private int mWidth;
    private int mHeight;

    private int mTrueWidth;
    private int mTrueHeight;

    void setCallback(Callback callback) {
        mCallback = callback;
    }

    abstract Surface getSurface();

    abstract View getView();

    abstract Class getOutputClass();

    abstract void setDisplayOrientation(int displayOrientation);

    abstract boolean isReady();

    protected void dispatchSurfaceChanged() {
        mCallback.onSurfaceChanged();
    }

    SurfaceHolder getSurfaceHolder() {
        return null;
    }

    SurfaceTexture getSurfaceTexture() {
        return null;
    }

    void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;

        // Refresh true preview size to adjust scaling
        setTruePreviewSize(mTrueWidth, mTrueHeight);
    }

    int getWidth() {
        return mWidth;
    }

    int getHeight() {
        return mHeight;
    }

    void setTruePreviewSize(int width, int height) {
        this.mTrueWidth = width;
        this.mTrueHeight = height;

        if (width != 0 && height != 0) {
            AspectRatio aspectRatio = AspectRatio.of(width, height);
            int targetHeight = (int) (getView().getWidth() * aspectRatio.toFloat());
            float scaleY;
            if (getView().getHeight() > 0) {
                scaleY = (float) targetHeight / (float) getView().getHeight();
            } else {
                scaleY = 1;
            }

            if (scaleY > 1) {
                getView().setScaleY(scaleY);
            } else {
                getView().setScaleX(1 / scaleY);
            }
        }
    }

    int getTrueWidth() {
        return mTrueWidth;
    }

    int getTrueHeight() {
        return mTrueHeight;
    }

}