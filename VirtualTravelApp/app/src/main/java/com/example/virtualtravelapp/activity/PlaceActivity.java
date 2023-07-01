package com.example.virtualtravelapp.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.adapter.PlaceAdapter;
import com.example.virtualtravelapp.database.DBManager;
import com.example.virtualtravelapp.model.Place;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceActivity extends AppCompatActivity {
    @BindView(R.id.lvPlace)
    ListView lvPlace;
    PlaceAdapter adapter;
    DBManager db;
    ArrayList<Place> listPlace;
    String imString = "";
    Bitmap bitmap;
	static int i = 1;
	int id;

    private AlertDialog dialog;

    //search
    @BindView(R.id.etSearch) EditText etSearch;

    Button btnFilter, btnOk;

    RadioGroup radioGroup1, radioGroup2, radioGroup3;
    @BindView(R.id.tvEmpty) TextView tvEmpty;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getIntExtra("id_diadanh", 0);
        btnFilter = (Button) findViewById(R.id.btnFilter);


        db = new DBManager(this);
        textChange();
//        db.openDataBase();
        listPlace = db.getPlaceId(id);
        if (listPlace.size() == 0) {
            Toast.makeText(getApplicationContext(), "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            setAdapterListView(listPlace);
//	        ArrayList<Place> listPlace11 = db.getPlace();
//	        for (int i = 0; i < listPlace11.size(); i++) {
//                new LoadImage().execute(listPlace11.get(i).getImage());
//            }

        }

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_menu, null);
        builder.setView(dialogView);

        Button buttonInsideDialog = dialogView.findViewById(R.id.btnOk);
        radioGroup1 = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) dialogView.findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) dialogView.findViewById(R.id.radioGroup3);
        buttonInsideDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton1;
                String selectedValue1;
                RadioButton radioButton2;
                String selectedValue2;
                RadioButton radioButton3;
                String selectedValue3;
                if(radioGroup1.getCheckedRadioButtonId() != -1){
                    radioButton1 = dialogView.findViewById(radioGroup1.getCheckedRadioButtonId());
                    selectedValue1 = radioButton1.getText().toString();
                } else {
                    selectedValue1 = "0";
                }
                if(radioGroup2.getCheckedRadioButtonId() != -1) {
                    radioButton2 = dialogView.findViewById(radioGroup2.getCheckedRadioButtonId());
                    selectedValue2 = radioButton2.getText().toString();
                } else {
                    selectedValue2 = "0";
                }
                if(radioGroup3.getCheckedRadioButtonId() != -1) {
                    radioButton3 = dialogView.findViewById(radioGroup3.getCheckedRadioButtonId());
                    selectedValue3 = radioButton3.getText().toString();
                } else {
                    selectedValue3 = "0";
                }

                radioGroup1.clearCheck();
                radioGroup2.clearCheck();
                radioGroup3.clearCheck();

                deXuat(selectedValue1, selectedValue2, selectedValue3);

                //dialog.dismiss();;
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void deXuat(String selectedValue1, String selectedValue2, String selectedValue3) {
        int a = 0, b = 0, c = 0;
        if(selectedValue1.equals("0") & selectedValue2.equals("0") & selectedValue3.equals("0")){
            dialog.dismiss();
        } else if (!selectedValue1.equals("0") & selectedValue2.equals("0") & selectedValue3.equals("0")) {
            if(selectedValue1.equals("500k - 2,5tr")){
                a = 1;
            } else {
                a = 2;
            }
        } else if(selectedValue1.equals("0") & !selectedValue2.equals("0") & selectedValue3.equals("0")){
            if(selectedValue2.equals("Dưới 18t")){
                b = 3;
            } else {
                b = 2;
            }
        } else if(selectedValue1.equals("0") & selectedValue2.equals("0") & !selectedValue3.equals("0")){
            if(selectedValue3.equals("Nóng")) {
                c = 1;
            } else {
                c = 2;
            }
        } else if(!selectedValue1.equals("0") & !selectedValue2.equals("0") & !selectedValue3.equals("0")){
            if(selectedValue1.equals("500k - 2,5tr") & selectedValue2.equals("Dưới 18t") & selectedValue3.equals("Nóng")){
                a = 1; b = 3; c = 1;
            } else if (selectedValue1.equals("500k - 2,5tr") & selectedValue2.equals("Trên 18t") & selectedValue3.equals("Nóng")) {
                a = 1; b = 2; c = 1;
            } else if (selectedValue1.equals("500k - 2,5tr") & selectedValue2.equals("Dưới 18t") & selectedValue3.equals("Lạnh")) {
                a = 1; b = 3; c = 2;
            } else if (selectedValue1.equals("500k - 2,5tr") & selectedValue2.equals("Trên 18t") & selectedValue3.equals("Lạnh")) {
                a = 1; b = 2; c = 2;
            } else if (selectedValue1.equals("2,5tr - 4tr") & selectedValue2.equals("Dưới 18t") & selectedValue3.equals("Nóng")) {
                a = 2; b = 3; c = 1;
            } else if (selectedValue1.equals("2,5tr - 4tr") & selectedValue2.equals("Trên 18t") & selectedValue3.equals("Nóng")) {
                a = 2; b = 2; c = 1;
            } else if (selectedValue1.equals("1tr - 2,5tr") & selectedValue2.equals("Dưới 18t") & selectedValue3.equals("Lạnh")) {
                a = 2; b = 3; c = 2;
            } else {
                a = 2; b = 2; c = 2;
            }
        }
        listPlace = db.filterPlace(a, b, c, id);
        setAdapterListView(listPlace);
    }


    public void setAdapterListView(ArrayList<Place> list) {
        adapter = new PlaceAdapter(getApplicationContext(), list);
        lvPlace.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    //ham download image
    private class LoadImage extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                imString = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
	            publishProgress(imString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imString;
        }

	    @Override
	    protected void onProgressUpdate(String... values) {
		    super.onProgressUpdate(values);
			    Place place = new Place();
			    place.setId(i++);
			    String image = values[0];
			    place.setImage(image);
			    db.editPlace(place);

	    }

	    protected void onPostExecute(String imString) {
//            Log.d("imString", imString);
        }

        public String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        }
    }

    //----------------------------------------------------------searchh
    //--
    @OnClick(R.id.ibtSpeech)
    public void speech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_somthing));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);;
        }catch (ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if ((result.get(0).equals("xóa"))){
                        etSearch.setText("");
                    }else {
                        etSearch.setText(result.get(0));
                    }
                }
                break;
            }
        }
    }

    @OnClick(R.id.btClean)
    public void cleanText(){
        etSearch.setText("");
    }

    public void textChange(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etSearch.setSelection(etSearch.getText().length());
                listPlace = db.searchPlace(String.valueOf(s), id);
                if (listPlace.size() == 0){
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                }
                setAdapterListView(listPlace);
                //adapter.notifyDataSetChanged();
            }
        });
    }
}
