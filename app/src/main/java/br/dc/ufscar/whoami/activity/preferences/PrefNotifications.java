//Tela para seleção das preferências (tipos) de notificações
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

public class PrefNotifications extends Activity {

	private Default notificacaoSonora;
	private Default notificacaoVisual;
	private Default avatar;

	private RadioButton radioNotificacaoSonora;
	private RadioButton radioNotificacaoVisual;
	private RadioButton radioAvatarSim;
	private RadioButton radioAvatarNao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_notifications);

		setTitle("Preferências de Interface");

		//informações padrões do predicate SoundNotif
		notificacaoSonora = new Default();
		notificacaoSonora.setPredicate("SoundNotif");
		notificacaoSonora.setRange("uninformed-primary-secondary");
		notificacaoSonora.setAuxiliary("hasPreference");
		notificacaoSonora.setSubgroup("Notifications");
		notificacaoSonora.setGroup("InterfacePreferences");
		notificacaoSonora.setId(R.id.sonora);

		//informações padrões do predicate VisualNotif
		notificacaoVisual = new Default();
		notificacaoVisual.setPredicate("VisualNotif");
		notificacaoVisual.setRange("n/i-primary-secondary");
		notificacaoVisual.setAuxiliary("hasPreference");
		notificacaoVisual.setSubgroup("Notifications");
		notificacaoVisual.setGroup("InterfacePreferences");
		notificacaoVisual.setId(R.id.visual);

		//informações padrões do predicate Avatar
		avatar = new Default();
		avatar.setPredicate("Avatar");
		avatar.setRange("n/i-yes-no");
		avatar.setAuxiliary("hasPreference");
		avatar.setSubgroup("Avatar");
		avatar.setGroup("InterfacePreferences");

		radioNotificacaoSonora = (RadioButton) findViewById(R.id.sonora);
		radioNotificacaoVisual = (RadioButton) findViewById(R.id.visual);
		radioAvatarSim = (RadioButton) findViewById(R.id.avatar_sim);
		radioAvatarNao = (RadioButton) findViewById(R.id.avatar_nao);

		carregaPreferencias();
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
			i = new Intent(PrefNotifications.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(PrefNotifications.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(PrefNotifications.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//o atributo object é preenchido de acordo com a preferência primária e secundária do usuário
	public void onClickSalvaPrefNot(View v) {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefNotifications.this);

		if (radioNotificacaoSonora.isChecked()) {
			notificacaoSonora.setObject("primary");
			notificacaoVisual.setObject("secondary");
		} else {
			if (radioNotificacaoVisual.isChecked()) {
				notificacaoVisual.setObject("primary");
				notificacaoSonora.setObject("secondary");

			} else {
				notificacaoSonora.setObject("n/i");
				notificacaoVisual.setObject("n/i");
			}
		}

		if (!dao.estaNaLista(notificacaoSonora)) {
			dao.salva(notificacaoSonora);
		} else {
			dao.altera(notificacaoSonora);
		}

		if (!dao.estaNaLista(notificacaoVisual)) {
			dao.salva(notificacaoVisual);
		} else {
			dao.altera(notificacaoVisual);
		}

		if (radioAvatarSim.isChecked()) {
			avatar.setObject("yes");
			avatar.setId(R.id.avatar_sim);
		} else {
			if (radioAvatarNao.isChecked()) {
				avatar.setObject("no");
				avatar.setId(R.id.avatar_nao);
			} else {
				avatar.setObject("n/i");
			}
		}

		if (!dao.estaNaLista(avatar)) {
			dao.salva(avatar);
		} else {
			dao.altera(avatar);
		}

		dao.close();

		Intent i = new Intent(PrefNotifications.this, PrefLayout.class);
		startActivity(i);
	}

	//carrega as informações que estão no BD quando o usuário acessa a tela
	public void carregaPreferencias() {

		InterfacePreferencesDAO dao = new InterfacePreferencesDAO(
				PrefNotifications.this);
		if (!dao.isEmpty()) {
			ArrayList<Default> listaPreferencias = new ArrayList<Default>(
					dao.getList());
			for (ListIterator<Default> iterator = listaPreferencias
					.listIterator(); iterator.hasNext();) {
				Default dado = iterator.next();

				if ((dado.getPredicate().equals("SoundNotif"))
						|| (dado.getPredicate().equals("VisualNotif"))
						|| (dado.getPredicate().equals("Avatar"))) {
					if (!dado.getObject().equals("n/i")) {
						if (dado.getPredicate().equals(
								notificacaoSonora.getPredicate())) {
							if (dado.getObject().equals("primary")) {
								radioNotificacaoSonora.setChecked(true);
							} else {
								if (dado.getObject().equals("secondary")) {
									radioNotificacaoVisual.setChecked(true);
								}
							}
						}
						if (dado.getPredicate().equals(avatar.getPredicate())) {
							RadioButton radio = (RadioButton) findViewById(dado
									.getId());
							radio.setChecked(true);
						}
					}
				}
			}
		}
		dao.close();
	}
}
