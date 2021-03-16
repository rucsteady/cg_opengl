package a2;

public class Matrix4 {

	public float[] mat = new float[16];
	public float[] matr = new float[16];

	public Matrix4() {
		mat = new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
	}

	public Matrix4(Matrix4 copy) {

		for (int i = 0; i < copy.mat.length; i++) {
			this.mat[i] = copy.mat[i];
		}
	}

	public Matrix4(float near, float far) {

		this.mat[0] = near;
		this.mat[5] = near;
		this.mat[10] = (-far - near) / (far - near);
		this.mat[11] = (-2.0f * near * far) / (far - near);
		this.mat[14] = -1;
		this.mat[15] = 0;

	}

	public Matrix4 multiply(Matrix4 other) {

		float[] result = new float[16];

		result[0] = this.mat[0] * other.mat[0] + other.mat[4] * this.mat[1] + other.mat[8] * this.mat[2]
				+ other.mat[12] * this.mat[3];
		result[1] = other.mat[1] * this.mat[0] + other.mat[5] * this.mat[1] + other.mat[9] * this.mat[2]
				+ other.mat[13] * this.mat[3];
		result[2] = other.mat[2] * this.mat[0] + other.mat[6] * this.mat[1] + other.mat[10] * this.mat[2]
				+ other.mat[14] * this.mat[3];
		result[3] = other.mat[3] * this.mat[0] + other.mat[7] * this.mat[1] + other.mat[11] * this.mat[2]
				+ other.mat[15] * this.mat[3];

		result[4] = other.mat[0] * this.mat[4] + other.mat[4] * this.mat[5] + other.mat[8] * this.mat[6]
				+ other.mat[12] * this.mat[7];
		result[5] = other.mat[1] * this.mat[4] + other.mat[5] * this.mat[5] + other.mat[9] * this.mat[6]
				+ other.mat[13] * this.mat[7];
		result[6] = other.mat[2] * this.mat[4] + other.mat[6] * this.mat[5] + other.mat[10] * this.mat[6]
				+ other.mat[14] * this.mat[7];
		result[7] = other.mat[3] * this.mat[4] + other.mat[7] * this.mat[5] + other.mat[11] * this.mat[6]
				+ other.mat[15] * this.mat[7];

		result[8] = other.mat[0] * this.mat[8] + other.mat[4] * this.mat[9] + other.mat[8] * this.mat[10]
				+ other.mat[12] * this.mat[11];
		result[9] = other.mat[1] * this.mat[8] + other.mat[5] * this.mat[9] + other.mat[9] * this.mat[10]
				+ other.mat[13] * this.mat[11];
		result[10] = other.mat[2] * this.mat[8] + other.mat[6] * this.mat[9] + other.mat[10] * this.mat[10]
				+ other.mat[14] * this.mat[11];
		result[11] = other.mat[3] * this.mat[8] + other.mat[7] * this.mat[9] + other.mat[11] * this.mat[10]
				+ other.mat[15] * this.mat[11];

		result[12] = other.mat[0] * this.mat[12] + other.mat[4] * this.mat[13] + other.mat[8] * this.mat[14]
				+ other.mat[12] * this.mat[15];
		result[13] = other.mat[1] * this.mat[12] + other.mat[5] * this.mat[13] + other.mat[9] * this.mat[14]
				+ other.mat[13] * this.mat[15];
		result[14] = other.mat[2] * this.mat[12] + other.mat[6] * this.mat[13] + other.mat[10] * this.mat[14]
				+ other.mat[14] * this.mat[15];
		result[15] = other.mat[3] * this.mat[12] + other.mat[7] * this.mat[13] + other.mat[11] * this.mat[14]
				+ other.mat[15] * this.mat[15];

		mat = result;
		return this;

	}

	public Matrix4 translate(float x, float y, float z) {
		Matrix4 result = new Matrix4();

		result.mat[3] = x;
		result.mat[7] = y;
		result.mat[11] = z;

		multiply(result);
		return this;
	}

	public Matrix4 scale(float uniformFactor) {
		mat[0] *= uniformFactor;
		mat[1] *= uniformFactor;
		mat[2] *= uniformFactor;
		mat[3] *= uniformFactor;

		mat[4] *= uniformFactor;
		mat[5] *= uniformFactor;
		mat[6] *= uniformFactor;
		mat[7] *= uniformFactor;

		mat[8] *= uniformFactor;
		mat[9] *= uniformFactor;
		mat[10] *= uniformFactor;
		mat[11] *= uniformFactor;

		mat[12] *= uniformFactor;
		mat[13] *= uniformFactor;
		mat[14] *= uniformFactor;
		mat[15] *= uniformFactor;

		return this;
	}

	public Matrix4 scale(float sx, float sy, float sz) {

		Matrix4 result = new Matrix4();

		result.mat[0] = sx;
		result.mat[5] = sy;
		result.mat[10] = sz;

		multiply(result);
		return this;
	}

	public Matrix4 rotateX(float angle) {

		float cosAngle = (float) Math.cos(angle);
		float sinAngle = (float) Math.sin(angle);

		Matrix4 result = new Matrix4();

		result.mat[5] = cosAngle;
		result.mat[6] = -sinAngle;
		result.mat[9] = sinAngle;
		result.mat[10] = cosAngle;

		multiply(result);
		return this;
	}

	public Matrix4 rotateY(float angle) {

		float cosAngle = (float) Math.cos(angle);
		float sinAngle = (float) Math.sin(angle);

		Matrix4 result = new Matrix4();

		result.mat[0] = cosAngle;
		result.mat[2] = -sinAngle;
		result.mat[8] = sinAngle;
		result.mat[10] = cosAngle;

		multiply(result);
		return this;
	}

	public Matrix4 rotateZ(float angle) {

		float cosAngle = (float) Math.cos(angle);
		float sinAngle = (float) Math.sin(angle);

		Matrix4 result = new Matrix4();

		result.mat[0] = cosAngle;
		result.mat[1] = -sinAngle;
		result.mat[4] = sinAngle;
		result.mat[5] = cosAngle;

		multiply(result);
		return this;
	}

	public float[] getValuesAsArray() {

		this.matr[0] = this.mat[0];
		this.matr[1] = this.mat[4];
		this.matr[2] = this.mat[8];
		this.matr[3] = this.mat[12];

		this.matr[4] = this.mat[1];
		this.matr[5] = this.mat[5];
		this.matr[6] = this.mat[9];
		this.matr[7] = this.mat[13];

		this.matr[8] = this.mat[2];
		this.matr[9] = this.mat[6];
		this.matr[10] = this.mat[10];
		this.matr[11] = this.mat[14];

		this.matr[12] = this.mat[3];
		this.matr[13] = this.mat[7];
		this.matr[14] = this.mat[11];
		this.matr[15] = this.mat[15];

		return this.matr;
	}

}