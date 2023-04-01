package com.wahyu.evakuasibencana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PengungsianActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsian);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(DbContract.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(DbContract.TAG_ID);
                String name = jo.getString(DbContract.TAG_NAMA);
                //String alamat = jo.getString(DbContract.TAG_ALAMAT);
                //String kecamatan = jo.getString(DbContract.TAG_KECAMATAN);
                //String kelurahan = jo.getString(DbContract.TAG_KELURAHAN);
                //String latitude = jo.getString(DbContract.TAG_LATITUDE);
                //String longitude = jo.getString(DbContract.TAG_LONGITUDE);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(DbContract.TAG_ID,id);
                employees.put(DbContract.TAG_NAMA,name);
                //employees.put(DbContract.TAG_ALAMAT,alamat);
                //employees.put(DbContract.TAG_KECAMATAN,kecamatan);
                //employees.put(DbContract.TAG_KELURAHAN,kelurahan);
                //employees.put(DbContract.TAG_LATITUDE,latitude);
                //employees.put(DbContract.TAG_LONGITUDE,longitude);
                list.add(employees);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                PengungsianActivity.this, list, R.layout.list_item,
                new String[]{DbContract.TAG_ID,DbContract.TAG_NAMA},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PengungsianActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(DbContract.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilLokasiActivity.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(DbContract.TAG_ID).toString();
        intent.putExtra(DbContract.EMP_ID,empId);
        startActivity(intent);
    }

    public void TambahPosko(View view) {
        Intent intent = new Intent(PengungsianActivity.this, TambahdataActivity.class);
        startActivity(intent);
    }
}