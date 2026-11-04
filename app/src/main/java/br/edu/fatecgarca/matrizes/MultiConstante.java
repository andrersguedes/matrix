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

public class MultiConstante extends Activity implements OnClickListener {

	char op;
	int l, c, quant;
	Button btnAdicionarElementosConst, btnCalcularMatrizConst;
	EditText edtElementoConst, edtConstante;
	String[] elementosConst;
	LinearLayout linearMatrizesConst;
	double constante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_constante);

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

		edtElementoConst = (EditText) findViewById(R.id.edtElementoConst);
		edtConstante = (EditText) findViewById(R.id.edtConstante);

		btnAdicionarElementosConst = (Button) findViewById(R.id.btnAdicionarElementoConst);
		btnAdicionarElementosConst.setOnClickListener(this);
		btnCalcularMatrizConst = (Button) findViewById(R.id.btnCalcularMatrizConst);
		btnCalcularMatrizConst.setOnClickListener(this);
		btnCalcularMatrizConst.setEnabled(false);

		linearMatrizesConst = (LinearLayout) findViewById(R.id.layoutMatrizConst);

	}

	@SuppressWarnings("deprecation")
	protected void mostrarDialog() {
		AlertDialog alertDialog1 = new AlertDialog.Builder(MultiConstante.this)
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
		float[][] matrizA = new float[l][c];

		if (v.getId() == R.id.btnAdicionarElementoConst) {

			elementosConst = edtElementoConst.getText().toString().split(",");

			if (elementosConst.length != quant) {
				Toast.makeText(
						this,
						"Quantidade de elementos da matriz não corresponde com a ordem da matriz!",
						Toast.LENGTH_SHORT).show();
			} else {
				int a = 0;

				for (int i = 0; i < matrizA.length; i++) {

					LinearLayout layout = new LinearLayout(this);
					layout.setOrientation(LinearLayout.HORIZONTAL);
					layout.setPadding(10, 2, 10, 2);

					for (int j = 0; j < matrizA[i].length; j++) {
						matrizA[i][j] = Float.parseFloat(elementosConst[a]);

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

					linearMatrizesConst.addView(layout);
				}

				btnCalcularMatrizConst.setEnabled(true);
			}
		} else if (v.getId() == R.id.btnCalcularMatrizConst) {
			constante = Double.parseDouble(edtConstante.getText().toString());

			Intent soma = new Intent(this, Operacoes.class);
			Bundle params = new Bundle();
			params.putStringArray("elementosA", elementosConst);
			params.putDouble("constante", constante);
			params.putInt("l", l);
			params.putInt("c", c);
			params.putChar("op", op);
			soma.putExtras(params);
			finish();
			startActivity(soma);
		}
	}
}
