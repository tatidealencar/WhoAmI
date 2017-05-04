//Tela inicial do aplicativo, indicação do estado emocional e botão para acesso às configurações

/*Precisa implementar o envio da política de privacidade via ExpressMessage (Marcelo)*/

package br.dc.ufscar.whoami.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.dao.EstadoEmocionalDAO;
import br.dc.ufscar.whoami.model.EmotionalState;
import br.dc.ufscar.whoami.services.PostUserProfileService;
import br.dc.ufscar.whoami.utils.Bluetooth;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;
import br.mb.web.expressmessage.ExpressMessage;

/*Bibliotecas a serem importadas caso o Thing Broker seja utilizado como serviço de mensageria*/
//import br.dc.ufscar.whoami.privacypolicy.PrivacyPolicyCreator;
//import br.ufscar.dc.thingbroker.interfaces.ResponseError;
//import br.ufscar.dc.thingbroker.model.Thing;
//import br.ufscar.dc.thingbroker.services.ThingService;
//import br.ufscar.dc.thingbroker.services.impl.ThingServiceImpl;

//import com.android.volley.Response.Listener;

public class Home extends FragmentActivity{

	/*Atributos a serem declaracos caso o Thing Broker seja utilizado como serviço de mensageria*/
	//private ThingService thingService;
	// private BluetoothHandler btHandler;

	private Bluetooth bluetooth;

	private EmotionalState estadoEmocionalClicado;
	private ImageButton botaoFeliz;
	private ImageButton botaoBravo;
	private ImageButton botaoAnimado;
	private ImageButton botaoEntediado;
	private ImageButton botaoTriste;
	private ImageButton botaoFrustrado;
	private ImageButton botaoRelaxado;
	private ImageButton botaoSonolento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		
		// desbloqueia o servico de HTTP
		ExpressMessage.unlockGuardPolicy();

		bluetooth = new Bluetooth();
		bluetooth.verificaBluetooth(this);

		/*Trecho de código para utilização do Thing Broker seja utilizado como serviço de mensageria*/
		// Thing Broker
		//this.thingService = new ThingServiceImpl(Configs.BASE_URL, null, this);

		//PrivacyPolicyCreator privacyPolicy = new PrivacyPolicyCreator();

		//Thing thing = new Thing(Bluetooth.getMacAddress());
		//thing.setMetadata(privacyPolicy.getPrivacyPolicy());

		/*
		 * thingService.deleteThing(thing, new Listener<Thing>() {
		 * 
		 * @Override public void onResponse(Thing arg0) {
		 * System.out.println("Criou"); } }, new ResponseError() {
		 * 
		 * @Override public void onExceptionResponse(Exception ex) { //
		 * System.out.println("Deu pau: " + ex.getMessage()); }
		 * 
		 * });
		 */

		/*thingService.createThing(thing, new Listener<Thing>() {

			@Override
			public void onResponse(Thing arg0) {
				System.out.println("Criou");
			}
		}, new ResponseError() {

			@Override
			public void onExceptionResponse(Exception ex) { //
				System.out.println("Deu pau: " + ex.getMessage());
			}

		});*/

		Intent serviceIntent = new Intent(this, PostUserProfileService.class);		
		this.startService(serviceIntent);		

		// SharedPreferences
		SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();

		if (!sharedPreferences.loadSavedPreferences("FirstAccess", this)
				.equals("checked")) {
			sharedPreferences.savePreferences("FirstAccess", "checked", this);
		}

		estadoEmocionalClicado = new EmotionalState();
		botaoFeliz = (ImageButton) findViewById(R.id.botao_feliz);
		botaoBravo = (ImageButton) findViewById(R.id.botao_bravo);
		botaoAnimado = (ImageButton) findViewById(R.id.botao_animado);
		botaoEntediado = (ImageButton) findViewById(R.id.botao_entediado);
		botaoTriste = (ImageButton) findViewById(R.id.botao_triste);
		botaoFrustrado = (ImageButton) findViewById(R.id.botao_frustrado);
		botaoRelaxado = (ImageButton) findViewById(R.id.botao_relaxado);
		botaoSonolento = (ImageButton) findViewById(R.id.botao_sonolento);

		Button botaoConfig = (Button) findViewById(R.id.config);
		botaoConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Home.this, Settings.class);
				startActivity(i);
			}
		});

		carregaEstadoEmocional();
	}

	/* Excluir
    public void verificaBluetooth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			FragmentManager fm = getSupportFragmentManager();
			ActivateBluetoothDialogFragment alertaBluetooth = new ActivateBluetoothDialogFragment();
			alertaBluetooth.show(fm, "Alerta_Bluetooth");
		}
	}*/

    //cada vez que o usuário clica em um item, seta o predicate e salva no BD
	public void onClickEstadoEmocional(View v) {

		zerarEstados();

		switch (v.getId()) {
		case R.id.botao_feliz:
			estadoEmocionalClicado.setPredicate("Feliz");
			botaoFeliz.setBackgroundResource(R.drawable.feliz_selecionado);
			break;
		case R.id.botao_bravo:
			estadoEmocionalClicado.setPredicate("Bravo");
			botaoBravo.setBackgroundResource(R.drawable.bravo_selecionado);
			break;
		case R.id.botao_animado:
			estadoEmocionalClicado.setPredicate("Animado");
			botaoAnimado.setBackgroundResource(R.drawable.animado_selecionado);
			break;
		case R.id.botao_entediado:
			estadoEmocionalClicado.setPredicate("Entediado");
			botaoEntediado
					.setBackgroundResource(R.drawable.entediado_selecionado);
			break;
		case R.id.botao_triste:
			estadoEmocionalClicado.setPredicate("Triste");
			botaoTriste.setBackgroundResource(R.drawable.triste_selecionado);
			break;
		case R.id.botao_frustrado:
			estadoEmocionalClicado.setPredicate("Frustrado");
			botaoFrustrado
					.setBackgroundResource(R.drawable.frustrado_selecionado);
			break;
		case R.id.botao_relaxado:
			estadoEmocionalClicado.setPredicate("Relaxado");
			botaoRelaxado
					.setBackgroundResource(R.drawable.relaxado_selecionado);
			break;
		case R.id.botao_sonolento:
			estadoEmocionalClicado.setPredicate("Sonolento");
			botaoSonolento
					.setBackgroundResource(R.drawable.sonolento_selecionado);
			break;
		default:
			break;
		}

		Date dateStart = new Date(System.currentTimeMillis());
		long longStart = System.currentTimeMillis();
		long longDurability = 900000; // 15 minutos
		long longEnd = longStart + longDurability;
		Date dateEnd = new Date(longEnd);
		String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S",
				Locale.ENGLISH).format(dateStart);
		String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S",
				Locale.ENGLISH).format(dateEnd);

        //informações padrões do predicate
        estadoEmocionalClicado.setStart(start);
		estadoEmocionalClicado.setEnd(end);
		estadoEmocionalClicado.setDurability("15");
		estadoEmocionalClicado.setGroup("EmotionalState");

		EstadoEmocionalDAO dao = new EstadoEmocionalDAO(Home.this);
		if (dao.isEmpty()) {
			dao.salva(estadoEmocionalClicado);
		} else {
			dao.altera(estadoEmocionalClicado);
		}

		dao.close();
	}

    //Quando o usuário escolhe um novo estado emocional, altera a imagem dos demais para indicar que não estão selecionados
	public void zerarEstados() {
		botaoFeliz.setBackgroundResource(R.drawable.feliz);
		botaoBravo.setBackgroundResource(R.drawable.bravo);
		botaoAnimado.setBackgroundResource(R.drawable.animado);
		botaoEntediado.setBackgroundResource(R.drawable.entediado);
		botaoTriste.setBackgroundResource(R.drawable.triste);
		botaoFrustrado.setBackgroundResource(R.drawable.frustrado);
		botaoRelaxado.setBackgroundResource(R.drawable.relaxado);
		botaoSonolento.setBackgroundResource(R.drawable.sonolento);
	}

    //carrega o estado emocional que está no BD quando o usuário acessa a tela
	public void carregaEstadoEmocional() {
		EmotionalState estadoEmocionalAtual = new EmotionalState();
		EstadoEmocionalDAO dao = new EstadoEmocionalDAO(Home.this);
		estadoEmocionalAtual = dao.get();

		if (!(dao.get() == null)) {
			if (estadoEmocionalAtual.getPredicate().toString().equals("Feliz")) {
				botaoFeliz.setBackgroundResource(R.drawable.feliz_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString().equals("Bravo")) {
				botaoBravo.setBackgroundResource(R.drawable.bravo_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString()
					.equals("Animado")) {
				botaoAnimado
						.setBackgroundResource(R.drawable.animado_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString()
					.equals("Entediado")) {
				botaoEntediado
						.setBackgroundResource(R.drawable.entediado_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString().equals("Triste")) {
				botaoTriste
						.setBackgroundResource(R.drawable.triste_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString()
					.equals("Frustrado")) {
				botaoFrustrado
						.setBackgroundResource(R.drawable.frustrado_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString()
					.equals("Relaxado")) {
				botaoRelaxado
						.setBackgroundResource(R.drawable.relaxado_selecionado);
			}
			if (estadoEmocionalAtual.getPredicate().toString()
					.equals("Sonolento")) {
				botaoSonolento
						.setBackgroundResource(R.drawable.sonolento_selecionado);
			}
		}
		dao.close();
	}
	
}
