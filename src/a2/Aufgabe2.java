package a2;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glUseProgram;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	private ShaderProgram shaderProgram;

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		glEnable(GL_CULL_FACE);

	}

	@Override
	public void update() {

	}

	@Override
	protected void render() {

	}

	@Override
	public void destroy() {
	}
}
