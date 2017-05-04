/* Serviço a ser inicializado quando o aplicativo é aberto
Verifica se há requisições de envio de perfil de usuário
Quando recebe uma requisição, envia o perfil de usuário para o requerente, após a autorização do usuário */

package br.dc.ufscar.whoami.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import br.dc.ufscar.whoami.configs.Configs;
import br.dc.ufscar.whoami.parser.DatabaseToJSONParser;
import br.dc.ufscar.whoami.utils.Bluetooth;
import br.mb.web.expressmessage.ExpressMessage;

public class PostUserProfileService extends Service {

	public static JSONObject json = null;
	private Context context;
	private ExpressMessage expressMessage; //serviço de mensageria

	public void onCreate() {
		context = this;
		this.expressMessage = new ExpressMessage(Bluetooth.getMacAddress(), Configs.BASE_URL);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		while (true) {
			try {
				new DoBackgroundTask().execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return START_NOT_STICKY;
		}
	}

	private class DoBackgroundTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			ExpressMessage.unlockGuardPolicy();

            //Verifica se há requisições de sistemas solicitando o perfil do usuário
            PostUserProfileService.json = expressMessage.getJSON();

			if(PostUserProfileService.json != null)
				return "OK";
			else
				return "Deu ruim";
		}

		protected void onPostExecute(String result) {

			new Handler().postDelayed(new Runnable() {
				public void run() {
					try {
						new DoBackgroundTask().execute();
						
						/*Após receber uma requisição, extrai a string "dataContent"
                        * Extrai a string "info" da string "dataContent"
                        * A string info contém os campos do perfil de usuário solicitados
                        * Chama o método sendProfile */
                        if(PostUserProfileService.json != null) {
			                Log.i("TESTE", json.toString());
			                try {
			                	JSONObject dataContent = new JSONObject(PostUserProfileService.json.getString("dataContent").toString());
			                    JSONObject info = new JSONObject(dataContent.getString("info").toString());

			                    try {
									sendProfile(info, json.getString("senderAddress"));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 2000);

		}
	}

    //Envia o perfil de usuário preenchido com os dados solicitados para o requerente (receiverAddress)
	private void sendProfile(final JSONObject jObjInfo, String receiverAddress) throws JSONException {
		Log.i("<JSON>",jObjInfo.toString());


        String appName = jObjInfo.getString("app_name");
		final String macAddress = receiverAddress;

        //Solicita ao usuário autorização para enviar seus dados para o requerente
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Autorização de envio de perfil");
		builder.setMessage("Você autoriza o envio de dados do seu perfil para a aplicação "
				+ appName + "?");

		//Caso o usuário autorize, recupera o perfil de usuáirio do BD por meio do parser DatabaseToJSONParser
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseToJSONParser parser = new DatabaseToJSONParser(jObjInfo
						.toString(), context);

                //Chama o método post do serviço ExpressMessage para enviar o perfil de usuário para o requerente
				expressMessage.post(macAddress, parser.parse().toString());
			}
		});

        //Caso o usuário não autorize, fecha o dialog
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});

		AlertDialog dialog = builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		
		PostUserProfileService.json = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
