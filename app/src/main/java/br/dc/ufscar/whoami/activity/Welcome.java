//Splash screen

/*Considerar a exclusão dessa tela e a migração das rotinas para a Home*/

package br.dc.ufscar.whoami.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.accessibility.AccessLocomotion;
import br.dc.ufscar.whoami.activity.privacysecurity.Login;
import br.dc.ufscar.whoami.configs.Configs;
import br.dc.ufscar.whoami.utils.Bluetooth;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;
import br.mb.web.expressmessage.ExpressMessage;

public class Welcome extends Activity {

	ExpressMessage expressMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		this.expressMessage = new ExpressMessage(Bluetooth.getMacAddress(), Configs.BASE_URL);
		expressMessage.unlockGuardPolicy();
	}

	private void startup() {

		int DELAY = 4000;

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				Intent intent;

				if (sharedPreferences.loadSavedPreferences("PasswordSet",
						Welcome.this).equals("set")) {
					intent = new Intent(Welcome.this, Login.class);
					startActivity(intent);
				} else {
					if (!sharedPreferences.loadSavedPreferences("FirstAccess",
							Welcome.this).equals("checked")) {

						intent = new Intent(Welcome.this, AccessLocomotion.class);
						startActivity(intent);

					} else {
						intent = new Intent(Welcome.this, Home.class);
						startActivity(intent);
					}
				}

			}
		}, DELAY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		startup();
	}
}
