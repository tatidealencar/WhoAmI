//Tela para seleção das preferências (tipos) de jogos
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
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.InterestDAO;
import br.dc.ufscar.whoami.model.Default;

public class PrefGames extends Activity {

	private Default interesse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_jogos);

		//informações padrões do predicate
		interesse = new Default();
		interesse.setRange("yes-no");
		interesse.setObject("yes");
		interesse.setAuxiliary("hasInterest");
		interesse.setSubgroup("Games");
		interesse.setGroup("Interest");

		setTitle("Interesses: Jogos");

		carregaInteresses();

		Button botaoConfig = (Button) findViewById(R.id.salvar_pref_jogos);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PrefGames.this, PrefRecreation.class);
				startActivity(i);
			}
		});
	}

	//cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickPrefJogos(View v) {

		InterestDAO dao = new InterestDAO(PrefGames.this);
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.jogo1:
			interesse.setPredicate("Action");
			interesse.setId(R.id.jogo1);
			break;
		case R.id.jogo2:
			interesse.setPredicate("Adventure");
			interesse.setId(R.id.jogo2);

			break;
		case R.id.jogo3:
			interesse.setPredicate("Racing");
			interesse.setId(R.id.jogo3);

			break;
		case R.id.jogo4:
			interesse.setPredicate("Sports");
			interesse.setId(R.id.jogo4);

			break;
		case R.id.jogo5:
			interesse.setPredicate("Strategy");
			interesse.setId(R.id.jogo5);

			break;
		case R.id.jogo6:
			interesse.setPredicate("Arcade");
			interesse.setId(R.id.jogo6);

			break;
		case R.id.jogo7:
			interesse.setPredicate("Platform");
			interesse.setId(R.id.jogo7);

			break;
		case R.id.jogo8:
			interesse.setPredicate("Board Games");
			interesse.setId(R.id.jogo8);

			break;
		case R.id.jogo9:
			interesse.setPredicate("Children");
			interesse.setId(R.id.jogo9);

			break;
		case R.id.jogo10:
			interesse.setPredicate("Fighting");
			interesse.setId(R.id.jogo10);

			break;
		case R.id.jogo11:
			interesse.setPredicate("Puzzle");
			interesse.setId(R.id.jogo11);

			break;
		case R.id.jogo12:
			interesse.setPredicate("Roleplaying");
			interesse.setId(R.id.jogo12);

			break;
		case R.id.jogo13:
			interesse.setPredicate("Simulation");
			interesse.setId(R.id.jogo13);

			break;
		case R.id.jogo14:
			interesse.setPredicate("Shoot-Em-Up");
			interesse.setId(R.id.jogo14);

			break;
		case R.id.jogo15:
			interesse.setPredicate("First-Person-Shooter");
			interesse.setId(R.id.jogo15);

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
			i = new Intent(PrefGames.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefGames.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefGames.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInteresses() {

		InterestDAO dao = new InterestDAO(PrefGames.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaInteresses = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaInteresses
					.listIterator(); iterator.hasNext();) {
				Default interesse = iterator.next();
				if (interesse.getSubgroup().equals("Games")) {
					CheckBox check = (CheckBox) findViewById(interesse.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}

}
