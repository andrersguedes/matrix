package br.com.arsgapps.matrizes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
 * @author André Guedes
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnSoma) {
			Intent soma = new Intent(this, OrdemMatriz.class);
			Bundle params = new Bundle();
			params.putChar("op", '1');
			soma.putExtras(params);
			startActivity(soma);
		} else if (v.getId() == R.id.btnSubtrai) {
			Intent soma = new Intent(this, OrdemMatriz.class);
			Bundle params = new Bundle();
			params.putChar("op", '2');
			soma.putExtras(params);
			startActivity(soma);
		} else if (v.getId() == R.id.btnMultiplica) {
			Intent mulConst = new Intent(this, OrdemMatriz.class);
			Bundle params = new Bundle();
			params.putChar("op", '3');
			mulConst.putExtras(params);
			startActivity(mulConst);
		} else if (v.getId() == R.id.btnMultiplicaMatrizes) {
			Intent mulMatrizes = new Intent(this, OrdemMatriz.class);
			Bundle params = new Bundle();
			params.putChar("op", '4');
			mulMatrizes.putExtras(params);
			startActivity(mulMatrizes);
		}
	}
}
