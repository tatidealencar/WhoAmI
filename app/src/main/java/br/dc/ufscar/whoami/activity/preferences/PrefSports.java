//Tela para seleção das preferências (tipos) de esportes

/*futuramente, mostrar essas informações por categoria, para diminuir a sobrecarga de informações na tela
		(também utilizar imagens para ilustrar as informações)*/

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
import android.widget.CheckBox;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.Home;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.InterestDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PrefSports extends Activity {

	private Default interesse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_esportes);

		setTitle("Interesses: Esportes");

		//informações padrões do predicate
		interesse = new Default();
		interesse.setRange("yes-no");
		interesse.setObject("yes");
		interesse.setAuxiliary("hasInterest");
		interesse.setSubgroup("Sports");
		interesse.setGroup("Interest");

		carregaInteresses();

		Button botaoConfig = (Button) findViewById(R.id.salvar_pref_esportes);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;

				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						PrefSports.this).equals("checked")) {
					
					sharedPreferences.savePreferences("FirstAccess",
							"checked", PrefSports.this);
					
					Toast.makeText(PrefSports.this, "Configurações salvas",
							Toast.LENGTH_SHORT).show();

					i = new Intent(PrefSports.this, Home.class);
					startActivity(i);

				} else {
					Toast.makeText(PrefSports.this, "Configurações salvas",
							Toast.LENGTH_SHORT).show();

					i = new Intent(PrefSports.this, PreferencesMain.class);
					startActivity(i);
				}
			}
		});
	}

    //cada vez que o usuário clica em um item, seta o predicate e salva no BD
    public void onClickPrefEsportes(View v) {

		InterestDAO dao = new InterestDAO(PrefSports.this);
		boolean checked = ((CheckBox) v).isChecked();


		switch (v.getId()) {
		case R.id.esporte1:
			interesse.setPredicate("Athletics");
			interesse.setId(R.id.esporte1);
			break;
		case R.id.esporte2:
			interesse.setPredicate("Automobile Racing");
			interesse.setId(R.id.esporte2);

			break;
		case R.id.esporte3:
			interesse.setPredicate("Badminton");
			interesse.setId(R.id.esporte3);

			break;
		case R.id.esporte4:
			interesse.setPredicate("Basketball");
			interesse.setId(R.id.esporte4);

			break;
		case R.id.esporte5:
			interesse.setPredicate("Baseball");
			interesse.setId(R.id.esporte5);

			break;
		case R.id.esporte6:
			interesse.setPredicate("Boating");
			interesse.setId(R.id.esporte6);

			break;
		case R.id.esporte7:
			interesse.setPredicate("Bowling");
			interesse.setId(R.id.esporte7);

			break;
		case R.id.esporte8:
			interesse.setPredicate("Boxing");
			interesse.setId(R.id.esporte8);

			break;
		case R.id.esporte9:
			interesse.setPredicate("Boomerangs");
			interesse.setId(R.id.esporte9);

			break;
		case R.id.esporte10:
			interesse.setPredicate("Whitewater Kayaking");
			interesse.setId(R.id.esporte10);

			break;
		case R.id.esporte11:
			interesse.setPredicate("Karting");
			interesse.setId(R.id.esporte11);

			break;
		case R.id.esporte12:
			interesse.setPredicate("Cycling");
			interesse.setId(R.id.esporte12);

			break;
		case R.id.esporte13:
			interesse.setPredicate("Running");
			interesse.setId(R.id.esporte13);

			break;
		case R.id.esporte14:
			interesse.setPredicate("Horse Racing");
			interesse.setId(R.id.esporte14);

			break;
		case R.id.esporte15:
			interesse.setPredicate("Sled Dog Racing");
			interesse.setId(R.id.esporte15);

			break;
		case R.id.esporte16:
			interesse.setPredicate("Cricket");
			interesse.setId(R.id.esporte16);

			break;
		case R.id.esporte17:
			interesse.setPredicate("Flying Discs");
			interesse.setId(R.id.esporte17);

			break;
		case R.id.esporte18:
			interesse.setPredicate("Climbing");
			interesse.setId(R.id.esporte18);

			break;
		case R.id.esporte19:
			interesse.setPredicate("Fencing");
			interesse.setId(R.id.esporte19);

			break;
		case R.id.esporte20:
			interesse.setPredicate("Winter Sports");
			interesse.setId(R.id.esporte20);

			break;
		case R.id.esporte21:
			interesse.setPredicate("Skiing");
			interesse.setId(R.id.esporte21);

			break;
		case R.id.esporte22:
			interesse.setPredicate("Cross Country Skiing");
			interesse.setId(R.id.esporte22);

			break;
		case R.id.esporte23:
			interesse.setPredicate("Soccer");
			interesse.setId(R.id.esporte23);

			break;
		case R.id.esporte24:
			interesse.setPredicate("Footbal");
			interesse.setId(R.id.esporte24);

			break;
		case R.id.esporte25:
			interesse.setPredicate("Gymnastics");
			interesse.setId(R.id.esporte25);

			break;
		case R.id.esporte26:
			interesse.setPredicate("Golf");
			interesse.setId(R.id.esporte26);

			break;
		case R.id.esporte27:
			interesse.setPredicate("Hockey");
			interesse.setId(R.id.esporte27);

			break;
		case R.id.esporte28:
			interesse.setPredicate("Field Hockey");
			interesse.setId(R.id.esporte28);

			break;
		case R.id.esporte29:
			interesse.setPredicate("Ice Hockey");
			interesse.setId(R.id.esporte29);

			break;
		case R.id.esporte30:
			interesse.setPredicate("Sailing");
			interesse.setId(R.id.esporte30);

			break;
		case R.id.esporte31:
			interesse.setPredicate("Olympics");
			interesse.setId(R.id.esporte31);

			break;
		case R.id.esporte32:
			interesse.setPredicate("Luge");
			interesse.setId(R.id.esporte32);

			break;
		case R.id.esporte33:
			interesse.setPredicate("Wrestling");
			interesse.setId(R.id.esporte33);

			break;
		case R.id.esporte34:
			interesse.setPredicate("Scuba Diving");
			interesse.setId(R.id.esporte34);

			break;
		case R.id.esporte35:
			interesse.setPredicate("Motorcycles");
			interesse.setId(R.id.esporte35);

			break;
		case R.id.esporte36:
			interesse.setPredicate("Swimming");
			interesse.setId(R.id.esporte36);

			break;
		case R.id.esporte37:
			interesse.setPredicate("Skydiving");
			interesse.setId(R.id.esporte37);

			break;
		case R.id.esporte38:
			interesse.setPredicate("Skating");
			interesse.setId(R.id.esporte38);

			break;
		case R.id.esporte39:
			interesse.setPredicate("Ice Skating");
			interesse.setId(R.id.esporte39);

			break;
		case R.id.esporte40:
			interesse.setPredicate("Rodeos");
			interesse.setId(R.id.esporte40);

			break;
		case R.id.esporte41:
			interesse.setPredicate("Skateboarding");
			interesse.setId(R.id.esporte41);

			break;
		case R.id.esporte42:
			interesse.setPredicate("Snowboarding");
			interesse.setId(R.id.esporte42);

			break;
		case R.id.esporte43:
			interesse.setPredicate("Snowshoeing");
			interesse.setId(R.id.esporte43);

			break;
		case R.id.esporte44:
			interesse.setPredicate("Softball");
			interesse.setId(R.id.esporte44);

			break;
		case R.id.esporte45:
			interesse.setPredicate("Surfing");
			interesse.setId(R.id.esporte45);

			break;
		case R.id.esporte46:
			interesse.setPredicate("Tennis");
			interesse.setId(R.id.esporte46);

			break;
		case R.id.esporte47:
			interesse.setPredicate("Table Tennis");
			interesse.setId(R.id.esporte47);

			break;
		case R.id.esporte48:
			interesse.setPredicate("Triathlon");
			interesse.setId(R.id.esporte48);

			break;
		case R.id.esporte49:
			interesse.setPredicate("Volleyball");
			interesse.setId(R.id.esporte49);

			break;
		case R.id.esporte50:
			interesse.setPredicate("Windsurfing");
			interesse.setId(R.id.esporte50);

			break;
		case R.id.esporte51:
			interesse.setPredicate("Chess");
			interesse.setId(R.id.esporte51);

			break;

		default:
			break;
		}

		if (checked) {
			if (!dao.estaNaLista(interesse.getPredicate())) {
				dao.salva(interesse);
			}
		} else {
			if (dao.estaNaLista(interesse.getPredicate()))
				dao.exclui(interesse);
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
			i = new Intent(PrefSports.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefSports.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefSports.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInteresses() {

		InterestDAO dao = new InterestDAO(PrefSports.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaInteresses = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaInteresses
					.listIterator(); iterator.hasNext();) {
				Default interesse = iterator.next();
				if (interesse.getSubgroup().equals("Sports")) {
					CheckBox check = (CheckBox) findViewById(interesse.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}
}
