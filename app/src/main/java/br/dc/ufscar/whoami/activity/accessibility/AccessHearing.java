//Tela para preenchimento das informações relacionadas à capacidade de audição

package br.dc.ufscar.whoami.activity.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.privacysecurity.ExportProfile;
import br.dc.ufscar.whoami.activity.privacysecurity.PrivacyPolicy;
import br.dc.ufscar.whoami.activity.privacysecurity.AccessPassword;
import br.dc.ufscar.whoami.dao.AbilitiesDAO;
import br.dc.ufscar.whoami.model.Default;

public class AccessHearing extends Activity {

	private Default habilidadeAtual; //audição
	private MediaPlayer mMediaPlayer; //miado do gato para testar audição

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acess__audicao);
		setTitle("Configurações de Acessibilidade");

		habilidadeAtual = new Default();

		AudioManager audioManager = (AudioManager) getSystemService(AccessHearing.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

		mMediaPlayer = new MediaPlayer();
		mMediaPlayer = MediaPlayer.create(this, R.raw.song);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setLooping(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMediaPlayer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMediaPlayer.stop();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mMediaPlayer.stop();
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
			i = new Intent(AccessHearing.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.fazer_backup:
			i = new Intent(AccessHearing.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessHearing.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickAudicao(View v) {

		//o atributo object é preenchido de acordo com a seleção do usuário (se ouve ou não o miado do gato)
		switch (v.getId()) {
		case R.id.audicao_sim:
			habilidadeAtual.setObject("yes"); //ouve
			break;

		case R.id.audicao_nao:
			habilidadeAtual.setObject("no"); //não ouve
			break;

		default:
			break;
		}

		//informações padrões do predicate
		habilidadeAtual.setPredicate("AbilityToHear");
		habilidadeAtual.setRange("yes-no");
		habilidadeAtual.setAuxiliary("has");
		habilidadeAtual.setGroup("Abilities");
		habilidadeAtual.setSubgroup("Capabilities");

		//grava informações no BD
		AbilitiesDAO dao = new AbilitiesDAO(AccessHearing.this);

		if (!dao.estaNaLista(habilidadeAtual.getPredicate())) {
			dao.salva(habilidadeAtual);
			mMediaPlayer.stop();
		} else {
			dao.altera(habilidadeAtual);
		}

		dao.close();

		Intent i = new Intent(AccessHearing.this, AccessSpeaks.class);
		startActivity(i);
	}
}