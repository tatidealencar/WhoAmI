//Tela para seleção das preferências relacionadas à saúde
package br.dc.ufscar.whoami.activity.preferences;

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
import android.widget.RadioButton;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.HealthDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PrefHealth extends Activity {

	private Default pressaoArterial;
	private Default diabetes;
	private Default abo;
	private Default fatorRh;
	private Default vegetarianismo;

	private RadioButton radioPressaoNormal;
	private RadioButton radioPressaoBaixa;
	private RadioButton radioPressaoAlta;
	private RadioButton radioDiabetesSim;
	private RadioButton radioDiabetesNao;
	private RadioButton radioTipoA;
	private RadioButton radioTipoB;
	private RadioButton radioTipoAB;
	private RadioButton radioTipoO;
	private RadioButton radioRhPositivo;
	private RadioButton radioRhNegativo;
	private RadioButton radioNaoVegetariano;
	private RadioButton radioOvoVegetarianismo;
	private RadioButton radioLactoVegetarianismo;
	private RadioButton radioOvoLactoVegetarianismo;
	private RadioButton radioSemiEstrito;
	private RadioButton radioEstrito;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_saude);

		setTitle("Nutrição e Saúde");

		//informações padrões do predicate BloodPressure
		pressaoArterial = new Default();
		pressaoArterial.setPredicate("BloodPressure");
		pressaoArterial.setRange("uninformed-low-normal-high");
		pressaoArterial.setAuxiliary("has");
		pressaoArterial.setGroup("Nutrition");

		//informações padrões do predicate Diabetes
		diabetes = new Default();
		diabetes.setPredicate("Diabetes");
		diabetes.setRange("uninformed-yes-no");
		diabetes.setAuxiliary("has");
		diabetes.setGroup("Nutrition");

		//informações padrões do predicate ABO
		abo = new Default();
		abo.setPredicate("ABO");
		abo.setRange("uninformed-A-B-AB-O");
		abo.setAuxiliary("has");
		abo.setSubgroup("BloodType");
		abo.setGroup("Nutrition");

		//informações padrões do predicate RhFactor
		fatorRh = new Default();
		fatorRh.setPredicate("RhFactor");
		fatorRh.setRange("uninformed-positive-negative");
		fatorRh.setAuxiliary("has");
		abo.setSubgroup("BloodType");
		fatorRh.setGroup("Nutrition");

		//informações padrões do predicate Vegetarian
		vegetarianismo = new Default();
		vegetarianismo.setPredicate("Vegetarian");
		vegetarianismo.setRange("uninformed-none-ovo-lacto-ovolacto-semistrict-strict");
		vegetarianismo.setAuxiliary("hasPreference");
		vegetarianismo.setGroup("Nutrition");

		radioPressaoNormal = (RadioButton) findViewById(R.id.pressao_normal);
		radioPressaoBaixa = (RadioButton) findViewById(R.id.pressao_baixa);
		radioPressaoAlta = (RadioButton) findViewById(R.id.pressao_alta);

		radioDiabetesSim = (RadioButton) findViewById(R.id.diabetes_sim);
		radioDiabetesNao = (RadioButton) findViewById(R.id.diabetes_nao);

		radioTipoA = (RadioButton) findViewById(R.id.tipo_a);
		radioTipoB = (RadioButton) findViewById(R.id.tipo_b);
		radioTipoAB = (RadioButton) findViewById(R.id.tipo_ab);
		radioTipoO = (RadioButton) findViewById(R.id.tipo_o);

		radioRhPositivo = (RadioButton) findViewById(R.id.rh_positivo);
		radioRhNegativo = (RadioButton) findViewById(R.id.rh_negativo);

		radioNaoVegetariano = (RadioButton) findViewById(R.id.nenhum_vegetarianismo);
		radioOvoVegetarianismo = (RadioButton) findViewById(R.id.ovo_vegetarianismo);
		radioLactoVegetarianismo = (RadioButton) findViewById(R.id.lacto_vegetarianismo);
		radioOvoLactoVegetarianismo = (RadioButton) findViewById(R.id.ovo_lacto_vegetarianismo);
		radioSemiEstrito = (RadioButton) findViewById(R.id.semiestrito);
		radioEstrito = (RadioButton) findViewById(R.id.estrito);

		carregaPrefSaude();

		//o atributo object é preenchido de acordo com a preferência do usuário
		Button botaoSalvar = (Button) findViewById(R.id.salvar_vegetarianismo);
		botaoSalvar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				HealthDAO dao = new HealthDAO(PrefHealth.this);

				// Pressão arterial
				if (radioPressaoNormal.isChecked()) {
					pressaoArterial.setObject("normal");
					pressaoArterial.setId(R.id.pressao_normal);
				} else {
					if (radioPressaoBaixa.isChecked()) {
						pressaoArterial.setObject("low");
						pressaoArterial.setId(R.id.pressao_baixa);
					} else {
						if (radioPressaoAlta.isChecked()) {
							pressaoArterial.setObject("high");
							pressaoArterial.setId(R.id.pressao_alta);
						} else {
							pressaoArterial.setObject("uninformed");
						}
					}
				}
				if (!dao.estaNaLista(pressaoArterial.getPredicate())) {
					dao.salva(pressaoArterial);
				} else {
					dao.altera(pressaoArterial);
				}

				// Diabetes
				if (radioDiabetesSim.isChecked()) {
					diabetes.setObject("yes");
					diabetes.setId(R.id.diabetes_sim);
				} else {
					if (radioDiabetesNao.isChecked()) {
						diabetes.setObject("no");
						diabetes.setId(R.id.diabetes_nao);
					} else {
						diabetes.setObject("uninformed");
					}

				}
				if (!dao.estaNaLista(diabetes.getPredicate())) {
					dao.salva(diabetes);
				} else {
					dao.altera(diabetes);
				}

				// ABO
				if (radioTipoA.isChecked()) {
					abo.setObject("A");
					abo.setId(R.id.tipo_a);
				} else {
					if (radioTipoB.isChecked()) {
						abo.setObject("B");
						abo.setId(R.id.tipo_b);
					} else {
						if (radioTipoAB.isChecked()) {
							abo.setObject("AB");
							abo.setId(R.id.tipo_ab);
						} else {
							if (radioTipoO.isChecked()) {
								abo.setObject("O");
								abo.setId(R.id.tipo_o);
							} else {
								abo.setObject("uninformed");
							}
						}
					}
				}
				if (!dao.estaNaLista(abo.getPredicate())) {
					dao.salva(abo);
				} else {
					dao.altera(abo);
				}

				// Fator Rh
				if (radioRhPositivo.isChecked()) {
					fatorRh.setObject("positive");
					fatorRh.setId(R.id.rh_positivo);
				} else {
					if (radioRhNegativo.isChecked()) {
						fatorRh.setObject("negative");
						fatorRh.setId(R.id.rh_negativo);
					} else {
						fatorRh.setObject("uninformed");
					}
				}
				if (!dao.estaNaLista(fatorRh.getPredicate())) {
					dao.salva(fatorRh);
				} else {
					dao.altera(fatorRh);
				}

				// Vegetarianismo
				if (radioNaoVegetariano.isChecked()) {
					vegetarianismo.setObject("none");
					vegetarianismo.setId(R.id.nenhum_vegetarianismo);
				} else {
					if (radioOvoVegetarianismo.isChecked()) {
						vegetarianismo.setObject("ovo");
						vegetarianismo.setId(R.id.ovo_vegetarianismo);
					} else {
						if (radioLactoVegetarianismo.isChecked()) {
							vegetarianismo.setObject("lacto");
							vegetarianismo.setId(R.id.ovo_lacto_vegetarianismo);
						} else {
							if (radioOvoLactoVegetarianismo.isChecked()) {
								vegetarianismo.setObject("ovolacto");
								vegetarianismo
										.setId(R.id.ovo_lacto_vegetarianismo);
							} else {
								if (radioSemiEstrito.isChecked()) {
									vegetarianismo.setObject("semistrict");
									vegetarianismo.setId(R.id.semiestrito);
								} else {
									if (radioEstrito.isChecked()) {
										vegetarianismo.setObject("strict");
										vegetarianismo.setId(R.id.estrito);
									} else {
										vegetarianismo.setObject("uninformed");
									}
								}
							}
						}
					}
				}

				if (!dao.estaNaLista(vegetarianismo.getPredicate())) {
					dao.salva(vegetarianismo);
				} else {
					dao.altera(vegetarianismo);
				}

				dao.close();
				
				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;
				
				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						PrefHealth.this).equals("checked")) {
					i = new Intent(PrefHealth.this, PrefNotifications.class);
					startActivity(i);

				} else {
					Toast.makeText(PrefHealth.this, "Configurações salvas",
							Toast.LENGTH_SHORT).show();

					i = new Intent(PrefHealth.this, PreferencesMain.class);
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
			i = new Intent(PrefHealth.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefHealth.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefHealth.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaPrefSaude() {

		HealthDAO dao = new HealthDAO(PrefHealth.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaPrefNutricao = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaPrefNutricao
					.listIterator(); iterator.hasNext();) {
				Default preferencia = iterator.next();
				if (!preferencia.getObject().toString().equals("uninformed")) {

					if (preferencia.getPredicate().equals(
							pressaoArterial.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(preferencia
								.getId());
						radio.setChecked(true);
					}
					if (preferencia.getPredicate().equals(
							diabetes.getPredicate())) {

						RadioButton radio = (RadioButton) findViewById(preferencia
								.getId());
						radio.setChecked(true);
					}
					if (preferencia.getPredicate().equals(abo.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(preferencia
								.getId());
						radio.setChecked(true);
					}
					if (preferencia.getPredicate().equals(
							fatorRh.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(preferencia
								.getId());
						radio.setChecked(true);
					}
					if (preferencia.getPredicate().equals(
							vegetarianismo.getPredicate())) {
						RadioButton radio = (RadioButton) findViewById(preferencia
								.getId());
						radio.setChecked(true);
					}
				}
			}

			dao.close();
		}
	}

}
