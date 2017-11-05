package com.devaton.cryptotracker.ui.typeface;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * Created by Ese Udom on 11/15/2016.
 */
public class VarelaRound extends TextView {

    private static final String TAG = "VarelaRound";
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public VarelaRound(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VarelaRound(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public VarelaRound(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VarelaRound(Context context) {
        super(context);
        init();
    }

    private static Typeface get(Context context, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), assetPath);
                    cache.put(assetPath, typeface);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get font " + assetPath + " because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

    private void init() {
        Typeface typeface = get(getContext(), "fonts/Varela-Round-regular.ttf");
        setTypeface(typeface);
    }
}
