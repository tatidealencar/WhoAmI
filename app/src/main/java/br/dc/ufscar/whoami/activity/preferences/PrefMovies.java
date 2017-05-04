//Tela para seleção das preferências (tipos) de filmes
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

public class PrefMovies extends Activity {

	private Default interesse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_filmes);

		//informações padrões do predicate
		interesse = new Default();
		interesse.setRange("yes-no");
		interesse.setObject("yes");
		interesse.setAuxiliary("hasInterest");
		interesse.setSubgroup("Film");
		interesse.setGroup("Interest");

		setTitle("Interesses: Filmes");

		carregaInteresses();

		Button botaoConfig = (Button) findViewById(R.id.salvar_pref_filmes);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PrefMovies.this, PrefMusicalGenre.class);
				startActivity(i);
			}
		});
	}

	//cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickPrefFilmes(View v) {

		InterestDAO dao = new InterestDAO(PrefMovies.this);
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.filme1:
			interesse.setPredicate("Action");
			interesse.setId(R.id.filme1);
			break;
		case R.id.filme2:
			interesse.setPredicate("Animation");
			interesse.setId(R.id.filme2);
			break;
		case R.id.filme3:
			interesse.setPredicate("Martial Arts");
			interesse.setId(R.id.filme3);
			break;
		case R.id.filme4:
			interesse.setPredicate("Adventure");
			interesse.setId(R.id.filme4);
			break;
		case R.id.filme5:
			interesse.setPredicate("Classics");
			interesse.setId(R.id.filme5);
			break;
		case R.id.filme6:
			interesse.setPredicate("Comedy");
			interesse.setId(R.id.filme6);
			break;
		case R.id.filme7:
			interesse.setPredicate("Children And Family");
			interesse.setId(R.id.filme7);
			break;
		case R.id.filme8:
			interesse.setPredicate("Crime");
			interesse.setId(R.id.filme8);
			break;
		case R.id.filme9:
			interesse.setPredicate("Cult");
			interesse.setId(R.id.filme9);
			break;
		case R.id.filme10:
			interesse.setPredicate("Documentary");
			interesse.setId(R.id.filme10);
			break;
		case R.id.filme11:
			interesse.setPredicate("Drama");
			interesse.setId(R.id.filme11);
			break;
		case R.id.filme12:
			interesse.setPredicate("Sports And Exercise");
			interesse.setId(R.id.filme12);
			break;
		case R.id.filme13:
			interesse.setPredicate("Foreign");
			interesse.setId(R.id.filme13);
			break;
		case R.id.filme14:
			interesse.setPredicate("Fantasy");
			interesse.setId(R.id.filme14);
			break;
		case R.id.filme15:
			interesse.setPredicate("Science Fiction");
			interesse.setId(R.id.filme15);
			break;
		case R.id.filme16:
			interesse.setPredicate("Westerns");
			interesse.setId(R.id.filme16);
			break;
		case R.id.filme17:
			interesse.setPredicate("Television");
			interesse.setId(R.id.filme17);
			break;
		case R.id.filme18:
			interesse.setPredicate("War");
			interesse.setId(R.id.filme18);
			break;
		case R.id.filme19:
			interesse.setPredicate("Mystery");
			interesse.setId(R.id.filme19);
			break;
		case R.id.filme20:
			interesse.setPredicate("Music And Concert");
			interesse.setId(R.id.filme20);
			break;
		case R.id.filme21:
			interesse.setPredicate("Musicals");
			interesse.setId(R.id.filme21);
			break;
		case R.id.filme22:
			interesse.setPredicate("Soaps");
			interesse.setId(R.id.filme22);
			break;
		case R.id.filme23:
			interesse.setPredicate("Romance");
			interesse.setId(R.id.filme23);
			break;
		case R.id.filme24:
			interesse.setPredicate("Thriller");
			interesse.setId(R.id.filme24);
			break;
		case R.id.filme25:
			interesse.setPredicate("Horror");
			interesse.setId(R.id.filme25);
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
			i = new Intent(PrefMovies.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefMovies.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefMovies.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInteresses() {

		InterestDAO dao = new InterestDAO(PrefMovies.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaInteresses = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaInteresses
					.listIterator(); iterator.hasNext();) {
				Default interesse = iterator.next();
				if (interesse.getSubgroup().equals("Film")) {
					CheckBox check = (CheckBox) findViewById(interesse.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}
}
