//Tela para preenchimento das informações relacionadas às habilidades de escrita, leitura e digitação

package br.dc.ufscar.whoami.activity.accessibility;

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
import br.dc.ufscar.whoami.activity.Characteristics;
import br.dc.ufscar.whoami.activity.Settings;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class Abilities extends Activity {

	private Default habilidade; //leitura, escrita e digutação

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.habilidades);

		setTitle("Configurações de Acessibilidade");

		//informações padrões do predicate
		habilidade = new Default();
		habilidade.setRange("yes-no");
		habilidade.setAuxiliary("has");
		habilidade.setSubgroup("Skills");
		habilidade.setGroup("Abilities");

		carregaHabilidades();

		Button botaoConfig = (Button) findViewById(R.id.habilidades_ok);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;
				
				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						Abilities.this).equals("checked")) {
					i = new Intent(Abilities.this, Characteristics.class);
					startActivity(i);

				} else {
					Toast.makeText(Abilities.this, "Configurações salvas",
							Toast.LENGTH_SHORT).show();
					i = new Intent(Abilities.this, Settings.class);
					startActivity(i);
				}

			}
		});
	}

	public void onClickHabilidades(View v) {

		AbilitiesDAO dao = new AbilitiesDAO(Abilities.this);
		boolean checked = ((CheckBox) v).isChecked();

		//o atributo object é preenchido caso o usuário possua a habilidade
		switch (v.getId()) {
		case R.id.ck_escrita:
			habilidade.setPredicate("WritingSkills"); //escrita
			habilidade.setObject("yes");
			habilidade.setId(R.id.ck_escrita);
			break;

		case R.id.ck_leitura:
			habilidade.setPredicate("ReadingSkills"); //leitura
			habilidade.setObject("yes");
			habilidade.setId(R.id.ck_leitura);
			break;

		case R.id.ck_digitacao:
			habilidade.setPredicate("TypingSkills"); //digitação
			habilidade.setObject("yes");
			habilidade.setId(R.id.ck_digitacao);
			break;

		default:
			break;
		}

		//grava informações no BD
		if (checked) {
			if (!dao.estaNaLista(habilidade.getPredicate())) {
				dao.salva(habilidade);
			}
		} else {
			if (dao.estaNaLista(habilidade.getPredicate())) {
				dao.exclui(habilidade);
			}
		}

		dao.close();

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
			i = new Intent(Abilities.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(Abilities.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(Abilities.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaHabilidades() {

		AbilitiesDAO dao = new AbilitiesDAO(Abilities.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaHabilidades = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaHabilidades
					.listIterator(); iterator.hasNext();) {
				Default habilidade = iterator.next();
				if (habilidade.getSubgroup().equals("Skills")) {
					CheckBox check = (CheckBox) findViewById(habilidade.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}
}
