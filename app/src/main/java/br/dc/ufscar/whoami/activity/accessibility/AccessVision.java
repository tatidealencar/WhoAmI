//Tela para preenchimento das informações relacionadas à capacidade de visão

package br.dc.ufscar.whoami.activity.accessibility;

import java.util.ArrayList;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.model.Default;

public class AccessVision extends Activity {

	private Default habilidadeAtual; //visão
	private CheckBox visaOpcao1; //círculo
	private CheckBox visaOpcao2; //triângulo
	private CheckBox visaOpcao3; //estrela

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acess__visao);
		setTitle("Configurações de Acessibilidade");

		visaOpcao1 = (CheckBox) findViewById(R.id.visao_opcao1);
		visaOpcao2 = (CheckBox) findViewById(R.id.visao_opcao2);
		visaOpcao3 = (CheckBox) findViewById(R.id.visao_opcao3);

		//informações padrões do predicate
		habilidadeAtual = new Default();
		habilidadeAtual.setPredicate("AbilityToSee");
		habilidadeAtual.setRange("low-medium-high");
		habilidadeAtual.setAuxiliary("has");
		habilidadeAtual.setGroup("Abilities");
		habilidadeAtual.setSubgroup("Capabilities");

		carregaInfoVisao();
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
			i = new Intent(AccessVision.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(AccessVision.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessVision.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickVisao(View v) {

		/*o atributo object é preenchido de o grau de visão (high, medium e low) de usuário
		de acordo com os itens que ele enxerga (círculo, triângulo e estrela)*/
		if (visaOpcao1.isChecked() && visaOpcao2.isChecked()
				&& visaOpcao3.isChecked()) {
			habilidadeAtual.setObject("high");
		} else {
			if (visaOpcao1.isChecked() && visaOpcao2.isChecked()
					&& !visaOpcao3.isChecked()) {
				habilidadeAtual.setObject("medium");
			} else {
				if (visaOpcao1.isChecked() && !visaOpcao2.isChecked()
						&& !visaOpcao3.isChecked()) {
					habilidadeAtual.setObject("low");
				} else {
					habilidadeAtual.setObject("uninformed");
				}
			}
		}

		//grava informações no BD
		AbilitiesDAO dao = new AbilitiesDAO(AccessVision.this);

		if (!dao.estaNaLista(habilidadeAtual.getPredicate())) {
			dao.salva(habilidadeAtual);
		} else {
			dao.altera(habilidadeAtual);
		}

		dao.close();

		Intent i = new Intent(AccessVision.this, AccessColorPerception.class);
		startActivity(i);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInfoVisao() {

		AbilitiesDAO dao = new AbilitiesDAO(AccessVision.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaHabilidades = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaHabilidades
					.listIterator(); iterator.hasNext();) {
				Default habilidade = iterator.next();
				if (habilidade.getPredicate().equals(
						habilidadeAtual.getPredicate())) {
					habilidadeAtual = habilidade;
					if (habilidadeAtual.getObject().equals("high")) {
						visaOpcao1.setChecked(true);
						visaOpcao2.setChecked(true);
						visaOpcao3.setChecked(true);
					} else {
						if (habilidadeAtual.getObject().equals("medium")) {
							visaOpcao1.setChecked(true);
							visaOpcao2.setChecked(true);
						} else {
							if (habilidadeAtual.getObject().equals("low")) {
								visaOpcao1.setChecked(true);
							}
						}
					}
				}
			}
		}
		dao.close();
	}
}
