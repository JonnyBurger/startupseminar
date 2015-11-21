package gcm.play.android.samples.com.gcmquickstart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import org.w3c.dom.Text;

public class CreateNewActivity extends AppCompatActivity {
    private Context _context;
    public String ticker = "AAPL";
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

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ticker = input.getText().toString();
                        TextView viewfromthe6 = (TextView) findViewById(R.id.selectedTicker);
                        viewfromthe6.setText(ticker);
                    }
                });
                builder.show();
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
                    jsonObj.put("type", "above_price");
                    jsonObj.put("amount", 110);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "http://7ce87ca1.ngrok.com/api/watch/add", jsonObj, new Response.Listener<JSONObject>() {
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
