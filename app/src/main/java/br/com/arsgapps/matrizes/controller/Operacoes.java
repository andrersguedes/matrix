package br.com.arsgapps.matrizes.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.arsgapps.matrizes.R;

public class Operacoes extends Activity {

	String[] elementoA, elementoB;
	float[][] matrizA, matrizB;
	int l, c;
	char op;
	LinearLayout layoutMatrizC, layoutMultiMatriz;
	double constante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		layoutMatrizC = (LinearLayout) findViewById(R.id.layoutMatrizC);
		layoutMultiMatriz = (LinearLayout) findViewById(R.id.layoutMultiMatriz);

		Intent soma = getIntent();
		Bundle params = soma.getExtras();
		elementoA = params.getStringArray("elementosA");
		elementoB = params.getStringArray("elementosB");
		constante = params.getDouble("constante");
		l = params.getInt("l");
		c = params.getInt("c");
		op = params.getChar("op");

		if (op == '1') {
			setTitle("Soma das matrizes");
			this.soma(elementoA, elementoB, l, c);
		} else if (op == '2') {
			setTitle("Subtração das matrizes");
			this.subtrai(elementoA, elementoB, l, c);
		} else if (op == '3') {
			setTitle("Multiplicação de constante");
			this.multiplicaConstante(elementoA, constante, l, c);
		}
	}

	public void soma(String[] elementosA, String[] elementosB, int l, int c) {

		float[][] matrizC = new float[l][c];

		int controle = 0;

		for (int i = 0; i < l; i++) {

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setPadding(10, 2, 10, 2);

			for (int j = 0; j < c; j++) {
				matrizC[i][j] = (Float.valueOf(elementosA[controle]) + Float
						.valueOf(elementosB[controle]));

				controle++;
				float res = matrizC[i][j];

				TextView elemento = new TextView(this);
				elemento.setText(String.format("%.2f", res).concat("  "));
				elemento.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				layout.addView(elemento);
			}
			layoutMatrizC.addView(layout);
		}
	}

	public void subtrai(String[] elementosA, String[] elementosB, int l, int c) {

		float[][] matrizC = new float[l][c];

		int controle = 0;

		for (int i = 0; i < l; i++) {

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setPadding(10, 2, 10, 2);

			for (int j = 0; j < c; j++) {
				matrizC[i][j] = (Float.valueOf(elementosA[controle]) - Float
						.valueOf(elementosB[controle]));

				controle++;
				float res = matrizC[i][j];

				TextView elemento = new TextView(this);
				elemento.setText(String.format("%.2f", res).concat("  "));
				elemento.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				layout.addView(elemento);
			}
			layoutMatrizC.addView(layout);
		}
	}

	public void multiplicaConstante(String[] elementoA, double constante,
			int l, int c) {
		float[][] matrizC = new float[l][c];

		int a = 0;

		for (int i = 0; i < l; i++) {

			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setPadding(10, 2, 10, 2);

			for (int j = 0; j < c; j++) {
				matrizC[i][j] = (float) (constante * Float
						.parseFloat(elementoA[a]));

				float res = matrizC[i][j];

				TextView elemento = new TextView(this);
				elemento.setText(String.format("%.2f", res).concat("  "));
				elemento.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				layout.addView(elemento);
				a++;
			}
			layoutMatrizC.addView(layout);
		}
	}
}
