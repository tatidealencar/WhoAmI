//Tela para seleção das preferências (tipos) de recreação
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

public class PrefRecreation extends Activity {

	private Default interesse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_recreacao);

		//informações padrões do predicate
		interesse = new Default();
		interesse.setRange("yes-no");
		interesse.setObject("yes");
		interesse.setAuxiliary("hasInterest");
		interesse.setSubgroup("Recreation");
		interesse.setGroup("Interest");

		setTitle("Interesses: Recreação");

		carregaInteresses();

		Button botaoConfig = (Button) findViewById(R.id.salvar_pref_recreacao);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(PrefRecreation.this, PrefSports.class);
				startActivity(i);
			}
		});
	}

	//cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickPrefRecreacao(View v) {

		InterestDAO dao = new InterestDAO(PrefRecreation.this);
		boolean checked = ((CheckBox) v).isChecked();

		switch (v.getId()) {
		case R.id.recreacao1:
			interesse.setPredicate("Camping");
			interesse.setId(R.id.recreacao1);
			break;
		case R.id.recreacao2:
			interesse.setPredicate("Pets");
			interesse.setId(R.id.recreacao2);

			break;
		case R.id.recreacao3:
			interesse.setPredicate("Aquariums");
			interesse.setId(R.id.recreacao3);

			break;
		case R.id.recreacao4:
			interesse.setPredicate("Crafts");
			interesse.setId(R.id.recreacao4);

			break;
		case R.id.recreacao5:
			interesse.setPredicate("Toys");
			interesse.setId(R.id.recreacao5);

			break;
		case R.id.recreacao6:
			interesse.setPredicate("Hunting");
			interesse.setId(R.id.recreacao6);

			break;
		case R.id.recreacao7:
			interesse.setPredicate("Hiking");
			interesse.setId(R.id.recreacao7);

			break;
		case R.id.recreacao8:
			interesse.setPredicate("Bicycling");
			interesse.setId(R.id.recreacao8);

			break;
		case R.id.recreacao9:
			interesse.setPredicate("Circus");
			interesse.setId(R.id.recreacao9);

			break;
		case R.id.recreacao10:
			interesse.setPredicate("Collecting");
			interesse.setId(R.id.recreacao10);

			break;
		case R.id.recreacao11:
			interesse.setPredicate("Dance");
			interesse.setId(R.id.recreacao11);

			break;
		case R.id.recreacao12:
			interesse.setPredicate("Puppets");
			interesse.setId(R.id.recreacao12);

			break;
		case R.id.recreacao13:
			interesse.setPredicate("Fairs");
			interesse.setId(R.id.recreacao13);

			break;
		case R.id.recreacao14:
			interesse.setPredicate("Festivals");
			interesse.setId(R.id.recreacao14);

			break;
		case R.id.recreacao15:
			interesse.setPredicate("Movies");
			interesse.setId(R.id.recreacao15);

			break;
		case R.id.recreacao16:
			interesse.setPredicate("Fireworks");
			interesse.setId(R.id.recreacao16);

			break;
		case R.id.recreacao17:
			interesse.setPredicate("Photography");
			interesse.setId(R.id.recreacao17);

			break;
		case R.id.recreacao18:
			interesse.setPredicate("Art Galleries");
			interesse.setId(R.id.recreacao18);

			break;
		case R.id.recreacao19:
			interesse.setPredicate("Humor");
			interesse.setId(R.id.recreacao19);

			break;
		case R.id.recreacao20:
			interesse.setPredicate("Gardening");
			interesse.setId(R.id.recreacao20);

			break;
		case R.id.recreacao21:
			interesse.setPredicate("Games");
			interesse.setId(R.id.recreacao21);

			break;
		case R.id.recreacao22:
			interesse.setPredicate("Fortune-telling");
			interesse.setId(R.id.recreacao22);

			break;
		case R.id.recreacao23:
			interesse.setPredicate("Gambling");
			interesse.setId(R.id.recreacao23);

			break;
		case R.id.recreacao24:
			interesse.setPredicate("Word Games");
			interesse.setId(R.id.recreacao24);

			break;
		case R.id.recreacao25:
			interesse.setPredicate("Books");
			interesse.setId(R.id.recreacao25);

			break;
		case R.id.recreacao26:
			interesse.setPredicate("Lotteries");
			interesse.setId(R.id.recreacao26);

			break;
		case R.id.recreacao27:
			interesse.setPredicate("Juggling");
			interesse.setId(R.id.recreacao27);

			break;
		case R.id.recreacao28:
			interesse.setPredicate("Backpacking");
			interesse.setId(R.id.recreacao28);

			break;
		case R.id.recreacao29:
			interesse.setPredicate("Railroad Models");
			interesse.setId(R.id.recreacao29);

			break;
		case R.id.recreacao30:
			interesse.setPredicate("Roller Coasters");
			interesse.setId(R.id.recreacao30);

			break;
		case R.id.recreacao31:
			interesse.setPredicate("Museums");
			interesse.setId(R.id.recreacao31);

			break;
		case R.id.recreacao32:
			interesse.setPredicate("Knots And Splices");
			interesse.setId(R.id.recreacao32);

			break;
		case R.id.recreacao33:
			interesse.setPredicate("Bird Watching");
			interesse.setId(R.id.recreacao33);

			break;
		case R.id.recreacao34:
			interesse.setPredicate("Crossword Puzzles");
			interesse.setId(R.id.recreacao34);

			break;
		case R.id.recreacao35:
			interesse.setPredicate("Fishing");
			interesse.setId(R.id.recreacao35);

			break;
		case R.id.recreacao36:
			interesse.setPredicate("Kites");
			interesse.setId(R.id.recreacao36);

			break;
		case R.id.recreacao37:
			interesse.setPredicate("Beaches");
			interesse.setId(R.id.recreacao37);

			break;
		case R.id.recreacao38:
			interesse.setPredicate("Jigsaw Puzzles");
			interesse.setId(R.id.recreacao38);

			break;
		case R.id.recreacao39:
			interesse.setPredicate("Radio");
			interesse.setId(R.id.recreacao39);

			break;
		case R.id.recreacao40:
			interesse.setPredicate("Rafting");
			interesse.setId(R.id.recreacao40);

			break;
		case R.id.recreacao41:
			interesse.setPredicate("Television");
			interesse.setId(R.id.recreacao41);

			break;
		case R.id.recreacao42:
			interesse.setPredicate("Card Tricks");
			interesse.setId(R.id.recreacao42);

			break;
		case R.id.recreacao43:
			interesse.setPredicate("Magic tricks");
			interesse.setId(R.id.recreacao43);

			break;
		case R.id.recreacao44:
			interesse.setPredicate("Travel");
			interesse.setId(R.id.recreacao44);

			break;
		case R.id.recreacao45:
			interesse.setPredicate("Zoos");
			interesse.setId(R.id.recreacao45);

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
			i = new Intent(PrefRecreation.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefRecreation.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefRecreation.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaInteresses() {

		InterestDAO dao = new InterestDAO(PrefRecreation.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaInteresses = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaInteresses
					.listIterator(); iterator.hasNext();) {
				Default interesse = iterator.next();
				if (interesse.getSubgroup().equals("Recreation")) {
					CheckBox check = (CheckBox) findViewById(interesse.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}

}
