//Tela para preenchimento dos dados pessoais do usuário
package br.dc.ufscar.whoami.activity;

import java.util.ArrayList;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.preferences.PrefMovies;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.DemographicsDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PersonalData extends Activity {

	private ArrayAdapter<CharSequence> primeiraLinguaAdapter;
	private ArrayAdapter<CharSequence> segundaLinguaAdapter;

	private Default dadoIdade;
	private Default dadoSexo;
	private Default dadoPrimeiraLingua;
	private Default dadoSegundaLingua;
	private Default dadoEscolaridade;
	private Default dadoOcupacao;

	private Spinner spinnerSegundaLingua;
	private Spinner spinnerPrimeiraLingua;
	private EditText editTextIdade;
	private RadioButton radioSexoMasculino;
	private RadioButton radioSexoFeminino;
	private RadioButton radioSemEscolaridade;
	private RadioButton radioEnsinoFundamental;
	private RadioButton radioEnsinoMedio;
	private RadioButton radioEnsinoSuperior;
	private EditText editTextOocupacao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dados_pessoais);

		setTitle("Dados pessoais");

		//informações padrões do predicate Age
		dadoIdade = new Default();
		dadoIdade.setPredicate("Age");
		dadoIdade.setRange("uninformed-years");
		dadoIdade.setAuxiliary("has");
		dadoIdade.setGroup("Demographics");
		dadoIdade.setId(R.id.idade);

		//informações padrões do predicate Gender
		dadoSexo = new Default();
		dadoSexo.setPredicate("Gender");
		dadoSexo.setRange("uninformed-male-female");
		dadoSexo.setAuxiliary("has");
		dadoSexo.setGroup("Demographics");

		//informações padrões do predicate FirstLanguage
		dadoPrimeiraLingua = new Default();
		dadoPrimeiraLingua.setPredicate("FirstLanguage");
		dadoPrimeiraLingua.setRange("languages");
		dadoPrimeiraLingua.setAuxiliary("hasKnowledge");
		dadoPrimeiraLingua.setGroup("Demographics");

		//informações padrões do predicate SecondLanguage
		dadoSegundaLingua = new Default();
		dadoSegundaLingua.setPredicate("SecondLanguage");
		dadoSegundaLingua.setRange("languages");
		dadoSegundaLingua.setAuxiliary("hasKnowledge");
		dadoSegundaLingua.setGroup("Demographics");

		//informações padrões do predicate EducationLevel
		dadoEscolaridade = new Default();
		dadoEscolaridade.setPredicate("EducationLevel");
		dadoEscolaridade.setRange("uninformed-basic-primary-secondary-higher");
		dadoEscolaridade.setAuxiliary("has");
		dadoEscolaridade.setGroup("Demographics");

		//informações padrões do predicate Employment
		dadoOcupacao = new Default();
		dadoOcupacao.setPredicate("Employment");
		dadoOcupacao.setRange("uninformed-jobs");
		dadoOcupacao.setAuxiliary("has");
		dadoOcupacao.setGroup("Demographics");
		dadoOcupacao.setId(R.id.ocupacao);

		spinnerPrimeiraLingua = (Spinner) findViewById(R.id.primeira_lingua);
		primeiraLinguaAdapter = ArrayAdapter.createFromResource(this,
				R.array.idiomas, android.R.layout.simple_spinner_item);
		primeiraLinguaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPrimeiraLingua.setAdapter(primeiraLinguaAdapter);

		spinnerSegundaLingua = (Spinner) findViewById(R.id.segunda_lingua);
		segundaLinguaAdapter = ArrayAdapter.createFromResource(this,
				R.array.idiomas, android.R.layout.simple_spinner_item);
		segundaLinguaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSegundaLingua.setAdapter(segundaLinguaAdapter);

		editTextIdade = (EditText) findViewById(R.id.idade);
		radioSexoMasculino = (RadioButton) findViewById(R.id.masculino);
		radioSexoFeminino = (RadioButton) findViewById(R.id.feminino);
		radioSemEscolaridade = (RadioButton) findViewById(R.id.sem_escolaridade);
		radioEnsinoFundamental = (RadioButton) findViewById(R.id.ensino_fundamental);
		radioEnsinoMedio = (android.widget.RadioButton) findViewById(R.id.ensino_medio);
		radioEnsinoSuperior = (android.widget.RadioButton) findViewById(R.id.ensino_superior);
		editTextOocupacao = (EditText) findViewById(R.id.ocupacao);

		carregaDadosPessoais();
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
			i = new Intent(PersonalData.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PersonalData.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PersonalData.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//o atributo object é preenchido de acordo com os dados informados pelo usuário
	public void onClickSalvaDP(View v) {
		DemographicsDAO dao = new DemographicsDAO(PersonalData.this);

		// Idade
		if (!editTextIdade.getText().toString().equals("")) {
			dadoIdade.setObject(editTextIdade.getText().toString());
		} else {
			dadoIdade.setObject("uninformed");
		}
		if (!dao.estaNaLista(dadoIdade.getPredicate())) {
			dao.salva(dadoIdade);
		} else {
			dao.altera(dadoIdade);
		}

		// Sexo
		if (radioSexoMasculino.isChecked()) {
			dadoSexo.setObject("male");
			dadoSexo.setId(R.id.masculino);
		} else {
			if (radioSexoFeminino.isChecked()) {
				dadoSexo.setObject("female");
				dadoSexo.setId(R.id.feminino);
			} else {
				dadoSexo.setObject("uninformed");
			}
		}
		if (!dao.estaNaLista(dadoSexo.getPredicate())) {
			dao.salva(dadoSexo);
		} else {
			dao.altera(dadoSexo);
		}

		// Primeira lingua
		dadoPrimeiraLingua.setObject(spinnerPrimeiraLingua.getSelectedItem()
				.toString());
		dadoPrimeiraLingua.setId(spinnerPrimeiraLingua
				.getSelectedItemPosition());
		if (!dao.estaNaLista(dadoPrimeiraLingua.getPredicate())) {
			dao.salva(dadoPrimeiraLingua);
		} else {
			dao.altera(dadoPrimeiraLingua);
		}

		// Segunda lingua
		dadoSegundaLingua.setObject(spinnerSegundaLingua.getSelectedItem()
				.toString());
		dadoSegundaLingua.setId(spinnerSegundaLingua.getSelectedItemPosition());
		if (!dao.estaNaLista(dadoSegundaLingua.getPredicate())) {
			dao.salva(dadoSegundaLingua);
		} else {
			dao.altera(dadoSegundaLingua);
		}

		// Escolaridade
		if (radioSemEscolaridade.isChecked()) {
			dadoEscolaridade.setObject("basic");
			dadoEscolaridade.setId(R.id.sem_escolaridade);
		} else {
			if (radioEnsinoFundamental.isChecked()) {
				dadoEscolaridade.setObject("primary");
				dadoEscolaridade.setId(R.id.ensino_fundamental);
			} else {
				if (radioEnsinoMedio.isChecked()) {
					dadoEscolaridade.setObject("secondary");
					dadoEscolaridade.setId(R.id.ensino_medio);
				} else {
					if (radioEnsinoSuperior.isChecked()) {
						dadoEscolaridade.setObject("higher");
						dadoEscolaridade.setId(R.id.ensino_superior);
					} else {
						dadoEscolaridade.setObject("uninformed");
					}
				}
			}
		}
		if (!dao.estaNaLista(dadoEscolaridade.getPredicate())) {
			dao.salva(dadoEscolaridade);
		} else {
			dao.altera(dadoEscolaridade);
		}

		// Ocupacao
		if (!editTextOocupacao.getText().toString().equals("")) {
			dadoOcupacao.setObject(editTextOocupacao.getText().toString());
		} else {
			dadoOcupacao.setObject("uninformed");
		}
		if (!dao.estaNaLista(dadoOcupacao.getPredicate())) {
			dao.salva(dadoOcupacao);
		} else {
			dao.altera(dadoOcupacao);
		}

		dao.close();

		SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
		Intent i;

		if (!sharedPreferences.loadSavedPreferences("FirstAccess",
				PersonalData.this).equals("checked")) {
			i = new Intent(PersonalData.this, PrefMovies.class);
			startActivity(i);

		} else {
			Toast.makeText(PersonalData.this, "Configurações salvas",
					Toast.LENGTH_SHORT).show();

			i = new Intent(PersonalData.this, Settings.class);
			startActivity(i);
		}
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaDadosPessoais() {

		DemographicsDAO dao = new DemographicsDAO(PersonalData.this);
		if (!dao.isEmpty()) {
			ArrayList<Default> listaDados = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaDados.listIterator(); iterator
					.hasNext();) {
				Default dado = iterator.next();
				if (!dado.getObject().toString().equals("uninformed")
						&& !dado.getPredicate().toString()
								.equals("FirstLanguage")
						&& !dado.getPredicate().toString()
								.equals("SecondLanguage")) {
					if (dado.getPredicate().equals(dadoIdade.getPredicate())) {
						editTextIdade.setText(dado.getObject().toString());
					}
					if (dado.getPredicate().equals(dadoSexo.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(dado
								.getId());
						radio.setChecked(true);
					}
					if (dado.getPredicate().equals(
							dadoEscolaridade.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(dado.getId());
						radio.setChecked(true);
					}
					if (dado.getPredicate().equals(dadoOcupacao.getPredicate())) {
						editTextOocupacao.setText(dado.getObject().toString());
					}
				}
				if (dado.getPredicate().equals(
						dadoPrimeiraLingua.getPredicate())) {
					spinnerPrimeiraLingua.setSelection(dado.getId(), true);
				}
				if (dado.getPredicate()
						.equals(dadoSegundaLingua.getPredicate())) {
					spinnerSegundaLingua.setSelection(dado.getId(), true);
				}
			}
		}
		dao.close();
	}
}
