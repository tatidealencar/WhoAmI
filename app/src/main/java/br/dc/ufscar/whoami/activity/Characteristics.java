//Tela para indicação das características do usuário
package br.dc.ufscar.whoami.activity;

import java.util.ArrayList;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.preferences.PrefHealth;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.CharacteristicsDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class Characteristics extends Activity {

	private Default caracteristica;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caracteristicas);

		setTitle("Características");

		//informações padrões do predicate
		caracteristica = new Default();
		caracteristica.setGroup("Characteristics");

		carregaCaracteristicas();

		Button botaoSalvar = (Button) findViewById(R.id.salvar_caracteristicas);
		botaoSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;
				
				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						Characteristics.this).equals("checked")) {
					i = new Intent(Characteristics.this, PrefHealth.class);
					startActivity(i);

				} else {
					Toast.makeText(Characteristics.this, "Configurações salvas",
							Toast.LENGTH_SHORT).show();

					i = new Intent(Characteristics.this, Settings.class);
					startActivity(i);
				}
			}
		});

	}

	//cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickCaracteristicas(final View v) {

		CharacteristicsDAO dao = new CharacteristicsDAO(Characteristics.this);
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.caracteristica1:
			caracteristica.setPredicate("Anxious");
			caracteristica.setId(R.id.caracteristica1);
			break;
		case R.id.caracteristica2:
			caracteristica.setPredicate("Kind");
			caracteristica.setId(R.id.caracteristica2);
			break;
		case R.id.caracteristica3:
			caracteristica.setPredicate("Calm");
			caracteristica.setId(R.id.caracteristica3);
			break;
		case R.id.caracteristica4:
			caracteristica.setPredicate("Talkative");
			caracteristica.setId(R.id.caracteristica4);
			break;
		case R.id.caracteristica5:
			caracteristica.setPredicate("Moody");
			caracteristica.setId(R.id.caracteristica5);
			break;
		case R.id.caracteristica6:
			caracteristica.setPredicate("Organized");
			caracteristica.setId(R.id.caracteristica6);
			break;
		case R.id.caracteristica7:
			caracteristica.setPredicate("Quiet");
			caracteristica.setId(R.id.caracteristica7);
			break;
		case R.id.caracteristica8:
			caracteristica.setPredicate("Reserved");
			caracteristica.setId(R.id.caracteristica8);
			break;
		case R.id.caracteristica9:
			caracteristica.setPredicate("Sympathetic");
			caracteristica.setId(R.id.caracteristica9);
			break;
		case R.id.caracteristica10:
			caracteristica.setPredicate("Tense");
			caracteristica.setId(R.id.caracteristica10);
			break;
		case R.id.caracteristica11:
			caracteristica.setPredicate("Shy");
			caracteristica.setId(R.id.caracteristica11);
			break;

		default:
			break;
		}

		if (checked) {
			if (!dao.estaNaLista(caracteristica.getPredicate())) {
				dao.salva(caracteristica);
			}
		} else {
			if (dao.estaNaLista(caracteristica.getPredicate())) {
				dao.exclui(caracteristica);
			}
		}
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
			i = new Intent(Characteristics.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(Characteristics.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(Characteristics.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaCaracteristicas() {

		CharacteristicsDAO dao = new CharacteristicsDAO(Characteristics.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaCaracteristicas = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaCaracteristicas
					.listIterator(); iterator.hasNext();) {
				Default caracteristica = iterator.next();

				CheckBox check = (CheckBox) findViewById(caracteristica.getId());
				check.setChecked(true);
			}
		}
		dao.close();
	}
}
