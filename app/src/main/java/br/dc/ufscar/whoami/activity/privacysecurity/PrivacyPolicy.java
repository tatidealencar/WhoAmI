//Tela para exibição da política de privacidade do aplicativo
package br.dc.ufscar.whoami.activity.privacysecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import br.dc.ufscar.whoami.R;

public class PrivacyPolicy extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.politica_privacidade);
		setTitle("Política de Privacidade");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recursos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.senha_de_acesso:
			i = new Intent(PrivacyPolicy.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrivacyPolicy.this, ExportProfile.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
