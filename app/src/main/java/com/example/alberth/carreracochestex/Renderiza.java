package com.example.alberth.carreracochestex;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

import java.util.Random;


public class Renderiza implements Renderer{

	/* Textura */
	private Textura texturaCarretera;
	private Textura texturaCoche1;
	private Textura texturaCoche2;
	
	/* Contexto */
	private Context contexto;
	
	private float despCarreteraY;
	
	private float despCoche2X;
	private float despCoche2Y;

	Random rando=new Random();


	public Renderiza(Context contexto) {
		this.contexto = contexto;	
	}	
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
	 
		texturaCarretera = new Textura(gl, contexto, "MyCarretera.png");
		texturaCarretera.setVertices(0, 0, 320, 480);
		
		texturaCoche1 = new Textura(gl, contexto, "car.png");
		texturaCoche1.setVertices(112, 50, 30, 50);
		
		texturaCoche2 = new Textura(gl, contexto, "f2_orange.png");
		texturaCoche2.setVertices(112, 50, 40, 50);
		
		despCarreteraY = 0;
		
		despCoche2X = 64;
		despCoche2Y = 480;
        
		/* Habilita la textura */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, 
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);
		
		/* Color de fondo */
		gl.glClearColor(0, 0, 0, 0);
	}
	
	public void dibujaCarretera(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0,despCarreteraY,0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCarretera.getCodigoTextura());
		texturaCarretera.muestra(gl);
	}
	
	public void dibujaCoche1(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCoche1.getCodigoTextura());
		texturaCoche1.muestra(gl);
	}
	
	public void dibujaCoche2(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(despCoche2X, despCoche2Y, 0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCoche2.getCodigoTextura());
		texturaCoche2.muestra(gl);
	}

	
	@Override
	public void onDrawFrame(GL10 gl) {

		/* Inicializa el buffer de color y de profundidad */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		dibujaCarretera(gl);
		
		dibujaCoche1(gl);
		
		dibujaCoche2(gl);


		despCarreteraY = despCarreteraY - 0.01f;
		if (despCarreteraY < -60)
			despCarreteraY = 0;



		despCoche2Y = despCoche2Y - 5;
		if (despCoche2Y < -60){
			despCoche2Y = 480;

			if(rando.nextInt(2)==0)
				despCoche2X=0;
			else
				despCoche2X=64;

		}

	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
	 
		/* Ventana de despliegue */
		gl.glViewport(0, 0, w, h);
	 
		/* Matriz de Proyecci�n */
		gl.glMatrixMode(GL10.GL_PROJECTION);
	 
		/* Inicializa la Matriz de Proyecci�n */
		gl.glLoadIdentity();
	 
		/* Proyecci�n paralela */
		GLU.gluOrtho2D(gl, 0, 320, 0, 480);
	 
		/* Matriz del Modelo-Vista */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	 
		/* Inicializa la Matriz del Modelo-Vista */
		gl.glLoadIdentity();
		
	}
}
