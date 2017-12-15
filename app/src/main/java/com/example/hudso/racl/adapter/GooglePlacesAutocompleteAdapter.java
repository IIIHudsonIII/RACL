package com.example.hudso.racl.adapter;

import android.content.Context;
import android.support.annotation.StringDef;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author Hudson Henrique Lopes
 * @since 02/11/2017
 */

public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {

    private static final String LOG_TAG = "GPlacesAutocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyAHEa5D8mD6CebPPZkSiA5WA_mFOk53qxU";

    private ArrayList<String> resultList;

    public static final String CITY = "(cities)";
    public static final String ADDRESS = "geocode";

    @StringDef({CITY, ADDRESS})
    public @interface Types {
    }

    public String TYPE;
    private AutoCompleteTextView parentTextView;

    public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, @Types String TYPE, AutoCompleteTextView parentTextView) {
        super(context, textViewResourceId);
        this.TYPE = TYPE;
        this.parentTextView = parentTextView;
    }

    public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, @Types String TYPE) {
        super(context, textViewResourceId);
        this.TYPE = TYPE;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 5) {
                    // Retrieve the autocomplete results.
                    resultList = GooglePlacesAutocompleteAdapter.this.autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Search options to list on field, by Google API.
     * More API parameters: http://codetheory.in/google-place-api-autocomplete-service-in-android-application/
     * @param input
     * @return
     */
    public ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:br");
            sb.append("&language=br");
            sb.append("&types=").append(TYPE);
            if (parentTextView != null) {
                String complement = this.parentTextView.getText().toString();
                int limit = complement.indexOf(",");
                if (limit > -1) {
                    complement = complement.substring(0, limit - 1);
                }
                limit = complement.indexOf("-");
                if (limit > -1) {
                    complement = complement.substring(0, limit - 1);
                }
                input = complement + " " + input;
            }
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "RACL.LOG - Error processing Places API URL_DEFAULT", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "RACL.LOG - Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                if (TYPE == CITY) {
                    resultList.add(predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(0).get("value") + " - " +
                            predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(1).get("value"));
                } else {
                    JSONObject city = predsJsonArray.getJSONObject(i).getJSONObject("structured_formatting");
                    resultList.add(city.getString("main_text"));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "RACL.LOG - Cannot process JSON results", e);
        }

        return resultList;
    }
}
