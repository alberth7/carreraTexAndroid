package com.example.alberth.carreracochestex;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;


public class Renderiza extends GLSurfaceView implements GLSurfaceView.Renderer{

	/* Textura */
	private Textura texturaCarretera;
	private Textura texturaCoche1;
	private Textura texturaCoche2;
	private Textura texturaCoche3;


	/* Contexto */
	private Context contexto;
	
	private float despCarreteraY;

	private float desplCoche1x;

	private float despCoche2X;
	private float despCoche2Y;

	private float despCoche3X;
	private float despCoche3Y;

	// coliciones
	private Rectangulo rectanguloCoche1; //coliciones
	private Rectangulo rectanguloCoche2; //coliciones
	private Rectangulo rectanguloCoche3; //coliciones

	//sonidos
	private SonidoSoundPool sonidoCar, explosion;

	//cotroles

	private int swColExplo =0;
	private  int swCarrera=0;

	Random rando=new Random();


	public Renderiza(Context contexto) {
		super(contexto);
		this.contexto = contexto;
		sonidoCar= new SonidoSoundPool(contexto,"scar2.ogg");
		explosion=new SonidoSoundPool(contexto,"explosion.ogg");
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


	}	
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
	 
		texturaCarretera = new Textura(gl, contexto, "MyCarretera.png");
		texturaCarretera.setVertices(0, 0, 320, 480);
		
		texturaCoche1 = new Textura(gl, contexto, "f3_white.png");
		texturaCoche1.setVertices(112, 50, 40, 50);
		
		texturaCoche2 = new Textura(gl, contexto, "f2_orange.png");
		texturaCoche2.setVertices(112, 50, 40, 50);

		texturaCoche3 = new Textura(gl, contexto, "f1_yellow.png");
		texturaCoche3.setVertices(112, 50, 40, 50);


		despCarreteraY = 0;

		desplCoche1x=0;

		despCoche2X = 64;
		despCoche2Y = 480;

		despCoche3X = 64;
		despCoche3Y = 240;

		//coliciones
		rectanguloCoche1=new Rectangulo(112,50,40,50);
		rectanguloCoche2=new Rectangulo(112+ despCoche2X,50+despCoche2Y,40,50);
		rectanguloCoche3=new Rectangulo(112+ despCoche3X,50+despCoche3Y,40,50);



		/* Habilita la textura */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
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
		gl.glTranslatef(desplCoche1x,0,0);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		rectanguloCoche1.x=112+desplCoche1x;

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

		/* Inicializa el buffer de color y de profundidad */
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		dibujaCarretera(gl);

		dibujaCoche1(gl);

		dibujaCoche2(gl);
		dibujaCoche3(gl);

		if(swCarrera==0){
			sonidoCar.repeat();
			swCarrera=1;
		}
		if (!seSobreponen(rectanguloCoche1, rectanguloCoche2)) {
			if (!seSobreponen(rectanguloCoche1, rectanguloCoche3)) {
				swColExplo = 0;


				despCarreteraY = despCarreteraY - 0.01f;
				if (despCarreteraY < -60)
					despCarreteraY = 0;

				//velocidad
				despCoche2Y = despCoche2Y - 5;
				despCoche3Y = despCoche3Y - 5;

				//coche 2
				if (despCoche2Y < -60) {
					despCoche2Y = 480;

					if (rando.nextInt(2) == 0) {
						despCoche2X = 0;
						despCoche3X = 64;

						//desplCoche1x=64;
					} else {
						despCoche2X = 64;
						despCoche3X = 0;

						//desplCoche1x=0;
					}

				}
				//coche 3
				if (despCoche3Y < -60) {
					despCoche3Y = 480;
				}


			} else {
				swCarrera = 1;
				if (swColExplo == 0) {
					swColExplo = 1;
					finDeJuegoSonido();
				}
			}

		}else {
			swCarrera = 1;
			if (swColExplo == 0) {
				swColExplo = 1;
				finDeJuegoSonido();
			}
		}
	}

public void movimientoCoches(float despCoche2X, float despCoche2Y){

}

	private void finDeJuegoSonido() {
		explosion.play();
		sonidoCar.stop();
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

	@Override
	public boolean onTouchEvent(MotionEvent e){
		if(swColExplo==1){
			Toast.makeText(this.getContext(), "PERDISTE..", Toast.LENGTH_LONG).show();

		}
		if(e.getAction()==MotionEvent.ACTION_UP){
			//explosion.play();
			//Toast toast1 = Toast.makeText(this.getContext(), "touch ok", Toast.LENGTH_SHORT);
			//toast1.show();
			if(desplCoche1x==0)
				desplCoche1x=64;
			else
				desplCoche1x=0;
		}


		return true;
	}

	public boolean seSobreponen(Rectangulo r1, Rectangulo r2){
		return (r1.x < r2.x + r2.ancho && r2.x < r1.x + r1.ancho &&
				r1.y < r2.y + r2.alto && r2.y < r1.y + r1.alto);
	}



}
