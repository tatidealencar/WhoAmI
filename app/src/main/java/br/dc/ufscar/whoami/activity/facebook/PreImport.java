//Tela inicial da importação dos dados do Facebook (opções: "Saiba mais", "Importar agora" e "Não importar")

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
import br.dc.ufscar.whoami.activity.Settings;
import br.dc.ufscar.whoami.activity.PersonalData;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PreImport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_importacao);

		setTitle("Importação de Dados");

		Button botaoSaibaMais = (Button) findViewById(R.id.saiba_mais);
		botaoSaibaMais.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PreImport.this, KnowMore.class);
				startActivity(i);
			}
		});

		Button botaoImportarAgora = (Button) findViewById(R.id.importar_agora_pre);
		botaoImportarAgora.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PreImport.this,
						ChooseDataImport.class);
				startActivity(i);
			}
		});

		Button botaoNaoImportar = (Button) findViewById(R.id.nao_importar);
		botaoNaoImportar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;

				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						PreImport.this).equals("checked")) {
					i = new Intent(PreImport.this, PersonalData.class);
					startActivity(i);

				} else {
					i = new Intent(PreImport.this, Settings.class);
					startActivity(i);
				}

			}
		});
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
			i = new Intent(PreImport.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PreImport.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PreImport.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
