package com.example.alberth.carreracochestex;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Clase Renderiza (OpenGL 1.x)
 * 
 * @author Jhonny Felipez
 * @version 1.0 01/01/2015
 * 
 */
public class Renderiza2 implements Renderer {
	private RectanguloGrafico rectangulo;
	private RectanguloGrafico lineas;
	private RectanguloGrafico coche;
	private float despLineasY;
	private float despCoche1X;
	private float despCoche2X;
	private float despCoche2Y;
	private Rectangulo rectanguloCoche1, rectanguloCoche2;
	public float acelerometroX = 0; 
	Random rand = new Random();

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		rectangulo = new RectanguloGrafico(96, 0, 128, 480);
		lineas = new RectanguloGrafico(157, 0, 6, 35);
		coche = new RectanguloGrafico(112, 50, 30, 30);
		rectanguloCoche1 = new Rectangulo(112, 50, 30, 30);
		
		despLineasY = 0;
		
		despCoche1X = 0;
		
		if (despCoche2X == 0) // 0 o 1
			despCoche2X = 0;
		else
			despCoche2X = 64;
		despCoche2Y = 480;
		
		rectanguloCoche2 = new Rectangulo(112 + despCoche2X, 50 + despCoche2Y, 30, 30);
		
		gl.glClearColor(0, 1, 1, 0);
	}

	public void dibujaCarretera(GL10 gl) {
		gl.glLoadIdentity();
		gl.glColor4f(127f / 255, 127f / 255, 127f / 255, 0);
		rectangulo.dibuja(gl);
	}

	public void dibujaLineas(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(0, despLineasY, 0);
		gl.glColor4f(1, 1, 1, 0);
		lineas.dibuja(gl);
		for (int i = 1; i <= 8; i++) {
			gl.glTranslatef(0, 60, 0);
			lineas.dibuja(gl);
		}
	}
	
	public void dibujaCoche1(GL10 gl) {
		if (acelerometroX > 0.5)
			despCoche1X = 0;
		else if(acelerometroX < -0.5 )
			despCoche1X = 64;
		
		gl.glLoadIdentity();
		gl.glColor4f(1, 0, 0, 0);
		rectanguloCoche1.x = 112 + despCoche1X;
		gl.glTranslatef(despCoche1X, 0, 0);
		coche.dibuja(gl);
	}
	
	public void dibujaCoche2(GL10 gl) {
		gl.glLoadIdentity();
		gl.glColor4f(1, 1, 0, 0);
		rectanguloCoche2.x = 112 + despCoche2X;
		rectanguloCoche2.y = 50 + despCoche2Y;
		gl.glTranslatef(despCoche2X, despCoche2Y, 0);
		coche.dibuja(gl);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		dibujaCarretera(gl);
		dibujaLineas(gl);
		dibujaCoche1(gl);
		dibujaCoche2(gl);

		if (!seSobreponen(rectanguloCoche1, rectanguloCoche2)) {
			despLineasY = despLineasY - 10;
			if (despLineasY < -60)
				despLineasY = 0;
			despCoche2Y = despCoche2Y - 5;
			if (despCoche2Y < -60) {
				if (rand.nextInt(2) == 0) // 0 o 1
					despCoche2X = 0;
				else
					despCoche2X = 64;
				despCoche2Y = 480;
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
