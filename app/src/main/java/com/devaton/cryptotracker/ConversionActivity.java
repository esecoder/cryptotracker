package com.devaton.cryptotracker;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.devaton.cryptotracker.model.Card;
import com.devaton.cryptotracker.ui.typeface.TextStyler;

/**
 * Created by Ese Udom on 11/4/2017.
 */
public class ConversionActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private View shadowView;
    private ImageView mImageView;
    private TextView mTextView, mTextView1, mTextView2;
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;
    private Card card;
    private RadioButton mRadioButton, mRadioButton1;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        initComponents();
    }

    private void initComponents() {
        card = (Card) getIntent().getSerializableExtra("CardObject");
        mToolbar = (Toolbar) findViewById(R.id.converter_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shadowView = findViewById(R.id.shadow_view);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            shadowView.setVisibility(View.VISIBLE);
        }

        mImageView = (ImageView) findViewById(R.id.crypto_type_icon);
        mImageView.setImageResource(Utils.getCryptoIcon(card.getCryptoType()));

        mTextView = (TextView) findViewById(R.id.result_currency_sign);
        mTextView.setText(Utils.getSign(card.getCurrency()));

        mTextView1 = (TextView) findViewById(R.id.result);
        mTextView1.setText(card.getLastRate());

        mTextInputLayout = (TextInputLayout) findViewById(R.id.amount_wrapper);
        mTextInputLayout.setTypeface(TextStyler.setVarelaRoundFont(this));
        mEditText = (EditText) findViewById(R.id.amount);
        mEditText.setTypeface(TextStyler.setVarelaRoundFont(this), Typeface.BOLD);
        mEditText.setText(getResources().getString(R.string.one));

        mTextView2 = (TextView) findViewById(R.id.rate);
        mTextView2.setText(card.getLastRate());

        mRadioButton = (RadioButton) findViewById(R.id.currency_1);
        mRadioButton1 = (RadioButton) findViewById(R.id.currency_2);
        mRadioButton.setText(card.getCurrency());
        mRadioButton1.setText(card.getCryptoType());
        mRadioButton1.setChecked(true);
        mRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTextView.setText(Utils.getSign(card.getCryptoType()));
                    mTextView1.setText(getResources().getString(R.string.number_placeholder));
                }
            }
        });

        mRadioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTextView.setText(Utils.getSign(card.getCurrency()));
                    mTextView1.setText(getResources().getString(R.string.number_placeholder));
                }
            }
        });

        mButton = (Button) findViewById(R.id.convert);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEditText.getText().toString();
                if (!s.isEmpty()) {
                    double amt = Double.parseDouble(s);
                    if (mRadioButton1.isChecked()) {
                        mTextView1.setText(String.valueOf(amt * Double.parseDouble(card.getLastRate())));
                    } else if (mRadioButton.isChecked()) {
                        mTextView1.setText(String.valueOf(amt / Double.parseDouble(card.getLastRate())));
                    }
                }
            }
        });
    }
}
