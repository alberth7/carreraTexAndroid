package com.example.alberth.carreracochestex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class RectanguloGrafico {

	float vertices[] = new float[8];
	FloatBuffer bufVertices;

	public RectanguloGrafico(float x1, float y1, float ancho, float alto) {
		float x2 = x1 + ancho;
		float y2 = y1 + alto;
		
		vertices[0] = x1; vertices[1] = y1;
		vertices[2] = x2; vertices[3] = y1;
		vertices[4] = x2; vertices[5] = y2;
		vertices[6] = x1; vertices[7] = y2;
		
		ByteBuffer bufByte = ByteBuffer.allocateDirect(vertices.length * 4);
		bufByte.order(ByteOrder.nativeOrder());
		bufVertices = bufByte.asFloatBuffer();
		bufVertices.put(vertices).rewind();
	}
	
	public void dibuja(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, bufVertices);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
