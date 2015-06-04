package com.example.jose.utplqr.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose.utplqr.AlertDialogFragment;
import com.example.jose.utplqr.R;
import com.example.jose.utplqr.data.ElementData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


public class DisplayData extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String IMAGE_URI = "IMAGE_URI";

    private ProgressBar mProgressBar;
    private String mQRContents;

    private String mClase;
    private String mElemento;

    private TextView mTituloView;
    private TextView mAutorView;
    private TextView mCreacionView;
    private TextView mUbicacionView;
    private TextView mTecnicaView;
    private TextView mRepresentaView;
    private ImageView mImageView;

    private ElementData mElementData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        setTitle("UTPL");

        //Image Loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();

        ImageLoader.getInstance().init(config);

        //Layout elements

        mTituloView = (TextView) findViewById(R.id.titulo);
        mAutorView = (TextView) findViewById(R.id.autor);
        mCreacionView = (TextView) findViewById(R.id.creacion);
        mUbicacionView = (TextView) findViewById(R.id.ubicacion);
        mTecnicaView = (TextView) findViewById(R.id.tecnica);
        mRepresentaView = (TextView) findViewById(R.id.representa);
        mImageView = (ImageView) findViewById(R.id.imageFromURI);



        //Assing progressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        //Get QR contents from previous activity
        Intent intent = getIntent();
        mQRContents = intent.getStringExtra(MainActivity.INDIVIDUAL_NUMBER);

        final String[] splitString = mQRContents.split("#");


        if (splitString.length == 7) {
            /** Test Data display ***/
            setCustomData(splitString[0], splitString[1], splitString[2], splitString[3], splitString[4], splitString[5], splitString[6]);
        } else {
            mClase = splitString[0];
            mElemento = splitString[1];

            String jsonUrl = "https://api.forecast.io/forecast/161fc4a073257857b499d246069eb4b3/37.8267,-122.423";
            getContents(jsonUrl);
        }

        //Image View Button
        ImageView imageView = (ImageView) findViewById(R.id.imageFromURI);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayData.this, DisplayFullImage.class);
                intent.putExtra(IMAGE_URI, splitString[1]);
                startActivity(intent);
            }
        });



    }

    /** Action Bar **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_activity_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***********************/

    public void getContents(String jsonUrl) {


        if (isNetworkAvailable()) {
            //Let the user know data is being loaded
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(jsonUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    //Alert user about error
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        //Diplay collected data on logcat
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mElementData = parseData(jsonData, mClase, mElemento);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
            alertUserAboutError();
        }
    }

    public void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    public ElementData parseData(String jsonData, String clase, String elemento) throws JSONException{
        //TODO Change json labels with the real data

        JSONObject jsonObject = new JSONObject(jsonData);

        JSONObject claseObj = jsonObject.getJSONObject(clase);
        JSONArray elementosArray = claseObj.getJSONArray("data");
        JSONObject elementoObj = elementosArray.getJSONObject(Integer.parseInt(elemento));

        ElementData elementData = new ElementData();

        elementData.setTitulo(elementoObj.getString("summary"));
        elementData.setImagenURI(elementoObj.getString("icon"));
        elementData.setAutor(elementoObj.getString("temperatureMin"));
        elementData.setCreacion(elementoObj.getString("temperatureMax"));
        elementData.setUbicacion(elementoObj.getString("humidity"));
        elementData.setTecnica(elementoObj.getString("pressure"));
        elementData.setRepresenta(elementoObj.getString("ozone"));

        return elementData;
    }

    public void updateDisplay() {
        ElementData elementData = mElementData;

        displayImage(elementData.getImagenURI(), mImageView);

        mTituloView.setText(elementData.getTitulo());
        mAutorView.setText(elementData.getAutor());
        mCreacionView.setText(elementData.getCreacion());
        mUbicacionView.setText(elementData.getUbicacion());
        mTecnicaView.setText(elementData.getTecnica());
        mRepresentaView.setText(elementData.getRepresenta());


    }

    public void setCustomData(String titulo, String imagen, String autor, String creacion, String ubicacion, String tecnica, String representa) {

        //Display image
        displayImage(imagen, mImageView);

        mTituloView.setText(titulo);
        mAutorView.setText(autor);
        mCreacionView.setText(creacion);
        mUbicacionView.setText(ubicacion);
        mTecnicaView.setText(tecnica);
        mRepresentaView.setText(representa);
    }

    public void displayImage(String imageUri, ImageView imageView) {
        final ProgressBar imageProgressBar = (ProgressBar) findViewById(R.id.imageProgressBar);

        ImageLoader imageLoader = ImageLoader.getInstance();


        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnFail(getResources().getDrawable(R.drawable.image_missing))
                .build();


        //download and display image from url
        //imageLoader.displayImage(imageUri, imageView, options);

        imageLoader.displayImage(imageUri, imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageProgressBar.setVisibility(View.GONE);
            }
        });



    }

}
