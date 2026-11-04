package br.edu.fatecgarca.matrizes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MultiMatrizes extends Activity implements OnClickListener {

	char op;
	boolean opcao = true;
	int l, c, quant, mA, mB;
	Button btnAdicionarElementosMatriz, btnCalcularMatrizMatriz;
	float[][] matrizA, matrizB;
	String[] elementosA, elementosB;
	EditText edtMultiA, edtMultiB;
	LinearLayout linearMultiA, linearMultiB, layoutMultiMatriz;
	TextView txtQuantMatriz;
	double constante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_matrizes);

		mostrarDialog();

		Intent soma = getIntent();
		if (soma != null) {
			Bundle params = soma.getExtras();
			if (params != null) {
				l = params.getInt("l");
				c = params.getInt("c");
				op = params.getChar("op");
			}
		}

		quant = l * c;

		btnAdicionarElementosMatriz = (Button) findViewById(R.id.btnAdicionarElementoMulti);
		btnCalcularMatrizMatriz = (Button) findViewById(R.id.btnCalcularMatrizMulti);
		btnAdicionarElementosMatriz.setOnClickListener(this);
		btnCalcularMatrizMatriz.setOnClickListener(this);

		edtMultiA = (EditText) findViewById(R.id.edtMatrizMultiA);
		edtMultiB = (EditText) findViewById(R.id.edtMatrizMultiB);
		txtQuantMatriz = (TextView) findViewById(R.id.txtQuantElementosMatriz);
		txtQuantMatriz.setText(String.valueOf(quant));

		layoutMultiMatriz = (LinearLayout) findViewById(R.id.layoutMultiMatriz);
		linearMultiA = (LinearLayout) findViewById(R.id.layoutMatrizMultiA);
		linearMultiB = (LinearLayout) findViewById(R.id.layoutMatrizMultiB);
	}

	@SuppressWarnings("deprecation")
	protected void mostrarDialog() {
		AlertDialog alertDialog1 = new AlertDialog.Builder(MultiMatrizes.this)
				.create();
		alertDialog1.setTitle("Informação");
		alertDialog1
				.setMessage("Insira os elementos separados por vírgula. \n\nPor exemplo: 2.0,3,5.1,7");
		alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alertDialog1.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnAdicionarElementoMulti) {

			matrizA = new float[l][c];
			matrizB = new float[c][l];

			elementosA = edtMultiA.getText().toString().split(",");
			elementosB = edtMultiB.getText().toString().split(",");

			if (elementosA.length != quant) {
				Toast.makeText(
						this,
						"Quantidade de elementos da matriz A não corresponde com a ordem da matriz!",
						Toast.LENGTH_SHORT).show();
			} else if (elementosB.length != quant) {
				Toast.makeText(
						this,
						"Quantidade de elementos da matriz B não corresponde com a ordem da matriz!",
						Toast.LENGTH_SHORT).show();
			} else {
				btnCalcularMatrizMatriz.setEnabled(true);

				int b = 0, a = 0;

				if (l > c) {
					opcao = false;
					for (int i = 0; i < matrizA.length; i++) {

						LinearLayout layout = new LinearLayout(this);
						layout.setOrientation(LinearLayout.HORIZONTAL);
						layout.setPadding(10, 2, 10, 2);

						mA++;
						for (int j = 0; j < c; j++) {
							matrizA[i][j] = Float.parseFloat(elementosA[a]);

							float res = matrizA[i][j];

							TextView elemento = new TextView(this);
							elemento.setTextColor(getResources().getColor(
									android.R.color.black));
							elemento.setText(String.format("%.2f", res).concat("  "));
							elemento.setLayoutParams(new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
							layout.addView(elemento);
							a++;
						}

						linearMultiA.addView(layout);
					}

					for (int i = 0; i < matrizB.length; i++) {

						LinearLayout layout = new LinearLayout(this);
						layout.setOrientation(LinearLayout.HORIZONTAL);
						layout.setPadding(5, 2, 5, 2);

						mB++;
						for (int j = 0; j <= matrizB.length; j++) {
							matrizB[i][j] = Float.parseFloat(elementosB[b]);

							float res = matrizB[i][j];

							TextView elemento = new TextView(this);
							elemento.setTextColor(getResources().getColor(
									android.R.color.black));
							elemento.setText(String.format("%.2f", res).concat("  "));
							elemento.setLayoutParams(new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
							layout.addView(elemento);
							b++;
						}

						linearMultiB.setOrientation(LinearLayout.VERTICAL);
						linearMultiB.addView(layout);
					}
				} else {
					for (int i = 0; i < matrizA.length; i++) {

						LinearLayout layout = new LinearLayout(this);
						layout.setOrientation(LinearLayout.HORIZONTAL);
						layout.setPadding(10, 2, 10, 2);

						mA++;
						for (int j = 0; j < matrizA[i].length; j++) {
							matrizA[i][j] = Float.parseFloat(elementosA[a]);

							float res = matrizA[i][j];

							TextView elemento = new TextView(this);
							elemento.setTextColor(getResources().getColor(
									android.R.color.black));
							elemento.setText(String.format("%.2f", res).concat("  "));
							elemento.setLayoutParams(new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
							layout.addView(elemento);
							a++;
						}

						linearMultiA.addView(layout);
					}
					for (int i = 0; i < l; i++) {

						LinearLayout layout = new LinearLayout(this);
						layout.setOrientation(LinearLayout.VERTICAL);
						layout.setPadding(5, 2, 5, 2);

						mB++;
						for (int j = 0; j < matrizB.length; j++) {
							matrizB[j][i] = Float.parseFloat(elementosB[b]);

							float res = matrizB[j][i];

							TextView elemento = new TextView(this);
							elemento.setTextColor(getResources().getColor(
									android.R.color.black));
							elemento.setText(String.format("%.2f", res));
							elemento.setLayoutParams(new LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
							layout.addView(elemento);
							b++;
						}

						linearMultiB.addView(layout);
					}
				}
			}
		} else if (v.getId() == R.id.btnCalcularMatrizMulti) {
			if (opcao == false) {
				float[][] matrizC = new float[l][l];

				for (int i = 0; i < mA; i++) {

					LinearLayout layout = new LinearLayout(this);
					layout.setOrientation(LinearLayout.VERTICAL);
					layout.setPadding(10, 2, 10, 2);

					float valor = 0;

					for (int j = 0; j < mB; j++) {

						for (int k = 0; k <= mB; k++) {

							valor += matrizA[j][k] * matrizB[k][i];

						}

						matrizC[i][j] = valor;
						float res2 = matrizC[i][j];

						TextView elemento = new TextView(this);
						elemento.setText(String.format("%.2f", res2));
						elemento.setTextColor(getResources().getColor(
								android.R.color.black));
						elemento.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						layout.addView(elemento);
						valor = 0;
					}

					layoutMultiMatriz.addView(layout);
				}
				opcao = true;
			} else {
				float[][] matrizC = new float[l][l];

				for (int i = 0; i < l; i++) {

					LinearLayout layout = new LinearLayout(this);
					layout.setOrientation(LinearLayout.VERTICAL);
					layout.setPadding(10, 2, 10, 2);

					float valor = 0;

					for (int j = 0; j < l; j++) {

						for (int k = 0; k < c; k++) {

							valor += matrizA[j][k] * matrizB[k][i];

						}

						matrizC[i][j] = valor;
						float res2 = matrizC[i][j];

						TextView elemento = new TextView(this);
						elemento.setText(String.format("%.2f", res2));
						elemento.setTextColor(getResources().getColor(
								android.R.color.black));
						elemento.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						layout.addView(elemento);
						valor = 0;
					}

					layoutMultiMatriz.addView(layout);
				}
			}
		}

	}
}
