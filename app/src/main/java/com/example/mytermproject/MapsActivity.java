package com.example.mytermproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private EditText editText;
    public int id;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        editText = (EditText) findViewById(R.id.editText);
        id = intent.getIntExtra("ID",0);
        button=(Button)findViewById(R.id.button);
        dbHelper =new DBHelper(MapsActivity.this,1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this, Locale.KOREA);
        MarkerOptions mOptions = new MarkerOptions();
        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                mOptions.position(new LatLng(latitude, longitude));
                googleMap.addMarker(mOptions);
            }
        });
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str=editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(str, 10);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if(addressList.size()==0){
                    Toast.makeText(v.getContext(),"잘못된 주소가 입력되었습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                dbHelper.EditPosition(id,latitude,longitude,editText.getText().toString());
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mOptions.title("search result");
                mOptions.snippet(address);
                mOptions.position(point);
                mMap.addMarker(mOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,20));
                finish();
            }
        });

        LatLng point = new LatLng(Double.parseDouble(dbHelper.GetPositionla(id)),Double.parseDouble(dbHelper.GetPositionlo(id)));
        mOptions.title(dbHelper.GetPlace(id));
        mOptions.position(point);
        mMap.addMarker(mOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,20));
    }
}


