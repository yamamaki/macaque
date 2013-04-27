package com.example.d2y;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapdemo extends FragmentActivity {
	private GoogleMap mMap;
	private GoogleMapOptions options = new GoogleMapOptions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapdemo);        
//      setUpMapIfNeeded();
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
        	.rotateGesturesEnabled(true)
        	.tiltGesturesEnabled(false);
        mMap.addMarker(new MarkerOptions()
        	.position(new LatLng(-33.796923, 150.922433))
        	.title("我是赵翔辰")
        	.snippet("Population: 4,137,400")
//        	.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        	.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
        	.draggable(true));
        mMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				double lat = round(arg0.latitude, 6, BigDecimal.ROUND_HALF_UP);
				double lng = round(arg0.longitude, 6, BigDecimal.ROUND_HALF_UP);
				LatLng latlng = new LatLng(lat, lng);
				Marker marker = mMap.addMarker(new MarkerOptions().position(latlng));
//	        		.title(latlng.latitude+" "+latlng.longitude)
//	        		.snippet("..."));
			}
			
		});
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg) {
				// TODO Auto-generated method stub
				LatLng pos = arg.getPosition();
				double lat = round(pos.latitude, 6, BigDecimal.ROUND_HALF_UP);
				double lng = round(pos.longitude, 6, BigDecimal.ROUND_HALF_UP);
				arg.setTitle(lat+" "+lng);
				Geocoder geocoder = new Geocoder(mapdemo.this, Locale.getDefault());
				try {
					List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);

					if (addresses.size() > 0) {
					   String strZipcode = addresses.get(0).getPostalCode();

					   //if 1st provider does not have data, loop through other providers to find it.
					   int count = 0;
					   while (strZipcode == null && count < addresses.size()) {
					      strZipcode = addresses.get(count).getLocality();
					      arg.setSnippet(strZipcode);
					      
					      arg.setVisible(true);
					      arg.setDraggable(true);
					      arg.setSnippet(strZipcode);
					      count++;
					   }
					}
				} catch (IOException e) {
					Log.e("TAG", "impossible to connect to Geocoder",e);
				}
			
				return false;
			}
		});
        mMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMarkerDragEnd(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub
				System.out.println("it is draggable");
			}
		});
    }
    private void setUpMapIfNeeded() {
    	if (mMap == null) {
    		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
    	}
    	if (mMap != null) {
    		
    	}
    }
    
    public static double round(double value, int scale, int roundingMode) {
    	BigDecimal bd = new BigDecimal(value);
    	bd = bd.setScale(scale, roundingMode);
    	double d = bd.doubleValue();
    	bd = null;
    	return d;
    }
}