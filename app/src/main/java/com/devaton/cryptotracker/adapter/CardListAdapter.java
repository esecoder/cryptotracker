package com.devaton.cryptotracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.devaton.cryptotracker.ConversionActivity;
import com.devaton.cryptotracker.R;
import com.devaton.cryptotracker.Utils;
import com.devaton.cryptotracker.model.Card;
import com.devaton.cryptotracker.network.DataConnectionChecker;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ese Udom on 11/4/2017.
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardHolder> {

    private final List<Card> cards;
    private Context context;
    private int itemResource;
    private int position;

    public CardListAdapter(Context context, int itemResource, List<Card> cards) {
        this.cards = cards;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new CardHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        Card card = this.cards.get(position);
        this.position = position;

        holder.bindCard(card);
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }


    //Holder class
    public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Card card;
        private Context context;

        private ImageView cryptoTypeIcon, itemDelete;
        private TextView currencySign, rate;
        private Spinner spinner;

        public CardHolder(final Context context, View itemView) {
            super(itemView);

            this.context = context;

            //Initialize UI components
            this.cryptoTypeIcon = (ImageView) itemView.findViewById(R.id.crypto_type_icon);
            this.itemDelete = (ImageView) itemView.findViewById(R.id.item_delete);
            this.currencySign = (TextView) itemView.findViewById(R.id.currency_sign);
            this.rate = (TextView) itemView.findViewById(R.id.rate);
            this.spinner = (Spinner) itemView.findViewById(R.id.currency);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.currencies, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);//select_dialog_singlechoice
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!Utils.isDuplicate(card, context))
                        updateRecordAndRate(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card.deleteSingleRecord(card.getCurrency(), card.getCryptoType());

                    int pos = getAdapterPosition();
                    cards.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, cards.size());
                }
            });

            itemView.setOnClickListener(this);
        }

        private void updateRecordAndRate(int position) {
            String c = (String) spinner.getSelectedItem();
            new Card(card.getCryptoType(), rate.getText().toString(), c).save();

            if (connectionAvailable()) {
                new GetRateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        "https://min-api.cryptocompare.com/data/price?fsym=" + card.getCryptoType() + "&tsyms=" + c);
            }
        }

        //Connection checker
        private boolean connectionAvailable() {
            return new DataConnectionChecker(context).isConnected();
        }

        //Bind each item
        private void bindCard(Card card) {
            this.card = card;

            String s = card.getCryptoType();
            if (s != null) {
                this.cryptoTypeIcon.setImageResource(Utils.getCryptoIcon(s));
            } else {
                this.cryptoTypeIcon.setImageResource(R.drawable.bitcoin_icon);
            }

            s = card.getCurrency();
            if (s != null) {
                this.currencySign.setText(Utils.getSign(s));
            } else {
                this.currencySign.setText(context.getResources().getString(R.string.naira_sign));
            }

            s = card.getLastRate();
            if (s != null) {
                this.rate.setText(s);
            } else {
                this.rate.setText(context.getResources().getString(R.string.number_placeholder));
            }

            s = card.getCurrency();
            if (s != null) {
                spinner.setSelection(Arrays.asList(context.getResources().getStringArray(R.array.currencies)).indexOf(s));
            } else {
                spinner.setSelection(0);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ConversionActivity.class);
            intent.putExtra("CardObject", card);
            context.startActivity(intent);
        }

        private void update(Double result) {
            if (result != null) {
                rate.setText(String.valueOf(result));
            }
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
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    double result = jsonObject.getDouble(card.getCurrency());

                    response.close();
                    //Log.d(TAG, responseBody.string());

                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("CardListAdapter", e.getMessage());
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
            protected void onPostExecute(Double result) {
                super.onPostExecute(result);
                update(result);
            }
        }
    }
}
