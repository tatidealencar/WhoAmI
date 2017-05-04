//Tela para preenchimento das informações relacionadas à percepção de cores

package br.dc.ufscar.whoami.activity.accessibility;

import java.util.ArrayList;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.model.Default;

public class AccessColorPerception extends Activity {

	private Default habilidadeAtual; //percepção de cores
	private RadioButton vermelho;
	private RadioButton verde;
	private RadioButton outros1;
	private RadioButton outros2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acess__percepcaocores);
		setTitle("Configurações de Acessibilidade");

		//informações padrões do predicate
		habilidadeAtual = new Default();
		habilidadeAtual.setPredicate("AbilityToDifferentiateColors");
		habilidadeAtual.setRange("uninformed-yes-no");
		habilidadeAtual.setAuxiliary("has");
		habilidadeAtual.setGroup("Abilities");
		habilidadeAtual.setSubgroup("Capabilities");

		vermelho = (RadioButton) findViewById(R.id.radio_vermelho);
		verde = (RadioButton) findViewById(R.id.radio_verde);
		outros1 = (RadioButton) findViewById(R.id.radio_outros_vermelho);
		outros2 = (RadioButton) findViewById(R.id.radio_outros_verde);

		carregaInfoPercepcaoCores();
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
			i = new Intent(AccessColorPerception.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(AccessColorPerception.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessColorPerception.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickPercepcaoCores(View v) {

		//o atributo object é preenchido de acordo com a seleção do usuário (se ouve ou não o miado do gato)
		if (vermelho.isChecked() && verde.isChecked()) {
			habilidadeAtual.setObject("yes"); //enxerga cores
		} else {
			if (outros1.isChecked() && outros2.isChecked()) {
				habilidadeAtual.setObject("no"); //não enxerga coers
			} else {
				habilidadeAtual.setObject("uninformed");
			}
		}

		//grava informações no BD
		AbilitiesDAO dao = new AbilitiesDAO(AccessColorPerception.this);

		if (!dao.estaNaLista(habilidadeAtual.getPredicate())) {
			dao.salva(habilidadeAtual);
		} else {
			dao.altera(habilidadeAtual);
		}

		dao.close();

		Intent i = new Intent(AccessColorPerception.this, Abilities.class);
		startActivity(i);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInfoPercepcaoCores() {

		AbilitiesDAO dao = new AbilitiesDAO(AccessColorPerception.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaHabilidades = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaHabilidades
					.listIterator(); iterator.hasNext();) {
				Default habilidade = iterator.next();
				if (habilidade.getPredicate().equals(
						habilidadeAtual.getPredicate())) {
					habilidadeAtual = habilidade;
					if (habilidadeAtual.getObject().toString().equals("yes")) {
						vermelho.setChecked(true);
						verde.setChecked(true);
					} else {
						if (habilidadeAtual.getObject().toString().equals("no")) {
							outros1.setChecked(true);
							outros2.setChecked(true);
						}
					}
				}
			}
		}
		dao.close();
	}
}