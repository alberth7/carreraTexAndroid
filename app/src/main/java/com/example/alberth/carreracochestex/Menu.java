package com.example.alberth.carreracochestex;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    TextView titulo;
    Typeface habes;

    Button bntTouch,btnAcelerometro,btnCreditos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String fuente="fuentes/GreatVibes-Regular.otf";

        this.habes= Typeface.createFromAsset(getAssets(),fuente);
        bntTouch=(Button) findViewById(R.id.BtnTouch);
        btnCreditos=(Button) findViewById(R.id.BtnCreditos);
        btnAcelerometro=(Button) findViewById(R.id.BtnAcelerometro);
        titulo=(TextView)findViewById(R.id.TxtTitulo);
        titulo.setTypeface(habes);



        bntTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnAcelerometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,Acelerometro.class);
                startActivity(intent);
            }
        });

        btnCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,Credito.class);
                startActivity(intent);
            }
        });


    }
}
