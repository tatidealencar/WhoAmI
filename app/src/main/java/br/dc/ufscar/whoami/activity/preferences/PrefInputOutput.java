//Tela para seleção das preferências (tipos) de entrada e saída de dados
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

public class PrefInputOutput extends Activity {

	private Default entradaDadosVoz;
	private Default entradaDadosTouch;
	private Default saidaDadosVisual;
	private Default saidaDadosVoz;

	private RadioButton radioInputVoz;
	private RadioButton radioInputTouch;
	private RadioButton radioOutputVisual;
	private RadioButton radioOutputVoz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_input_output);

		setTitle("Preferências de Interface");

		//informações padrões do predicate VoiceInput
		entradaDadosVoz = new Default();
		entradaDadosVoz.setPredicate("VoiceInput");
		entradaDadosVoz.setRange("uninformed-primary-secondary");
		entradaDadosVoz.setAuxiliary("hasPreference");
		entradaDadosVoz.setSubgroup("Input");
		entradaDadosVoz.setGroup("InterfacePreferences");
		entradaDadosVoz.setId(R.id.input_voice);

		//informações padrões do predicate Touch
		entradaDadosTouch = new Default();
		entradaDadosTouch.setPredicate("Touch");
		entradaDadosTouch.setRange("uninformed-primary-secondary");
		entradaDadosTouch.setAuxiliary("hasPreference");
		entradaDadosTouch.setSubgroup("Input");
		entradaDadosTouch.setGroup("InterfacePreferences");
		entradaDadosTouch.setId(R.id.input_touch);

		//informações padrões do predicate Visual
		saidaDadosVisual = new Default();
		saidaDadosVisual.setPredicate("Visual");
		saidaDadosVisual.setRange("uninformed-primary-secondary");
		saidaDadosVisual.setAuxiliary("hasPreference");
		saidaDadosVisual.setSubgroup("Output");
		saidaDadosVisual.setGroup("InterfacePreferences");
		saidaDadosVisual.setId(R.id.output_visual);

		//informações padrões do predicate VoiceOutput
		saidaDadosVoz = new Default();
		saidaDadosVoz.setPredicate("VoiceOutput");
		saidaDadosVoz.setRange("uninformed-primary-secondary");
		saidaDadosVoz.setAuxiliary("hasPreference");
		saidaDadosVoz.setSubgroup("Output");
		saidaDadosVoz.setGroup("InterfacePreferences");
		saidaDadosVoz.setId(R.id.output_voice);

		//informações padrões do predicate
		radioInputVoz = (RadioButton) findViewById(R.id.input_voice);
		radioInputTouch = (RadioButton) findViewById(R.id.input_touch);
		radioOutputVisual = (RadioButton) findViewById(R.id.output_visual);
		radioOutputVoz = (RadioButton) findViewById(R.id.output_voice);

		carregaPreferencias();
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
			i = new Intent(PrefInputOutput.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefInputOutput.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefInputOutput.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//o atributo object é preenchido de acordo com a preferência primária e secundária do usuário
	public void onClickSalvaPrefIO(View v) {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefInputOutput.this);

		// Input
		if (radioInputVoz.isChecked()) {
			entradaDadosVoz.setObject("primary");
			entradaDadosTouch.setObject("secondary");
		} else {
			if (radioInputTouch.isChecked()) {
				entradaDadosTouch.setObject("primary");
				entradaDadosVoz.setObject("secondary");
			} else {
				entradaDadosVoz.setObject("uninformed");
				entradaDadosTouch.setObject("uninformed");
			}
		}
		if (!dao.estaNaLista(entradaDadosVoz)) {
			dao.salva(entradaDadosVoz);
		} else {
			dao.altera(entradaDadosVoz);
		}

		if (!dao.estaNaLista(entradaDadosTouch)) {
			dao.salva(entradaDadosTouch);
		} else {
			dao.altera(entradaDadosTouch);
		}

		// Output
		if (radioOutputVisual.isChecked()) {
			saidaDadosVisual.setObject("primary");
			saidaDadosVoz.setObject("secondary");
		} else {
			if (radioOutputVoz.isChecked()) {
				saidaDadosVoz.setObject("primary");
				saidaDadosVisual.setObject("secondary");
			} else {
				saidaDadosVisual.setObject("uninformed");
				saidaDadosVoz.setObject("uninformed");
			}
		}
		if (!dao.estaNaLista(saidaDadosVisual)) {
			dao.salva(saidaDadosVisual);
		} else {
			dao.altera(saidaDadosVisual);
		}

		if (!dao.estaNaLista(saidaDadosVoz)) {
			dao.salva(saidaDadosVoz);
		} else {
			dao.altera(saidaDadosVoz);
		}

		dao.close();

		Intent i = new Intent(PrefInputOutput.this, PrefInformationPresentation.class);
		startActivity(i);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaPreferencias() {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefInputOutput.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaPreferencias = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaPreferencias
					.listIterator(); iterator.hasNext();) {
				Default dado = iterator.next();

				if ((dado.getPredicate().equals("VoiceInput"))
						|| (dado.getPredicate().equals("Touch"))
						|| (dado.getPredicate().equals("Visual"))
						|| (dado.getPredicate().equals("VoiceOutput"))) {
					if (!dado.getObject().equals("uninformed")) {

						if (dado.getPredicate().equals(
								entradaDadosVoz.getPredicate())) {
							if (dado.getObject().equals("primary")) {
								radioInputVoz.setChecked(true);
							} else {
								if (dado.getObject().equals("secondary")) {
									radioInputTouch.setChecked(true);
								}
							}
						}
						if (dado.getPredicate().equals(
								saidaDadosVoz.getPredicate())) {
							if (dado.getObject().equals("primary")) {
								radioOutputVoz.setChecked(true);
							} else {
								if (dado.getObject().equals("secondary")) {
									radioOutputVisual.setChecked(true);
								}
							}
						}
					}
				}
			}
		}
		dao.close();
	}

}
