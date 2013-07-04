package com.example.d2y;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
 
import org.json.JSONObject;
 
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
 
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
 
public class Map extends FragmentActivity {
    //搜索添加要的参数
    AutoCompleteTextView atvPlaces;
 
    DownloadTask placesDownloadTask;
    DownloadTask placeDetailsDownloadTask;
    ParserTask placesParserTask;
    ParserTask placeDetailsParserTask;
 
    public GoogleMap googleMap;
    //点击添加所需的参数
    public MarkerOptions markerOptions;
    LatLng latLng;
 
    final int PLACES=0;
    final int PLACES_DETAILS=1;
    
    //记录的是搜索获得的位置信息
    String search;
    //返回给前一个页面的数据
    String record;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);
 
        // Getting reference to the SupportMapFragment of the activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap from SupportMapFragment
        googleMap = fm.getMap();
        Log.v("nimei",""+googleMap.getMapType());
        googleMap.setOnMapClickListener(new OnMapClickListener() {
        	 
            @Override
            public void onMapClick(LatLng arg0) {
 
                // Getting the Latitude and Longitude of the touched location
                latLng = arg0;
 
                // Clears the previously touched position
                googleMap.clear();
 
                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
 
                // Creating a marker
                markerOptions = new MarkerOptions();
 
                // Setting the position for the marker
                markerOptions.position(latLng);
 
                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
 
                // Adding Marker on the touched location with address
               new ReverseGeocodingTask(getBaseContext()).execute(latLng);

            }
        });
        
        
        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @SuppressWarnings("deprecation")
			@Override
            public void onInfoWindowClick(final Marker marker) {
       //   以后给用户一个自己输入title的功能,现在不能更改
            	AlertDialog adRef = new AlertDialog.Builder(Map.this).create();
            	adRef.setMessage("是否确认添加地点？");
            	adRef.setIcon(android.R.drawable.btn_star);
            	adRef.setTitle("地图");
            	adRef.setButton("确定",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						  record = marker.getTitle();
						  Intent data = new Intent();
						  data.putExtra("position", record);
						  setResult(RESULT_OK,data);
						  finish();
					}
				});
               adRef.setButton2("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
               adRef.show();
            }
        });
        //-----------------------------------上面实现点击，下面实现搜索------ -----------------------------------------------
        
        // Getting a reference to the AutoCompleteTextView
        atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
        atvPlaces.setThreshold(1);
 
        // Adding textchange listener
        atvPlaces.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Creating a DownloadTask to download Google Places matching "s"
                placesDownloadTask = new DownloadTask(PLACES);
 
                // Getting url to the Google Places Autocomplete api
                String url = getAutoCompleteUrl(s.toString());
 
               // Start downloading Google Places
                // This causes to execute doInBackground() of DownloadTask class
                placesDownloadTask.execute(url);
 
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
 
        // Setting an item click listener for the AutoCompleteTextView dropdown list
        atvPlaces.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
            long id) {
 
                ListView lv = (ListView) arg0;
                SimpleAdapter adapter = (SimpleAdapter) arg0.getAdapter();
 
                HashMap<String, String> hm = (HashMap<String, String>) adapter.getItem(index);
 
                // Creating a DownloadTask to download Places details of the selected place
                placeDetailsDownloadTask = new DownloadTask(PLACES_DETAILS);
 
                // Getting url to the Google Places details api
                String url = getPlaceDetailsUrl(hm.get("reference"));
                search = hm.get("description");
                // Start downloading Google Place Details
                // This causes to execute doInBackground() of DownloadTask class
                placeDetailsDownloadTask.execute(url);
 
            }
        });
    }
 
    private String getAutoCompleteUrl(String place){
 
        // Obtain browser key from https://code.google.com/apis/console
        String key = "key=AIzaSyBGUNwSPxgif33vXEcLyq90skMnUyuN0GI";
        // place to be be searched
        String input = "input="+place;
 
        // place type to be searched
        String types = "types=geocode";
 
        // Sensor enabled
        String sensor = "sensor=false";
//        String language = "language=zh-CN";
        // Building the parameters to the web service
        String parameters = sensor+"&"+input+"&"+key;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
 
        return url;
    }
 
    private String getPlaceDetailsUrl(String ref){
 
        // Obtain browser key from https://code.google.com/apis/console
        String key = "key=AIzaSyBGUNwSPxgif33vXEcLyq90skMnUyuN0GI";
 
        // reference of place
        String reference = "reference="+ref;
 
        // Sensor enabled
        String sensor = "sensor=false";
//        String language ="language=zh-CN";
        // Building the parameters to the web service
        String parameters = reference+"&"+sensor+"&"+key;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/details/"+output+"?"+parameters;

        return url;
    }
 
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        Log.v("AAAAAa", data);
        return data;
    }
 
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
        private int downloadType=0;
 
        // Constructor
        public DownloadTask(int type){
            this.downloadType = type;
        }
 
        @Override
        protected String doInBackground(String... url) {
 
            // For storing data from web service
            String data = "";
 
            try{
                // Fetching the data from web service
            	data = downloadUrl(url[0]);
     
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            switch(downloadType){
            case PLACES:
                // Creating ParserTask for parsing Google Places
                placesParserTask = new ParserTask(PLACES);
 
                // Start parsing google places json data
                // This causes to execute doInBackground() of ParserTask class
                placesParserTask.execute(result);
 
                break;
 
            case PLACES_DETAILS :
                // Creating ParserTask for parsing Google Places
                placeDetailsParserTask = new ParserTask(PLACES_DETAILS);
 
                // Starting Parsing the JSON string
                // This causes to execute doInBackground() of ParserTask class
                placeDetailsParserTask.execute(result);
            }
        }
    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void,HashMap<String,String>>{
        Context mContext;
 
        public ReverseGeocodingTask(Context context){
            super();
            mContext = context;
        }
 
        // Finding address using reverse geocoding
        @Override
        protected HashMap<String,String>  doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;
            HashMap<String,String> map= new HashMap<String,String>();  
            List<Address> addresses = null;
            String addressText="";
            //代表经纬度
            String Ltitude="";
 
            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
 
            if(addresses != null && addresses.size() > 0 ){
                Address address = addresses.get(0);
                addressText = String.format("%s, %s, %s",
                address.getSubLocality() == null?"未知"  : address.getSubLocality() ,
                address.getLocality() == null?  "未知"  :  address.getLocality(),
                address.getCountryName());
                Ltitude = String.format("%s,%s","Latitude:"+
                String.valueOf(address.getLatitude()),"Longitude:"+
                String.valueOf(address.getLongitude()));
            }
            map.put("title", addressText);
            map.put("snippet", Ltitude);
            return map;
        }
 
        @Override
        protected void onPostExecute(HashMap<String,String> map) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(map.get("title"));
            markerOptions.snippet(map.get("snippet"));
            // Placing a marker on the touched position
            googleMap.addMarker(markerOptions);
 
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        int parserType = 0;
 
        public ParserTask(int type){
            this.parserType = type;
        }
 
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
 
            JSONObject jObject;
            List<HashMap<String, String>> list = null;
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                switch(parserType){
                case PLACES :
                    MapPlaceJSONParser placeJsonParser = new MapPlaceJSONParser();
                    // Getting the parsed data as a List construct
                    list = placeJsonParser.parse(jObject);
                    break;
                case PLACES_DETAILS :
                    MapPlaceDetailsJSONParser placeDetailsJsonParser = new MapPlaceDetailsJSONParser();
                    // Getting the parsed data as a List construct
                    list = placeDetailsJsonParser.parse(jObject);
                }
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return list;
        }
 
        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
 
            switch(parserType){
            case PLACES :
                String[] from = new String[] { "description"};
                int[] to = new int[] { android.R.id.text1 };
 
                // Creating a SimpleAdapter for the AutoCompleteTextView
                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);
 
                // Setting the adapter
                atvPlaces.setAdapter(adapter);
                break;
            case PLACES_DETAILS :
                HashMap<String, String> hm = result.get(0);
 
                // Getting latitude from the parsed data
                double latitude = Double.parseDouble(hm.get("lat"));
 
                // Getting longitude from the parsed data
                double longitude = Double.parseDouble(hm.get("lng"));
 
 
                LatLng point = new LatLng(latitude, longitude);
 
                CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(point);
                CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(5);
 
                // Showing the user input location in the Google Map
                googleMap.moveCamera(cameraPosition);
                googleMap.animateCamera(cameraZoom);
                
                MarkerOptions options = new MarkerOptions();
                options.position(point);
                options.title(search);
                options.snippet("Latitude:"+latitude+",Longitude:"+longitude);
 
                // Adding the marker in the Google Map
                googleMap.clear();
                googleMap.addMarker(options);
            }
        }
    }
}