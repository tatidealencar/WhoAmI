//Tela para preenchimento das informações relacionadas à capacidade de locomoção

package br.dc.ufscar.whoami.activity.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.model.Default;

public class AccessLocomotion extends Activity {

	private Default habilidadeAtual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acess__locomocao);
		setTitle("Configurações de Acessibilidade");

		habilidadeAtual = new Default(); //locomoção
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
			i = new Intent(AccessLocomotion.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(AccessLocomotion.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessLocomotion.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickLocomocao(View v) {

		//o atributo object é preenchido de acordo com a seleção do usuário (se tem problemas para andar ou não)
		switch (v.getId()) {
		case R.id.locomocao_sim:
			habilidadeAtual.setObject("no"); //não se locomove
			break;

		case R.id.locomocao_nao:
			habilidadeAtual.setObject("yes"); //se locomove
			break;

		default:
			break;
		}

		//informações padrões do predicate
		habilidadeAtual.setPredicate("AbilityToWalk");
		habilidadeAtual.setRange("yes-no");
		habilidadeAtual.setAuxiliary("has");
		habilidadeAtual.setGroup("Abilities");
		habilidadeAtual.setSubgroup("Capabilities");

		//grava informações no BD
		AbilitiesDAO dao = new AbilitiesDAO(AccessLocomotion.this);
		
		if (!dao.estaNaLista(habilidadeAtual.getPredicate())) {
			dao.salva(habilidadeAtual);
		} else {
			dao.altera(habilidadeAtual);
		}

		dao.close();
		
		Intent i = new Intent(AccessLocomotion.this, AccessHearing.class);
		startActivity(i);
	}

}
