//Tela informativa sobre a importação de dados do Facebook
package br.dc.ufscar.whoami.activity.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;

public class KnowMore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saiba_mais);
		
		setTitle("Importação de Dados");

		Button botaoImportar = (Button) findViewById(R.id.importar);
		botaoImportar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(KnowMore.this,
						ChooseDataImport.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recursos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.senha_de_acesso:
			i = new Intent(KnowMore.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(KnowMore.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(KnowMore.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
