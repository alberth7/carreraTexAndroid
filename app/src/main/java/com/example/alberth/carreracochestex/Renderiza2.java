package com.example.alberth.carreracochestex;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderiza2 implements Renderer {
	/* Textura */
	private Textura texturaCarretera;
	private Textura texturaCoche1;
	private Textura texturaCoche2;
	private Textura texturaCoche3;

	private float despCarreteraY;


	/* Contexto */
	private Context contexto;

	private RectanguloGrafico rectangulo;
	private RectanguloGrafico lineas;
	private RectanguloGrafico coche;

	private float despLineasY;
	private float despCoche1X;
	private float despCoche2X;
	private float despCoche2Y;
	private float despCoche3X;
	private float despCoche3Y;
	private SonidoSoundPool sonidoCar, explosion;

	private Rectangulo rectanguloCoche1, rectanguloCoche2,rectanguloCoche3;
	public float acelerometroX = 0;
	Random rand = new Random();

	int swColicion=0;
	int swSonidoCarrera=0;

	public Renderiza2(Context context){
		this.contexto=context;
		sonidoCar= new SonidoSoundPool(contexto,"scar2.ogg");
		explosion=new SonidoSoundPool(contexto,"explosion.ogg");

	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		rectangulo = new RectanguloGrafico(96, 0, 128, 480);
		lineas = new RectanguloGrafico(157, 0, 6, 35);
		coche = new RectanguloGrafico(112, 50, 30, 30);
		rectanguloCoche1 = new Rectangulo(112, 50, 30, 30);

		texturaCarretera = new Textura(gl, contexto, "MyCarretera.png");
		texturaCarretera.setVertices(0, 0, 320, 480);

		texturaCoche1 = new Textura(gl, contexto, "car.png");
		texturaCoche1.setVertices(112, 50, 25, 50);

		texturaCoche2 = new Textura(gl, contexto, "f2_orange.png");
		texturaCoche2.setVertices(112, 50, 35, 45);

		texturaCoche3 = new Textura(gl, contexto, "f1_yellow.png");
		texturaCoche3.setVertices(112, 50, 35, 45);

		despLineasY = 0;

		despCoche1X = 0;

		if (despCoche2X == 0) // 0 o 1
			despCoche2X = 0;
		else
			despCoche2X = 64;

		despCoche2Y = 480;
		despCoche3Y = 240;


		rectanguloCoche2 = new Rectangulo(112 + despCoche2X, 50 + despCoche2Y, 30, 30);
		rectanguloCoche3 = new Rectangulo(112 + despCoche3X, 50 + despCoche3Y, 30, 30);


		/* Habilita la textura */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);
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

	public void dibujaCoche1(GL10 gl) {
		if (acelerometroX > 0.5)
			despCoche1X = 0;
		else if(acelerometroX < -0.5 )
			despCoche1X = 64;
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(despCoche1X,0,0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		rectanguloCoche1.x=112+despCoche1X;

		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCoche1.getCodigoTextura());
		texturaCoche1.muestra(gl);
	}


	public void dibujaCoche2(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//coliciones
		rectanguloCoche2.x=112+despCoche2X;
		rectanguloCoche2.y=50+despCoche2Y;

		gl.glTranslatef(despCoche2X, despCoche2Y, 0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCoche2.getCodigoTextura());
		texturaCoche2.muestra(gl);
	}

	public void dibujaCoche3(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//coliciones
		rectanguloCoche3.x=112+despCoche3X;
		rectanguloCoche3.y=50+despCoche3Y;

		gl.glTranslatef(despCoche3X, despCoche3Y, 0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glBindTexture(GL10.GL_TEXTURE_2D,
				texturaCoche3.getCodigoTextura());
		texturaCoche3.muestra(gl);
	}


	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		dibujaCarretera(gl);
		dibujaCoche1(gl);
		dibujaCoche2(gl);
		dibujaCoche3(gl);

		if(swSonidoCarrera==0){
			swSonidoCarrera=1;
			sonidoCar.repeat();
		}

		if (!seSobreponen(rectanguloCoche1, rectanguloCoche2)) {

			if (!seSobreponen(rectanguloCoche1, rectanguloCoche3)) {
				despCarreteraY = despCarreteraY - 0.01f;
				if (despCarreteraY < -60)
					despCarreteraY = 0;
				despCoche2Y = despCoche2Y - 5;
				despCoche3Y = despCoche3Y - 5;
				if (despCoche2Y < -60) {
					if (rand.nextInt(2) == 0) { // 0 o 1
						despCoche2X = 0;
						despCoche3X = 64;
					} else {
						despCoche2X = 64;
						despCoche3X = 0;

					}
					despCoche2Y = 480;
				}
				if (despCoche3Y < -60) {
					despCoche3Y = 480;
				}
			}else{
				if (swColicion == 0) {
					swColicion=1;
					explosion.play();
					sonidoCar.stop();
				}
			}
		}else{
			if (swColicion == 0) {
				swColicion=1;
				explosion.play();
				sonidoCar.stop();
			}
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, 320, 0, 480);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public boolean seSobreponen(Rectangulo r1, Rectangulo r2) {
		return (r1.x < r2.x + r2.ancho && r1.x + r1.ancho >  r2.x  &&
				r1.y < r2.y + r2.alto && r1.y + r1.alto > r2.y);
	}
}
