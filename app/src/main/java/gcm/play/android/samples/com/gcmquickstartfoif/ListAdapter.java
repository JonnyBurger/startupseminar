package gcm.play.android.samples.com.gcmquickstartfoif;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

public class ListAdapter extends ArrayAdapter<JSONObject> {
    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public ListAdapter(Context context, int resource, List<JSONObject> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cell, null);
        }

        JSONObject p = getItem(position);

        TextView tv = (TextView) v.findViewById(R.id.label);
        TextView tv2 = (TextView) v.findViewById(R.id.sublabel);
        try {
            tv.setText(p.getString("symbol").toUpperCase());
            String type = p.getString("type");
            String sublabel = "";
            if (type.equals("above_price")) {
                sublabel += "Above $";
            }
            sublabel += p.getDouble("amount");
            if (p.has("fulfilled")) {
                sublabel += " - Happened " + DateUtils.getRelativeTimeSpanString(p.getLong("fulfilled"));

            }
            tv2.setText(sublabel);
        }
        catch (Exception e) {

        }

        return v;
    }
}
