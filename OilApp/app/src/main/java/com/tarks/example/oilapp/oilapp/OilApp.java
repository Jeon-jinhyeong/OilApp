package com.tarks.example.oilapp.oilapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class OilApp extends Activity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    //GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
    //static final LatLng SEOUL = new LatLng(37.527089, 127.028480);
    //private static final String TAG = "@@@";
    //private GoogleApiClient mGoogleApiClient;
    //private LocationRequest mLocationRequest;
    //private static final int REQUEST_CODE_LOCATION = 2;
    static GoogleMap googleMap;
    private Document doc = null;
    private Node node1 = null;
    private Node node2 = null;
    private Node node3 = null;
    private GetTransCoordThread getCoordthread;
    private GetTransCoordThread_1 getCoordthread_1;
    //private InputStream input = null;
    String lati;
    String longi;
    String xml;
    static String x_code = null;
    static String y_code = null;
    static String x_code_1 = null;
    static String y_code_1 = null;
    static String OS_NM = null;
    static String GIS_X_COOR = null;
    static String GIS_Y_COOR = null;
    StringBuilder sb;
    String juyoo = null;
    String order = null;
    String around = null;

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_oil_app);

        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();*/

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button = (Button) findViewById(R.id.button2);
        ButtonClick buttonClick = new ButtonClick();
        button.setOnClickListener(buttonClick);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String around2 = adapterView.getItemAtPosition(i).toString();
                if (around2 == "휘발유") {
                    around = "B027";
                } else if (around2 == "경유"){
                    around = "D047";
                } else if (around2 == "고급휘발유"){
                    around = "B034";
                } else if (around2 == "실내등유"){
                    around = "C004";
                } else if (around2 == "자동차부탄"){
                    around = "K015";
                }
                System.out.println(around2 + " " + around);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() == "1Km") {
                    around = "1000";
                } else if (adapterView.getItemAtPosition(i).toString() == "2Km"){
                    around = "2000";
                } else if (adapterView.getItemAtPosition(i).toString() == "3Km"){
                    around = "3000";
                } else if (adapterView.getItemAtPosition(i).toString() == "4Km"){
                    around = "4000";
                } else if (adapterView.getItemAtPosition(i).toString() == "5Km"){
                    around = "5000";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() == "거리순") {
                    order = "2";
                } else if (adapterView.getItemAtPosition(i).toString() == "가격순"){
                    order = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onMapClick(LatLng latLng) {

        googleMap.clear();

        lati = Double.toString(latLng.latitude);
        longi = Double.toString(latLng.longitude);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).snippet("lat:" + lati + "long:" + longi).title("test"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    class ButtonClick implements View.OnClickListener {
        public void onClick (View v) {

            getCoordthread = new GetTransCoordThread(false, longi, lati, "WGS84", "KTM");
            getCoordthread.start();

            try{
                getCoordthread.join();
            }catch (Exception e) {

            }

            x_code = getCoordthread.getResult_1();
            y_code = getCoordthread.getResult_2();

            System.out.println(x_code + " " + y_code);

            aroundAll aroundAll = new aroundAll();
            aroundAll.start();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder;
                try {
                    documentBuilder = factory.newDocumentBuilder();
                    doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element element = doc.getDocumentElement(); // import 할떄 DOM것을  을 써야한다.

                //원하는 태그의 데이터를 추출
                NodeList nodeList1 = element.getElementsByTagName("OS_NM");
                NodeList nodeList2 = element.getElementsByTagName("GIS_X_COOR");
                NodeList nodeList3 = element.getElementsByTagName("GIS_Y_COOR");
                //System.out.println(nodeList1.getLength());
                //System.out.println(nodeList2.getLength());
                //System.out.println(nodeList3.getLength());
                //node = nodeList.item(0);
                //System.out.println(node.getTextContent());
                //System.out.println(nodeList.getLength());
                //System.out.println(nodeList.item(0));

                for (int i = 0; i < nodeList1.getLength(); i++) {
                    node1 = nodeList1.item(i);
                    OS_NM = node1.getTextContent();
                    node2 = nodeList2.item(i);
                    GIS_X_COOR = node2.getTextContent();
                    node3 = nodeList3.item(i);
                    GIS_Y_COOR = node3.getTextContent();

                    //System.out.println(OS_NM);
                    //System.out.println(GIS_X_COOR);
                    //System.out.println(GIS_Y_COOR);
                    //googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(y_code_1), Double.parseDouble(x_code_1))).snippet("lat: " + y_code_1 + "long: " + x_code_1).title(OS_NM));
                    if(GIS_X_COOR != null) {
                        //getaroundMarkerItem(GIS_X_COOR, GIS_Y_COOR, OS_NM);

                        getCoordthread_1 = new GetTransCoordThread_1(false, GIS_X_COOR, GIS_Y_COOR, "KTM", "WGS84");
                        getCoordthread_1.start();

                        try{
                            getCoordthread_1.join();
                        }catch (Exception e) {

                        }

                        x_code_1 = getCoordthread_1.getResult_1();
                        y_code_1 = getCoordthread_1.getResult_2();

                        //System.out.println(x_code_1 + " " + y_code_1);
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(y_code_1), Double.parseDouble(x_code_1))).snippet("lat: " + y_code_1 + "long: " + x_code_1).title(OS_NM));
                    }
                }
            }catch (Exception e) {
                //Log.e("Exception", e.getMessage());
            }
        }
    }

    class aroundAll extends Thread {

        @Override
        public void run() {
            if (x_code != null) {
                sb = new StringBuilder();
                try {
                    URL url = new URL("http://www.opinet.co.kr/api/aroundAll.do?code=F061160929&x=" + x_code + "&y=" + y_code + "&radius=" + around + "&sort=" + order + "&prodcd=" + juyoo + "&out=xml");
                    System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            InputStream is = conn.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            while (true) {
                                String line = br.readLine();
                                //System.out.println(line);
                                if (line == null)
                                    break;
                                sb.append(line).append('\n');
                            }
                            br.close();
                            conn.disconnect();
                        }
                    }
                } catch (Exception e) {
                    //Log.e("Exception", e.getMessage());
                }
                //System.out.println(sb.toString());
                xml = sb.toString();
                //System.out.println(xml);
            }
        }
    }

    /*private void getaroundMarkerItem(String x, String y, String z) {
        ArrayList<MarkerItem> aroundList = new ArrayList();

        aroundList.add(new MarkerItem(x, y, z));

        for (MarkerItem markerItem : aroundList) {
            addMarker(markerItem);
            Log.d("log : ", markerItem + "");

        }
    }*/

    /*private Marker addMarker(MarkerItem markerItem) {

        getCoordthread_1 = new GetTransCoordThread_1(false, markerItem.getLat(), markerItem.getLon(), "KTM", "WGS84");
        getCoordthread_1.start();

        System.out.println(x_code_1 + " " + y_code_1);

        LatLng position = new LatLng(Double.parseDouble(x_code_1), Double.parseDouble(y_code_1));
        String price = markerItem.getPrice();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(price);
        markerOptions.position(position);

        return googleMap.addMarker(markerOptions);
    }*/

    /*@SuppressLint("LongLogTag")
    public static void TransCoordThreadResponse(String x, String y) {    //대기정보 가져온 결과값
        Log.d("TransCoordThreadResponse", "Call");
        if (x.equals("NaN") || y.equals("NaN")) {
            Log.d("if", "if");
        } else {
            x_code = x;
            y_code = y;
        }
    }

    @SuppressLint("LongLogTag")
    public static void TransCoordThreadResponse_1(String x, String y) {    //대기정보 가져온 결과값
        Log.d("TransCoordThreadResponse_1", "Call");
        if (x.equals("NaN") || y.equals("NaN")) {
            Log.d("if", "if");
        } else {
            x_code_1 = x;
            y_code_1 = y;
            System.out.println(x_code_1 + " " + y_code_1);
        }
    }*/

    /*
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(1500);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CheckPermission();
        }

        startLocationUpdates();
    }

    private void CheckPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(OilApp.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(OilApp.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ActivityCompat.requestPermissions(OilApp.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(OilApp.this,
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }

        hasWriteContactsPermission = ContextCompat.checkSelfPermission(OilApp.this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(OilApp.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(OilApp.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION);

                return;
            }
            ActivityCompat.requestPermissions(OilApp.this,
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }
        ;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        googleMap.clear();
        LatLng CURRENT_LOCATION = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(CURRENT_LOCATION).snippet("Lat:"+location.getLatitude()+"Lng:"+location.getLongitude()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, 15));
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            Toast.makeText(this,"Location Unavialable",Toast.LENGTH_LONG).show();
        }


    }*/
}