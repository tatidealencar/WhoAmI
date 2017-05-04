//Tela para seleção das preferências relacionadas à tela (tamanho da fonte, dos elementos gráficos e contraste)
package br.dc.ufscar.whoami.activity.preferences;

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
import br.dc.ufscar.whoami.dao.InterfacePreferencesDAO;
import br.dc.ufscar.whoami.model.Default;

public class PrefLayout extends Activity {

	private Default tamanhoFonte;
	private Default tamanhoElementosGraficos;
	private Default contraste;

	private RadioButton radioFontePequena;
	private RadioButton radioFonteMedia;
	private RadioButton radioFonteGrande;
	private RadioButton radioElementoPequeno;
	private RadioButton radioElementoMedio;
	private RadioButton radioElementoGrande;
	private RadioButton radioContrasteSim;
	private RadioButton radioContrasteNao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_layout);

		setTitle("Preferências de Interface");

		//informações padrões do predicate FontSize
		tamanhoFonte = new Default();
		tamanhoFonte.setPredicate("FontSize");
		tamanhoFonte.setRange("uninformed-small-medium-large");
		tamanhoFonte.setAuxiliary("hasPreference");
		tamanhoFonte.setSubgroup("Layout");
		tamanhoFonte.setGroup("InterfacePreferences");

		//informações padrões do predicate GraphicalElementSize
		tamanhoElementosGraficos = new Default();
		tamanhoElementosGraficos.setPredicate("GraphicalElementSize");
		tamanhoElementosGraficos.setRange("uninformed-small-medium-large");
		tamanhoElementosGraficos.setAuxiliary("hasPreference");
		tamanhoElementosGraficos.setSubgroup("Layout");
		tamanhoElementosGraficos.setGroup("InterfacePreferences");

		//informações padrões do predicate Contrast
		contraste = new Default();
		contraste.setPredicate("Contrast");
		contraste.setRange("uninformed-yes-no");
		contraste.setAuxiliary("hasPreference");
		contraste.setSubgroup("Layout");
		contraste.setGroup("InterfacePreferences");

		radioFontePequena = (RadioButton) findViewById(R.id.fonte_pequena);
		radioFonteMedia = (RadioButton) findViewById(R.id.fonte_media);
		radioFonteGrande = (RadioButton) findViewById(R.id.fonte_grande);
		radioElementoPequeno = (RadioButton) findViewById(R.id.elemento_pequeno);
		radioElementoMedio = (RadioButton) findViewById(R.id.elemento_medio);
		radioElementoGrande = (RadioButton) findViewById(R.id.elemento_grande);
		radioContrasteSim = (RadioButton) findViewById(R.id.contraste_sim);
		radioContrasteNao = (RadioButton) findViewById(R.id.contraste_nao);

		carregaPreferencias();
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
			i = new Intent(PrefLayout.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefLayout.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefLayout.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//o atributo object é preenchido de acordo com a preferência do usuário
	public void onClickSalvaPrefLayout(View v) {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefLayout.this);

		// Fonte
		if (radioFontePequena.isChecked()) {
			tamanhoFonte.setObject("small");
			tamanhoFonte.setId(R.id.fonte_pequena);
		} else {
			if (radioFonteMedia.isChecked()) {
				tamanhoFonte.setObject("medium");
				tamanhoFonte.setId(R.id.fonte_media);

			} else {
				if (radioFonteGrande.isChecked()) {
					tamanhoFonte.setObject("large");
					tamanhoFonte.setId(R.id.fonte_grande);
				} else {
					tamanhoFonte.setObject("uninformed");
				}
			}
		}
		if (!dao.estaNaLista(tamanhoFonte)) {
			dao.salva(tamanhoFonte);
		} else {
			dao.altera(tamanhoFonte);
		}

		// Elementos gráficos
		if (radioElementoPequeno.isChecked()) {
			tamanhoElementosGraficos.setObject("small");
			tamanhoElementosGraficos.setId(R.id.elemento_pequeno);
		} else {
			if (radioElementoMedio.isChecked()) {
				tamanhoElementosGraficos.setObject("medium");
				tamanhoElementosGraficos.setId(R.id.elemento_medio);

			} else {
				if (radioElementoGrande.isChecked()) {
					tamanhoElementosGraficos.setObject("large");
					tamanhoElementosGraficos.setId(R.id.elemento_grande);
				} else {
					tamanhoElementosGraficos.setObject("uninformed");
				}
			}
		}
		;

		if (!dao.estaNaLista(tamanhoElementosGraficos)) {
			dao.salva(tamanhoElementosGraficos);
		} else {
			dao.altera(tamanhoElementosGraficos);
		}

		// Contraste
		if (radioContrasteSim.isChecked()) {
			contraste.setObject("yes");
			contraste.setId(R.id.contraste_sim);
		} else {
			if (radioContrasteNao.isChecked()) {
				contraste.setObject("no");
				contraste.setId(R.id.contraste_nao);
			} else {
				contraste.setObject("uninformed");
			}
		}

		if (!dao.estaNaLista(contraste)) {
			dao.salva(contraste);
		} else {
			dao.altera(contraste);
		}

		dao.close();

		Intent i = new Intent(PrefLayout.this, PrefInputOutput.class);
		startActivity(i);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaPreferencias() {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefLayout.this);
		if (!dao.isEmpty()) {
			ArrayList<Default> listaPreferencias = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaPreferencias
					.listIterator(); iterator.hasNext();) {
				Default dado = iterator.next();
				if (dado.getPredicate().equals("FontSize")
						|| dado.getPredicate().equals("GraphicalElementSize")
						|| dado.getPredicate().equals("Contrast")) {
					if (!dado.getObject().equals("uninformed")) {

						if (dado.getPredicate().equals(
								tamanhoFonte.getPredicate())) {

							RadioButton radio = (RadioButton) findViewById(dado
									.getId());
							radio.setChecked(true);
						}
						if (dado.getPredicate().equals(
								tamanhoElementosGraficos.getPredicate())) {

							RadioButton radio = (RadioButton) findViewById(dado
									.getId());
							radio.setChecked(true);
						}
						if (dado.getPredicate()
								.equals(contraste.getPredicate())) {
							RadioButton radio = (RadioButton) findViewById(dado
									.getId());
							radio.setChecked(true);
						}
					}
				}
			}
		}
		dao.close();
	}
}
