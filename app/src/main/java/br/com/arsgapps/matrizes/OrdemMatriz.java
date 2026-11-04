package br.com.arsgapps.matrizes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrdemMatriz extends Activity implements OnClickListener {

	int l, c;
	String linhas, colunas;
	EditText edtLinhas, edtColunas;
	Button btnInformarValores;
	char op;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordem);

		edtLinhas = findViewById(R.id.edtNumLinhas);
		edtColunas = findViewById(R.id.edtNumColunas);

		btnInformarValores = findViewById(R.id.btnInformarValores);
		btnInformarValores.setOnClickListener(this);

		Intent soma = getIntent();
		if (soma != null) {
			Bundle params = soma.getExtras();
			if (params != null) {
				op = params.getChar("op");
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnInformarValores) {
			linhas = edtLinhas.getText().toString();
			colunas = edtColunas.getText().toString();

			l = Integer.parseInt(linhas);
			c = Integer.parseInt(colunas);

			if ((l > 0) && (c > 0)) {
				if (op == '1' || op == '2') {
					Intent soma = new Intent(this, SomaSubtracaoMatriz.class);
					Bundle params = new Bundle();
					params.putInt("l", l);
					params.putInt("c", c);
					params.putChar("op", op);
					soma.putExtras(params);
					finish();
					startActivity(soma);
				} else if (op == '3') {
					Intent mulConst = new Intent(this, MultiConstante.class);
					Bundle params = new Bundle();
					params.putInt("l", l);
					params.putInt("c", c);
					params.putChar("op", op);
					mulConst.putExtras(params);
					finish();
					startActivity(mulConst);
				} else if(op == '4'){
					Intent mulMatrizes = new Intent(this, MultiMatrizes.class);
					Bundle params = new Bundle();
					params.putInt("l", l);
					params.putInt("c", c);
					params.putChar("op", op);
					mulMatrizes.putExtras(params);
					finish();
					startActivity(mulMatrizes);
				}
			} else {
				Toast.makeText(this, "Insira valores válidos",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
