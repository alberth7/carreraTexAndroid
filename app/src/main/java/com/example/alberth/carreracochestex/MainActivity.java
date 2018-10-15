package com.example.alberth.carreracochestex;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView superficie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		/* Ventana sin título */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* Establece las banderas de la ventana de esta Actividad */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/* Se crea el objeto GLSurfaceView */
        superficie = new GLSurfaceView(this);

		/* Se crea el objeto Renderiza */
        Renderiza renderiza = new Renderiza(this);

		/* GLSurfaceView <- Renderiza : Inicia el renderizado */
        superficie.setRenderer(renderiza);

		/*
		 * Activity <- GLSurfaceView  : Coloca la Vista de la Superficie del
		 * OpenGL como un Contexto de ésta Actividad.
		 */
        setContentView(superficie);
        // setContentView(R.layout.activity_main);
    }

}