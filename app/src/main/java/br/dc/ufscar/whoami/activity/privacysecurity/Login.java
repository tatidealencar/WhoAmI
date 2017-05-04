//Tela para autenticação do usuário no aplicativo, caso ele tenha optado por colocar senha de bloqueio
package br.dc.ufscar.whoami.activity.privacysecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.Home;
import br.dc.ufscar.whoami.dao.UserDAO;
import br.dc.ufscar.whoami.utils.ComputeHashMD5;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
	}

	public void onClickLogin(View v) {

		Intent i;

		switch (v.getId()) {
		case R.id.login:
			TextView senhaDigitada = (TextView) findViewById(R.id.senha_login);
			if (verificaSenha(senhaDigitada.getText().toString())) {
				i = new Intent(Login.this, Home.class);
				startActivity(i);
			} else {
				Toast.makeText(Login.this, "A senha digitada não confere",
						Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.recuperar_senha:
			i = new Intent(Login.this, RecoverPassword.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}

	public boolean verificaSenha(String senha) {
		UserDAO dao = new UserDAO(Login.this);

		String senhaBD = dao.getSenha();
		dao.close();

		if (ComputeHashMD5.compute(senha).compareTo(senhaBD) == 0) {
			return true;
		} else {
			return false;
		}
	}

}
