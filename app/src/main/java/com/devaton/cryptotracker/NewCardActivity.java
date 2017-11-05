package com.devaton.cryptotracker;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.devaton.cryptotracker.model.Card;
import com.devaton.cryptotracker.network.DataConnectionChecker;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ese Udom on 11/4/2017.
 */
public class NewCardActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private View shadowView;
    private RelativeLayout mRelativeLayout;
    private Spinner mSpinner, mSpinner1;
    private Button mButton;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        initComponents();
    }

    private void initComponents() {
        mToolbar = (Toolbar) findViewById(R.id.new_card_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shadowView = findViewById(R.id.shadow_view);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            shadowView.setVisibility(View.VISIBLE);
        }

        mRelativeLayout = (RelativeLayout) findViewById(R.id.loading_screen);

        mSpinner = (Spinner) findViewById(R.id.crypto_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cryptos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);//select_dialog_singlechoice
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);

        mSpinner1 = (Spinner) findViewById(R.id.currency);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);//select_dialog_singlechoice
        // Apply the adapter to the spinner
        mSpinner1.setAdapter(adapter1);

        mButton = (Button) findViewById(R.id.add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card = new Card((((String) mSpinner.getSelectedItem()).equals("Bitcoin")? "BTC" : "ETH"), "0.00",
                        (String) mSpinner1.getSelectedItem());
                if (!Utils.isDuplicate(card, NewCardActivity.this))
                    fetchRate(card);
                else
                    Toast.makeText(NewCardActivity.this, getResources().getString(R.string.card_duplicate), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchRate(Card card) {
        if (connectionAvailable()) {
            new GetRateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    "https://min-api.cryptocompare.com/data/price?fsym=" + card.getCryptoType() + "&tsyms=" + card.getCurrency());
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            this.card.save();
            setResult(RESULT_OK);
            finish();
        }
    }

    //Connection checker
    private boolean connectionAvailable() {
        return new DataConnectionChecker(this).isConnected();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
    }

    //Update Rate in the background with API
    private class GetRateTask extends AsyncTask<String, Void, Double> {

        private Response response;

        public GetRateTask() {
        }

        @Override
        protected Double doInBackground(String... urls) {
            // Okhttp method
            OkHttpClient client = new OkHttpClient();
            //connectionTimeOut(40, TimeUnit.SECONDS);

            Request request = new Request.Builder()
                    .url(urls[0])
                    .get()
                    .build();

            try {
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                JSONObject jsonObject = new JSONObject(response.body().string());
                double result = jsonObject.getDouble(card.getCurrency());

                response.close();
                //Log.d(TAG, responseBody.string());

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("NewCardActivity", e.getMessage());
                return null;
            }

            //Retrofit method
            //APIServiceInterface apiServiceInterface = APIUtils.getAPIServiceInterface();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRelativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);
            mRelativeLayout.setVisibility(View.GONE);
            if (result != null) {
                card.setLastRate(String.valueOf(result));
                card.save();
                setResult(RESULT_OK);
                finish();
            } else {
                card.save();
                setResult(RESULT_OK);
                finish();
            }
        }
    }

}
