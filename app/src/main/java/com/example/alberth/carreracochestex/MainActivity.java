package com.example.alberth.carreracochestex;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout LnLyDj;
    TextView tvTiempo, tvMensaje,tvPerdedor;
    Chronometer cronometro;


    private GLSurfaceView superficie;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       LnLyDj=(LinearLayout)findViewById(R.id.LnLyDJ);
       cronometro=(Chronometer)findViewById(R.id.ChTiempo);
       tvMensaje=(TextView)findViewById(R.id.TvMensaje);
        tvPerdedor=(TextView)findViewById(R.id.TvPerdiste);

        GLSurfaceView s=new Renderiza(this,cronometro,tvMensaje,tvPerdedor);
        LnLyDj.addView(s);

    }


}