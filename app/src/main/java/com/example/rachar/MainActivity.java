package com.example.rachar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    EditText edtValor,edtQuantidade;
    TextView txtResultado;
    FloatingActionButton shareBtn,btnTTS;
    View myView;

    TextToSpeech textToSpeech;

    String Resultado;
    String message = getResources().getString(R.string.message);
    String compute = getResources().getString(R.string.compute);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValor = findViewById(R.id.edtValor);
        edtQuantidade = findViewById(R.id.edtQuantidade);

        txtResultado =findViewById(R.id.txtResultado);

        shareBtn = findViewById(R.id.btnShare);
        btnTTS = findViewById(R.id.btnTTS);

        textToSpeech = new TextToSpeech(getApplicationContext()
                , new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        btnTTS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

             String s = Resultado;
             int speech = textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = Resultado;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
        edtQuantidade.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Calcular(myView);
                return false;
            }
        });
        edtValor.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Calcular(myView);
                return false;
            }
        });
    }

    public void Calcular (View view){
        if(edtValor.getText().toString().trim().length() > 0 && edtQuantidade.getText().toString().trim().length() > 0){
            double valor = Double.parseDouble(edtValor.getText().toString());
            double quant = Double.parseDouble(edtQuantidade.getText().toString());
            if(quant >0){
                double result = valor/quant;
                DecimalFormat decimal = new DecimalFormat("0.00");
                String valorFormatado = decimal.format(result);

                Resultado = valorFormatado + " "+ message;
                txtResultado.setText("R$: "+ valorFormatado);

                shareBtn.setVisibility(View.VISIBLE);
                btnTTS.setVisibility(View.VISIBLE);
            }else{
                Resultado = compute;
                txtResultado.setText(Resultado);

                shareBtn.setVisibility(View.VISIBLE);
                btnTTS.setVisibility(View.VISIBLE);
            }
        }else{
            txtResultado.setText("R$: 0,00");
            shareBtn.setVisibility(View.INVISIBLE);
            btnTTS.setVisibility(View.INVISIBLE);
        }
    }


}