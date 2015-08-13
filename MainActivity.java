package com.example.chase.imageupload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Context getcontext;
    public Adapter cAdapter;
    public ArrayList<imgData> imgList;
    public imgData imgdata;
    public int queryActionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getcontext = this;

        setUpDrawables();
    }

    public void createListView(){
        imgList = new ArrayList<imgData>();
        imgList.add(imgdata);
        imgList.add(imgdata);
        imgList.add(imgdata);
        imgList.add(imgdata);
        imgList.add(imgdata);
        imgList.add(imgdata);
        cAdapter = new CustomAdapter(this,imgList);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter((ListAdapter) cAdapter);
    }

    public void setUpDrawables(){
        imgdata = new imgData();
        imgdata.drawables = new ArrayList<Drawable>(imgdata.imgs.size());
        System.out.println(imgdata.imgs.size());
        System.out.println(imgdata.drawables.size());
        for(int i=0;i<imgdata.imgs.size();i++){
            queryImg(imgdata.imgs.get(i), i);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void queryImg(String url, final int pos){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                int reqWidth = 256;
                int reqHeight = 256;

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                // First decode with inJustDecodeBounds=true to check dimensions
                Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length, options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length, options);

                Drawable image = new BitmapDrawable(bitmap);

                imgdata.drawables.add(image);

                queryActionCount++;

                if(queryActionCount == imgdata.imgs.size()){
                    createListView();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


                queryActionCount++;

                if(queryActionCount == imgdata.imgs.size()){
                    createListView();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
