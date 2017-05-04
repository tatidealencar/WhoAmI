//Tela para seleção das preferências (tipos) de apresentação da informação
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
import br.dc.ufscar.whoami.activity.PersonalData;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.InterfacePreferencesDAO;
import br.dc.ufscar.whoami.model.Default;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class PrefInformationPresentation extends Activity {

	private Default preferencia; //texto, imagem, gráfico, cor, som, linguagem de sinais

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_apresentacao_info);

		setTitle("Preferências de Interface");

		//informações padrões do predicate
		preferencia = new Default();
		preferencia.setRange("yes-no");
		preferencia.setObject("yes");
		preferencia.setAuxiliary("hasPreference");
		preferencia.setSubgroup("InformationPresentation");
		preferencia.setGroup("InterfacePreferences");

		carregaPrefApresentacaoInfo();

		Button botaoSalvar = (Button) findViewById(R.id.salvar_preferencia_apresentacao_info);
		botaoSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent i;
				
				if (!sharedPreferences.loadSavedPreferences("FirstAccess",
						PrefInformationPresentation.this).equals("checked")) {
					
					i = new Intent(PrefInformationPresentation.this, PersonalData.class);
					//i = new Intent(PrefInformationPresentation.this, PreImport.class);
					startActivity(i);

				} else {
					Toast.makeText(PrefInformationPresentation.this,
							"Configurações salvas", Toast.LENGTH_SHORT).show();

					i = new Intent(PrefInformationPresentation.this,
							PreferencesMain.class);
					startActivity(i);
				}
			}
		});
	}

	public void onClickPrefApresentacaoInfo(View v) {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefInformationPresentation.this);
		boolean checked = ((CheckBox) v).isChecked();

		//o atributo object é preenchido caso o usuário possua a preferência
		switch (v.getId()) {
		case R.id.texto:
			preferencia.setPredicate("Text");
			preferencia.setObject("yes");
			preferencia.setId(R.id.texto);
			break;
		case R.id.imagem:
			preferencia.setPredicate("Image");
			preferencia.setObject("yes");
			preferencia.setId(R.id.imagem);
			break;
		case R.id.grafico:
			preferencia.setPredicate("Graphic");
			preferencia.setObject("yes");
			preferencia.setId(R.id.grafico);
			break;
		case R.id.cor:
			preferencia.setPredicate("Color");
			preferencia.setObject("yes");
			preferencia.setId(R.id.cor);
			break;
		case R.id.som:
			preferencia.setPredicate("Sound");
			preferencia.setObject("yes");
			preferencia.setId(R.id.som);
			break;
		case R.id.linguagem_de_sinais:
			preferencia.setPredicate("SignLanguage");
			preferencia.setObject("yes");
			preferencia.setId(R.id.linguagem_de_sinais);
			break;
		default:
			break;
		}

		//grava informações no BD
		if (checked) {
			if (!dao.estaNaLista(preferencia)) {
				dao.salva(preferencia);
			}
		} else {
			if (dao.estaNaLista(preferencia)) {
				dao.exclui(preferencia);
			}
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
			i = new Intent(PrefInformationPresentation.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefInformationPresentation.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefInformationPresentation.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaPrefApresentacaoInfo() {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefInformationPresentation.this);

		if (!dao.isEmpty()) {
			ArrayList<Default> listaPreferencias = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaPreferencias
					.listIterator(); iterator.hasNext();) {
				Default preferencia = iterator.next();
				
				if (preferencia.getSubgroup().equals("InformationPresentation")) {
					CheckBox check = (CheckBox) findViewById(preferencia
							.getId());
					check.setChecked(true);
				}
			}
		}
		dao.close();
	}

}
