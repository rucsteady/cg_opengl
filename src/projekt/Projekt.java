package projekt;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.lwjgl.glfw.GLFW;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

public class Projekt extends AbstractOpenGLBase {

	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgramTex;

	// Input Stuff from CodingAP
	// https://www.youtube.com/channel/UClt_fTxah4FScqLECvCcneA
	// Use keyboard left, right, up, down for moving
	// Use mouse left, right for zoom in out

	private int vaoOne; // wario
	private int vaoTwo; // mario
	private int vaoThree; // luigi
	private Texture texOne; // wario
	private Texture texTwo; // mario
	private Texture texThree; // luigi
	private int uniformWorldMatrix;
	private int uniformLocMyProjMatrix;
	private Matrix4 projMatrix = new Matrix4(1.0f, 100f);
	private float rotatori;
	private float rotatori_due;

	public static void main(String[] args) {
		new Projekt().start("CG Projekt", 700, 700);

	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("projekt");
		shaderProgramTex = new ShaderProgram("new");

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		setBackgroundColor(0.2f, 0.3f, 0.4f);

		// One
		vaoOne = glGenVertexArrays();
		glBindVertexArray(vaoOne);

		// VBOs
		int vboOnePosi = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboOnePosi);

		float onePosi[] = { 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f,
				0.0f, 0.0f, -0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, -0.5f,
				0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, -0.5f, 0.0f, 0.0f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f,
				0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, -0.5f, 0.0f, 0.0f, 0.0f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, -0.5f,
				0.0f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5f, };

		glBufferData(GL_ARRAY_BUFFER, onePosi, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboOneNormal = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboOneNormal);

		float oneNormal[] = { 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f, -1.0f,
				1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f,
				-1.0f, 0.0f, -1.0f, -1.0f, 0.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 0.0f, -1.0f, -1.0f,
				0.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, };

		glBufferData(GL_ARRAY_BUFFER, oneNormal, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		texOne = new Texture("wario.png", 5);

		int vboOneTexture = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboOneTexture);

		float oneTexture[] = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, };

		glBufferData(GL_ARRAY_BUFFER, oneTexture, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);
		glUseProgram(0);

		// two

		vaoTwo = glGenVertexArrays();
		glBindVertexArray(vaoTwo);

		int vboTwoPosi = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTwoPosi);

		float twoPosi[] = { -1.0f, 0.0f, 1.0f, 1.0f, 2.0f, 1.0f, -1.0f, 2.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
				1.0f, 2.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 2.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 2.0f,
				-1.0f, 1.0f, 2.0f, 1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 2.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f,
				-1.0f, 2.0f, -1.0f, 1.0f, 2.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 2.0f, 1.0f, -1.0f, 0.0f, -1.0f, -1.0f,
				0.0f, -1.0f, -1.0f, 2.0f, 1.0f, -1.0f, 2.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f,
				-1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 2.0f, 1.0f, 1.0f, 2.0f, -1.0f,
				-1.0f, 2.0f, 1.0f, -1.0f, 2.0f, 1.0f, 1.0f, 2.0f, -1.0f, -1.0f, 2.0f, -1.0f, };

		glBufferData(GL_ARRAY_BUFFER, twoPosi, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboTwoNormal = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTwoNormal);

		float twoNormal[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, };

		glBufferData(GL_ARRAY_BUFFER, twoNormal, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		int vboTwoTexture = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTwoTexture);

		float twoTexture[] = { 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, };

		texTwo = new Texture("mario.png", 5);

		glBufferData(GL_ARRAY_BUFFER, twoTexture, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		// three

		vaoThree = glGenVertexArrays();
		glBindVertexArray(vaoThree);

		int vboThreePosi = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboThreePosi);

		float threePosi[] = { -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
				-1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f, -1.0f,
				-1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f };

		glBufferData(GL_ARRAY_BUFFER, threePosi, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		int vboThreeNormal = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboThreeNormal);

		float threeNormal[] = { 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f,
				-1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, };

		glBufferData(GL_ARRAY_BUFFER, threeNormal, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		texThree = new Texture("luigi.png", 5);

		int vboThreeTexture = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboThreeTexture);

		float threeTexture[] = { 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.5f, 0.0f,
				0.0f, 1.0f, 1.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 1.0f, };

		glBufferData(GL_ARRAY_BUFFER, threeTexture, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

	}

	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		rotatori += 0.01f;
		rotatori_due += 0.05f;

		// This Input Guide from CodingAp
		// https://www.youtube.com/channel/UClt_fTxah4FScqLECvCcneA
		// New LWJGL 3 3D Game Tutorial - #13 First Person Camera

		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
			projMatrix.translate(0, 0, 0.1f);
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT))
			projMatrix.translate(0, 0, -0.1f);
		System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT))
			projMatrix.translate(-0.1f, 0, 0);
		if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
			projMatrix.translate(0.1f, 0, 0);
		if (Input.isKeyDown(GLFW.GLFW_KEY_UP))
			projMatrix.translate(0, 0.1f, 0);
		if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
			projMatrix.translate(0, -0.1f, 0);
		glUseProgram(0);
	}

	@Override
	protected void render() {

		glUseProgram(shaderProgram.getId());
//		glUseProgram(shaderProgramTex.getId());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
		uniformWorldMatrix = glGetUniformLocation(shaderProgram.getId(), "worldMatrix");
		glUniformMatrix4fv(uniformLocMyProjMatrix, false, projMatrix.getValuesAsArray());

		// OBJECT: One wario

		Matrix4 matrix1 = new Matrix4();
		matrix1.translate(1.5f, 3.0f, -5.0f).scale(2.0f, 2.0f, 1.0f).rotateX(-rotatori).rotateY(rotatori_due);
		glUniformMatrix4fv(uniformWorldMatrix, false, matrix1.getValuesAsArray());
		glBindVertexArray(vaoOne);
		glBindTexture(GL_TEXTURE_2D, texOne.getId());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glDrawArrays(GL_TRIANGLES, 0, 24);

		// OBJECT: two mario

		Matrix4 matrix2 = new Matrix4();
		matrix2.scale(3.0f, 2.0f, 1.0f).translate(-1.0f, -2.0f, -4.0f).rotateZ(rotatori_due);
		glUniformMatrix4fv(uniformWorldMatrix, false, matrix2.getValuesAsArray());
		glBindVertexArray(vaoTwo);
		glBindTexture(GL_TEXTURE_2D, texTwo.getId());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glDrawArrays(GL_TRIANGLES, 0, 36);

		// OBJECT: Three luigi

		Matrix4 matrix3 = new Matrix4();
		// Mond um Erde
		// Luigi dreht sich um Warios lokales koord.
		matrix3.multiply(matrix1).scale(0.1f).rotateZ(rotatori).rotateY(-rotatori).translate(1.0f, 1.0f, -0.5f);
		glUniformMatrix4fv(uniformWorldMatrix, false, matrix3.getValuesAsArray());
		glBindVertexArray(vaoThree);
		glBindTexture(GL_TEXTURE_2D, texThree.getId());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glDrawArrays(GL_TRIANGLES, 0, 36);

	};

	@Override
	public void destroy() {
	}
}