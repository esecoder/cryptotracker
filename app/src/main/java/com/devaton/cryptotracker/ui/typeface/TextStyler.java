package com.devaton.cryptotracker.ui.typeface;


import android.content.Context;
import android.graphics.Typeface;

public class TextStyler {

    public TextStyler() {
    }

    public static Typeface setVarelaRoundFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Varela-Round-regular.ttf");
    }

    public static Typeface setVarelaRoundBoldFont(Context context) {
        return Typeface.create(Typeface.createFromAsset(context.getAssets(), "fonts/Varela-Round-regular.ttf"), Typeface.BOLD);
    }

    public static Typeface setLobsterRegularFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Lobster-Regular.ttf");
    }
}
