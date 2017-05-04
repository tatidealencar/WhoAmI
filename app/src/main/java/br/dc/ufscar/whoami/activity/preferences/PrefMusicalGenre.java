//Tela para seleção das preferências (tipos) de gêneros musicais
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

public class PrefMusicalGenre extends Activity {

	private Default interesse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_gen_musical);

		//informações padrões do predicate
		interesse = new Default();
		interesse.setRange("yes-no");
		interesse.setObject("yes");
		interesse.setAuxiliary("hasInterest");
		interesse.setSubgroup("MusicGenre");
		interesse.setGroup("Interest");

		setTitle("Interesses: Gênero Musical");

		carregaInteresses();

		Button botaoConfig = (Button) findViewById(R.id.salvar_pref_musicas);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PrefMusicalGenre.this, PrefGames.class);
				startActivity(i);
			}
		});
	}

	//cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickPrefGenMusical(View v) {

		InterestDAO dao = new InterestDAO(PrefMusicalGenre.this);
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.musica1:
			interesse.setPredicate("Blues");
			interesse.setId(R.id.musica1);
			break;
		case R.id.musica2:
			interesse.setPredicate("Celtic");
			interesse.setId(R.id.musica2);

			break;
		case R.id.musica3:
			interesse.setPredicate("Classical");
			interesse.setId(R.id.musica3);

			break;
		case R.id.musica4:
			interesse.setPredicate("Concerts");
			interesse.setId(R.id.musica4);

			break;
		case R.id.musica5:
			interesse.setPredicate("Choral Music");
			interesse.setId(R.id.musica5);

			break;
		case R.id.musica6:
			interesse.setPredicate("Country");
			interesse.setId(R.id.musica6);

			break;
		case R.id.musica7:
			interesse.setPredicate("Dance");
			interesse.setId(R.id.musica7);

			break;
		case R.id.musica8:
			interesse.setPredicate("Eletronic");
			interesse.setId(R.id.musica8);

			break;
		case R.id.musica9:
			interesse.setPredicate("World Music");
			interesse.setId(R.id.musica9);

			break;
		case R.id.musica10:
			interesse.setPredicate("Folk");
			interesse.setId(R.id.musica10);

			break;
		case R.id.musica11:
			interesse.setPredicate("Religion");
			interesse.setId(R.id.musica11);

			break;
		case R.id.musica12:
			interesse.setPredicate("Heavy Metal");
			interesse.setId(R.id.musica12);

			break;
		case R.id.musica13:
			interesse.setPredicate("Hymns");
			interesse.setId(R.id.musica13);

			break;
		case R.id.musica14:
			interesse.setPredicate("Hip-Hop");
			interesse.setId(R.id.musica14);

			break;
		case R.id.musica15:
			interesse.setPredicate("Humor And Comedy");
			interesse.setId(R.id.musica15);

			break;
		case R.id.musica16:
			interesse.setPredicate("Indian");
			interesse.setId(R.id.musica16);

			break;
		case R.id.musica17:
			interesse.setPredicate("Improvisation");
			interesse.setId(R.id.musica17);

			break;
		case R.id.musica18:
			interesse.setPredicate("Jazz");
			interesse.setId(R.id.musica18);

			break;
		case R.id.musica19:
			interesse.setPredicate("Jewish");
			interesse.setId(R.id.musica19);

			break;
		case R.id.musica20:
			interesse.setPredicate("Karaoke");
			interesse.setId(R.id.musica20);

			break;
		case R.id.musica21:
			interesse.setPredicate("Lyrics");
			interesse.setId(R.id.musica21);

			break;
		case R.id.musica22:
			interesse.setPredicate("Composers");
			interesse.setId(R.id.musica22);

			break;
		case R.id.musica23:
			interesse.setPredicate("Musicals");
			interesse.setId(R.id.musica23);

			break;
		case R.id.musica24:
			interesse.setPredicate("Children");
			interesse.setId(R.id.musica24);

			break;
		case R.id.musica25:
			interesse.setPredicate("New Age");
			interesse.setId(R.id.musica25);

			break;
		case R.id.musica26:
			interesse.setPredicate("Opera");
			interesse.setId(R.id.musica26);

			break;
		case R.id.musica27:
			interesse.setPredicate("Symphony Orchestras");
			interesse.setId(R.id.musica27);

			break;
		case R.id.musica28:
			interesse.setPredicate("Popular");
			interesse.setId(R.id.musica28);

			break;
		case R.id.musica29:
			interesse.setPredicate("Psychedelic");
			interesse.setId(R.id.musica29);

			break;
		case R.id.musica30:
			interesse.setPredicate("Punk Rock");
			interesse.setId(R.id.musica30);

			break;
		case R.id.musica31:
			interesse.setPredicate("Quartets");
			interesse.setId(R.id.musica31);

			break;
		case R.id.musica32:
			interesse.setPredicate("Ragtime");
			interesse.setId(R.id.musica32);

			break;
		case R.id.musica33:
			interesse.setPredicate("Rap");
			interesse.setId(R.id.musica33);

			break;
		case R.id.musica34:
			interesse.setPredicate("Reggae");
			interesse.setId(R.id.musica34);

			break;
		case R.id.musica35:
			interesse.setPredicate("Rock");
			interesse.setId(R.id.musica35);

			break;
		case R.id.musica36:
			interesse.setPredicate("Rhythm-n-Blues");
			interesse.setId(R.id.musica36);

			break;
		case R.id.musica37:
			interesse.setPredicate("Western Swing");
			interesse.setId(R.id.musica37);

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
			i = new Intent(PrefMusicalGenre.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefMusicalGenre.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefMusicalGenre.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInteresses() {

		InterestDAO dao = new InterestDAO(PrefMusicalGenre.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaInteresse = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaInteresse.listIterator(); iterator
					.hasNext();) {
				Default interesse = iterator.next();
				if (interesse.getSubgroup().equals("MusicGenre")) {
					CheckBox check = (CheckBox) findViewById(interesse.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}

}
