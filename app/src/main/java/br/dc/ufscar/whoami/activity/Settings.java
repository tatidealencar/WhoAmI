//Tela para indicação de quais preferências serão preenchidas (acessibilidade, dados pessoais, característica ou preferências)
package br.dc.ufscar.whoami.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.accessibility.AccessLocomotion;
import br.dc.ufscar.whoami.activity.facebook.PreImport;
import br.dc.ufscar.whoami.activity.preferences.PreferencesMain;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;

public class Settings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		setTitle("Configurações");
		
		/*Button usoFacebook = (Button) findViewById(R.id.uso_facebook);
		usoFacebook.setVisibility(View.GONE);*/
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
			i = new Intent(Settings.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(Settings.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(Settings.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickConfiguracoes(View v) {

		Intent i;

		switch (v.getId()) {
		case R.id.acessibilidade:
			i = new Intent(Settings.this, AccessLocomotion.class);
			startActivity(i);
			break;

		case R.id.dados_pessoais:
			i = new Intent(Settings.this, PersonalData.class);
			startActivity(i);
			break;

		case R.id.caracteristicas:
			i = new Intent(Settings.this, Characteristics.class);
			startActivity(i);
			break;

		case R.id.preferencias:
			i = new Intent(Settings.this, PreferencesMain.class);
			startActivity(i);
			break;

		case R.id.uso_facebook:
			i = new Intent(Settings.this, PreImport.class);
			startActivity(i);

		default:
			break;
		}
	}
}