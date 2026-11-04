package br.edu.fatecgarca.matrizes;

import br.edu.fatecgarca.matrizes.controller.Operacoes;
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

public class SomaSubtracaoMatriz extends Activity implements OnClickListener {

	int l, c, quant;
	LinearLayout layoutMatrizA, layoutMatrizB, layoutMatrizC, layoutMatrizes;
	Button btnAdicionarElementos, btnCalcularMatriz;
	EditText edtElementoA, edtElementoB;
	TextView txtQuantElementos;
	String[] elementosA, elementosB;
	char op;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mostrarDialog();

		setContentView(R.layout.activity_soma);

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

		layoutMatrizA = (LinearLayout) findViewById(R.id.layoutMatrizA);
		layoutMatrizB = (LinearLayout) findViewById(R.id.layoutMatrizB);
		layoutMatrizC = (LinearLayout) findViewById(R.id.layoutMatrizC);

		btnAdicionarElementos = (Button) findViewById(R.id.btnAdicionarElemento);
		btnAdicionarElementos.setOnClickListener(this);
		btnCalcularMatriz = (Button) findViewById(R.id.btnCalcularMatriz);
		btnCalcularMatriz.setOnClickListener(this);
		btnCalcularMatriz.setEnabled(false);

		layoutMatrizes = (LinearLayout) findViewById(R.id.linearMatrizes);

		txtQuantElementos = (TextView) findViewById(R.id.txtQuantElementos);

		edtElementoA = (EditText) findViewById(R.id.edtElementoA);
		edtElementoB = (EditText) findViewById(R.id.edtElementoB);

		txtQuantElementos.setText(String.valueOf(quant));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnAdicionarElemento) {
			layoutMatrizA.removeAllViewsInLayout();
			layoutMatrizB.removeAllViewsInLayout();

			float[][] matrizA = new float[l][c];
			float[][] matrizB = new float[l][c];

			elementosA = edtElementoA.getText().toString().split(",");
			elementosB = edtElementoB.getText().toString().split(",");

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

				btnCalcularMatriz.setEnabled(true);

				int a = 0;

				for (int i = 0; i < matrizA.length; i++) {

					LinearLayout layout = new LinearLayout(this);
					layout.setOrientation(LinearLayout.HORIZONTAL);
					layout.setPadding(10, 2, 10, 2);

					for (int j = 0; j < matrizA[i].length; j++) {
						matrizA[i][j] = Float.parseFloat(elementosA[a]);

						float res = matrizA[i][j];

						TextView elemento = new TextView(this);
						elemento.setTextColor(getResources().getColor(
								android.R.color.black));
						elemento.setText(String.valueOf(res).concat("  "));
						elemento.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						layout.addView(elemento);
						a++;
					}

					layoutMatrizA.addView(layout);
				}

				int b = 0;

				for (int i = 0; i < matrizB.length; i++) {

					LinearLayout layout = new LinearLayout(this);
					layout.setOrientation(LinearLayout.HORIZONTAL);
					layout.setPadding(10, 2, 10, 2);

					for (int j = 0; j < matrizB[i].length; j++) {
						matrizB[i][j] = Float.parseFloat(elementosB[b]);

						float res = matrizB[i][j];

						TextView elemento = new TextView(this);
						elemento.setTextColor(getResources().getColor(
								android.R.color.black));
						elemento.setText(String.valueOf(res).concat("  "));
						elemento.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						layout.addView(elemento);
						b++;
					}

					layoutMatrizB.addView(layout);
				}
			}
		} else if (v.getId() == R.id.btnCalcularMatriz) {
			Intent soma = new Intent(this, Operacoes.class);
			Bundle params = new Bundle();
			params.putStringArray("elementosA", elementosA);
			params.putStringArray("elementosB", elementosB);
			params.putInt("l", l);
			params.putInt("c", c);
			params.putChar("op", op);
			soma.putExtras(params);
			finish();
			startActivity(soma);
		}
	}

	@SuppressWarnings("deprecation")
	protected void mostrarDialog() {
		AlertDialog alertDialog1 = new AlertDialog.Builder(
				SomaSubtracaoMatriz.this).create();
		alertDialog1.setTitle("Informação");
		alertDialog1
				.setMessage("Insira os elementos separados por vírgula. \n\nPor exemplo: 2.0,3,5.1,7");
		alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alertDialog1.show();

	}
}
