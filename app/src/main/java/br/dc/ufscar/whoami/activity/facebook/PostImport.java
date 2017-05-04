//Tela a ser apresentada após a importação dos dados do Facebook

package br.dc.ufscar.whoami.activity.facebook;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.Home;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PostImport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pos_importacao);

		setTitle("Importação de Dados");

		/* IC Guilherme
		 * Chamar serviço de importação de dados do Facebook que vai retornar um JSON (usar: String json)
		 * O JSON retornado deve estar no mesmo formato do JSON da classe DatabaseToJSONParser */

		//String para teste
		// String json =
		// "{\"Age\": \"26\", \"EducationLevel\": \"higher\", \"Employment\": \"bancária\", \"FirstLanguage\": \"Português\", \"SecondLanguage\": \"English\", \"Gender\": \"female\", \"birthday\": \"26-05-1987\", \"Film\": [ { \"Action\": \"medium\" }, { \"Adventure\": \"low\" }, { \"Animation\": \"high\" }, { \"Comedy\": \"medium\" }, {  \"Cult\": \"medium\" }, { \"Documentary\": \"medium\" }, { \"Drama\": \"high\" }, { \"Fantasy\": \"medium\" }, { \"Foreign\": \"medium\" }, { \"Independent\": \"medium\" }, { \"Romance\": \"medium\" }, { \"ScienceFiction\": \"high\" }, { \"Classics\": \"medium\" } ], \"MusicGenre\": [ { \"Concerts\": \"medium\" }, { \"Eletronic\": \"low\" }, { \"Rock\": \"high\" }, { \"SymphonyOrchestras\": \"medium\" } ], \"Games\": [ { \"Racing\": \"high\" }, { \"Fighting\": \"medium\" } ], \"Recreation\": [ { \"Aquariums\": \"medium\" }, { \"ArtGalleries\": \"high\" }, { \"Books\": \"high\" }, { \"Camping\": \"medium\" }, { \"Movies\": \"high\" }, { \"Museums\": \"medium\" }, { \"Pets\": \"high\" }, { \"Travel\": \"medium\" }, { \"Zoo\": \"medium\" } ], \"Sports\": [ { \"Swimming\": \"yes\" } ] }";

		//String para teste
		String json = "{\"Age\": \"uninformed\", \"EducationLevel\": \"uninformed\", \"Employment\": \"uninformed\", \"FirstLanguage\": \"uninformed\", \"SecondLanguage\": \"uninformed\", \"Gender\": \"uninformed\", \"birthday\": \"uninformed\", \"Film\": [], \"MusicGenre\": [], \"Games\": [], \"Recreation\": [], \"Sports\": [] }";

		String mensagem = new String();

		//Contagem da porcentagem de dados preenchidos no aplicativo por meio da importação de dados do Facebook
		try {
			JSONObject jObj = new JSONObject(json);

			int totalTiposDados = 0;

			if (!jObj.getString("Age").equals("uninformed")) {
				mensagem = mensagem + "* Idade" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if (!jObj.getString("EducationLevel").equals("uninformed")) {
				mensagem = mensagem + "* Escolaridade" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if (!jObj.getString("Employment").equals("uninformed")) {
				mensagem = mensagem + "* Emprego" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if (!jObj.getString("FirstLanguage").equals("uninformed")) {
				mensagem = mensagem + "* Primeiro idioma" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if (!jObj.getString("SecondLanguage").equals("uninformed")) {
				mensagem = mensagem + "* Segundo idioma" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if (!jObj.getString("Gender").equals("uninformed")) {
				mensagem = mensagem + "* Sexo" + "\n\n";
				totalTiposDados = totalTiposDados + 1;
			}

			if ((jObj.getJSONArray("Film").length() != 0)
					|| (jObj.getJSONArray("MusicGenre").length() != 0)
					|| (jObj.getJSONArray("Games").length() != 0)
					|| (jObj.getJSONArray("Recreation").length() != 0)
					|| (jObj.getJSONArray("Sports").length() != 0)) {
				mensagem = mensagem + "* Interesses:\n";
			}

			if ((jObj.getJSONArray("Film").length() != 0)) {
				mensagem = mensagem + "- Gêneros de filmes" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if ((jObj.getJSONArray("MusicGenre").length() != 0)) {
				mensagem = mensagem + "- Gêneros musicais" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if ((jObj.getJSONArray("Games").length() != 0)) {
				mensagem = mensagem + "- Tipos de jogos" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if ((jObj.getJSONArray("Recreation").length() != 0)) {
				mensagem = mensagem + "- Tipos de recreação" + "\n";
				totalTiposDados = totalTiposDados + 1;
			}
			if ((jObj.getJSONArray("Sports").length() != 0)) {
				mensagem = mensagem + "- Tipos de esportes" + "\n\n";
				totalTiposDados = totalTiposDados + 1;
			}

			int percent = (int) (totalTiposDados * 100.0f) / 43;

			Log.i("TOTAL", String.valueOf(totalTiposDados));

			mensagem = mensagem + "Um total de "
					+ String.valueOf(totalTiposDados)
					+ " dados foram importados. Ou seja, "
					+ String.valueOf(percent)
					+ "% do seu perfil já está prenchido!";

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView textViewMsgDadosImportados = (TextView) findViewById(R.id.textViewDadosImportados);
		textViewMsgDadosImportados.setText(mensagem);

		Button botaoContinuar = (Button) findViewById(R.id.continuar_preencher_perfil);
		botaoContinuar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						PostImport.this).equals("checked")) {

					sharedPreferences.savePreferences("FirstAccess", "checked",
							PostImport.this);
				}

				Intent i = new Intent(PostImport.this, Home.class);
				startActivity(i);
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
			i = new Intent(PostImport.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PostImport.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PostImport.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
