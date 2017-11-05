package com.devaton.cryptotracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.devaton.cryptotracker.adapter.CardListAdapter;
import com.devaton.cryptotracker.model.Card;
import com.devaton.cryptotracker.model.ResultModel;
import com.devaton.cryptotracker.network.DataConnectionChecker;
import com.devaton.cryptotracker.receiver.RatesUpdateReceiver;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ese Udom on 11/2/2017.
 */
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static HomeActivity activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private View shadowView;
    private FloatingActionButton mFab;
    private TextView mTextView;
    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayout;
    private LinkedList<String> currList;
    private List<Card> cards = new LinkedList<>();
    private boolean started = false;
    private boolean stopped = false;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private String cryS;
    private String currS;

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_home);
        activity = this;
        initComponents();
        initList();
    }

    public static HomeActivity getInstance() {
        return activity;
    }

    private void initComponents() {
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        shadowView = findViewById(R.id.shadow_view);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            shadowView.setVisibility(View.VISIBLE);
        }

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                startActivityForResult(new Intent(HomeActivity.this, NewCardActivity.class), 2);
            }
        });

        mTextView = (TextView) findViewById(R.id.empty_list_text);
    }

    private void initList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.card_list);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.loading_screen);
        mLinearLayout = (LinearLayout) findViewById(R.id.empty_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadDataSet();
    }

    //Fetch card models
    private void loadDataSet() {
        new FetchCardsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        //dataSetConfig(Card.getAll());
    }

    //Initialize list with model from local database and update rates
    private void dataSetConfig(List<Card> cds) {
        // specify an adapter
        if (cds != null) {

            if (cds.isEmpty()) {
                mLinearLayout.setVisibility(View.VISIBLE);
                mTextView.setText(getResources().getString(R.string.no_cards));
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter = new CardListAdapter(this,
                        R.layout.fragment_card_list_item, cds);
                mRecyclerView.setAdapter(mAdapter);

                updateRates(cds);

                if (!started) {
                    startUpdate();
                    started = true;
                }

                for (Card c: cds)
                    cards.add(c);
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_fetching_cards), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //mAdapter.notifyDataSetChanged();
            loadDataSet();
            Log.d("onActivityResult", "In here");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!stopped) {
            cancelUpdate();
            stopped = true;
            started = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!stopped) {
            cancelUpdate();
            stopped = true;
            started = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelUpdate();
        stopped = true;
        started = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cards != null && !cards.isEmpty()) {
            if (!started) {
                startUpdate();
                started = true;
            }
        }
    }

    public void startUpdate() {
        // Retrieve a PendingIntent that will perform a broadcast
        Intent updateIntent = new Intent(this, RatesUpdateReceiver.class);
        updateIntent.putExtra("Cryptos", cryS)
                .putExtra("Currencies", currS);
        pendingIntent = PendingIntent.getBroadcast(this, 1, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Log.d(TAG, "Update Alarm started");
    }

    public void cancelUpdate() {
        Intent updateIntent = new Intent(this, RatesUpdateReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);

        Log.d(TAG, "Update Alarm cancelled");
    }

    //Connection checker
    private boolean connectionAvailable() {
        return new DataConnectionChecker(this).isConnected();
    }

    //Updating rates in the background
    private void updateRates(List<Card> cards) {
        if (connectionAvailable()) {
            List<String> cryptoList = new LinkedList<>();
            currList = new LinkedList<>();
            for (Card card : cards) {
                String s = card.getCryptoType();
                if (!cryptoList.contains(s)) {
                    cryptoList.add(s);
                }

                String s1 = card.getCurrency();
                if (!currList.contains(s1)) {
                    currList.add(s1);
                }
            }

            cryS = Arrays.toString(cryptoList.toArray());
            cryS = cryS.substring(0, cryS.lastIndexOf(']'));
            cryS = cryS.replace(" ", "");

            currS = Arrays.toString(currList.toArray());
            currS = currS.substring(0, currS.lastIndexOf(']'));
            currS = currS.replace(" ", "");

            new GetRatesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    "https://min-api.cryptocompare.com/data/pricemulti?fsyms=" + cryS + "&tsyms=" + currS);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void update(ResultModel result) {
        if (result != null) {
            for (Card card : cards) {
                if (card.getCryptoType().equals("BTC")) {
                    if (result.getBtc() != null) {
                        card.setLastRate(String.valueOf(Utils.getCurrRate(card.getCurrency(), result.getBtc(), null)));
                    }
                } else if (card.getCryptoType().equals("ETH")) {
                    if (result.getEth() != null) {
                        card.setLastRate(String.valueOf(Utils.getCurrRate(card.getCurrency(), null, result.getEth())));
                    }
                }
            }
        }
    }

    //Update Rate in the background with API
    public class GetRatesTask extends AsyncTask<String, Void, ResultModel> {

        private Response response;

        public GetRatesTask() {
        }

        @Override
        protected ResultModel doInBackground(String... urls) {
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

                ResultModel object = new Gson().fromJson(response.body().charStream(), ResultModel.class);

                response.close();
                //Log.d(TAG, responseBody.string());

                return object;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return null;
            }

            //Retrofit method
            //APIServiceInterface apiServiceInterface = APIUtils.getAPIServiceInterface();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ResultModel object) {
            super.onPostExecute(object);
            update(object);
        }
    }

    //Fetch card models from database
    private class FetchCardsTask extends AsyncTask<String, Void, List<Card>> {

        public FetchCardsTask() {
        }

        @Override
        protected List<Card> doInBackground(String... params) {
            return Card.getAll();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView.setVisibility(View.GONE);
            mLinearLayout.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Card> cards) {
            super.onPostExecute(cards);
            mRelativeLayout.setVisibility(View.GONE);
            dataSetConfig(cards);
        }
    }
}
