package gcm.play.android.samples.com.gcmquickstartfoif;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CreateNewActivity extends AppCompatActivity {
    private Context _context;
    public String ticker = "AAPL";
    public float amount = 100.0f;
    public String type = "above_price";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout symbolPicker = (RelativeLayout) findViewById(R.id.symbolPicker);
        symbolPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                final EditText input = new EditText(_context);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
                builder.setView(input);
                builder.setTitle("Pick symbol");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ticker = input.getText().toString();
                        TextView viewfromthe6 = (TextView) findViewById(R.id.selectedTicker);
                        viewfromthe6.setText(ticker);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        RelativeLayout pricePicker = (RelativeLayout) findViewById(R.id.amountPicker);
        pricePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                final EditText input = new EditText(_context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setTitle("Pick amount");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            amount = Float.parseFloat(input.getText().toString());
                            TextView view = (TextView) findViewById(R.id.selectedAmount);
                            view.setText("$" + input.getText().toString());
                        } catch (Exception e) {

                        }
                    }
                });
                builder.show();
            }
        });


        RelativeLayout typePicker = (RelativeLayout) findViewById(R.id.typePicker);
        typePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                final String[] types = new String[]{"Above Price", "Below Price"};
                builder.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tv = (TextView) findViewById(R.id.selectedType);
                        String _type = types[which];
                        if (_type.equals("Above Price")) {
                            type = "above_price";
                            tv.setText("Above price");
                        }
                        else if (_type.equals("Below Price")) {
                            type = "below_price";
                            tv.setText("Below price");
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });


        View.OnClickListener buttonListener = new View.OnClickListener() {
            boolean clicked = false;
            int numClicks = 0;

            @Override
            public void onClick(View v) {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("send_to", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("motherfuckingtoken", ":("));
                    jsonObj.put("symbol", ticker);
                    jsonObj.put("type", type);
                    jsonObj.put("amount", amount);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "http://name-55690.onmodulus.net/api/watch/add", jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent_info = new Intent(CreateNewActivity.this,MainActivity.class);
                            startActivity(intent_info);
                            System.out.println("oh shit");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("fuck");
                        }
                    });
                    jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1));
                    Volley.newRequestQueue(_context).add(jsObjRequest);
                }
                catch (Exception e) {
                    // fuck
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(buttonListener);
    }

}
