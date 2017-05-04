//Tela para preenchimento das informações relacionadas à capacidade de fala

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

public class AccessSpeaks extends Activity {

	private Default habilidadeAtual; //fala

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acess__fala);
		setTitle("Configurações de Acessibilidade");

		habilidadeAtual = new Default();
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
			i = new Intent(AccessSpeaks.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(AccessSpeaks.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessSpeaks.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickFala(View v) {

        //o atributo object é preenchido de acordo com a seleção do usuário (se tem algum problema na fala ou não)
		switch (v.getId()) {
		case R.id.fala_sim:
			habilidadeAtual.setObject("no"); //com problemas na fala
			break;

		case R.id.fala_nao:
			habilidadeAtual.setObject("yes"); //sem problemas na fala
			break;

		default:
			break;
		}

        //informações padrões do predicate
		habilidadeAtual.setPredicate("AbilityToTalk");
		habilidadeAtual.setRange("yes-no");
		habilidadeAtual.setAuxiliary("has");
		habilidadeAtual.setGroup("Abilities");
		habilidadeAtual.setSubgroup("Capabilities");

        //grava informações no BD
        AbilitiesDAO dao = new AbilitiesDAO(AccessSpeaks.this);

		if (!dao.estaNaLista(habilidadeAtual.getPredicate())) {
			dao.salva(habilidadeAtual);
		} else {
			dao.altera(habilidadeAtual);
		}

		dao.close();

		Intent i = new Intent(AccessSpeaks.this, AccessVision.class);
		startActivity(i);
	}

}
