//Tela para cadastro de senha de acesso (opcional)
package br.dc.ufscar.whoami.activity.privacysecurity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.activity.Settings;
import br.dc.ufscar.whoami.dao.UserDAO;
import br.dc.ufscar.whoami.model.Usuario;
import br.dc.ufscar.whoami.utils.ComputeHashMD5;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class AccessPassword extends Activity {

	private TextView senha;
	private TextView senharepetida;
	private TextView email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.senha_acesso);
		setTitle("Senha de acesso");		

		senha = (TextView) findViewById(R.id.senha);
		senharepetida = (TextView) findViewById(R.id.repertirsenha);
		email = (TextView) findViewById(R.id.email);

		carregaDados();

		Button botaoSalvar = (Button) findViewById(R.id.salvar_senha);
		Button botaoExcluir = (Button) findViewById(R.id.excluir_senha);
		TextView textViewTextoSenha = (TextView) findViewById(R.id.textViewTextoSenha);
		TextView textViewSenha = (TextView) findViewById(R.id.textViewSenha);

		SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();

		if (sharedPreferences.loadSavedPreferences("PasswordSet",
				AccessPassword.this).equals("set")) {
			botaoSalvar.setText("Alterar");
			botaoExcluir.setVisibility(View.VISIBLE);
			textViewTextoSenha.setVisibility(View.GONE);
			textViewSenha.setPadding(0, 40, 0, 0);
			
		} else {
			botaoSalvar.setText("Salvar");
			botaoExcluir.setVisibility(View.GONE);
		}

		botaoSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (senha.length() < 6) {
					Toast.makeText(AccessPassword.this,
							"A senha deve ter no mínimo 6 caracteres",
							Toast.LENGTH_LONG).show();
				} else {
					if (senha.length() > 20) {
						Toast.makeText(AccessPassword.this,
								"A senha deve ter no máximo 20 caracteres",
								Toast.LENGTH_LONG).show();
					} else {
						if (senha.getText().toString()
								.compareTo(senharepetida.getText().toString()) != 0) {
							Toast.makeText(AccessPassword.this,
									"As senhas digitas não são iguais",
									Toast.LENGTH_LONG).show();
						} else {
							if (!validateEmail(email.getText().toString())) {
								Toast.makeText(AccessPassword.this,
										"Informe um e-mail válido",
										Toast.LENGTH_LONG).show();
							} else {
								UserDAO dao = new UserDAO(
										AccessPassword.this);
								Usuario usuario = new Usuario();
								usuario.setEmail(email.getText().toString());
								usuario.setSenha(ComputeHashMD5.compute(senha
										.getText().toString()));

								if (dao.isEmpty()) {
									dao.salvaUsuario(usuario);
									Toast.makeText(AccessPassword.this,
											"Dados salvos com sucesso",
											Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											AccessPassword.this, Login.class);
									startActivity(intent);
								} else {
									dao.alteraUsuario(usuario);
									Toast.makeText(AccessPassword.this,
											"Dados alterados com sucesso",
											Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											AccessPassword.this,
											Settings.class);
									startActivity(intent);
								}

								SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();

								sharedPreferences.savePreferences(
										"PasswordSet", "set", AccessPassword.this);

								dao.close();

							}
						}
					}
				}
			}
		});

		botaoExcluir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserDAO dao = new UserDAO(AccessPassword.this);
				dao.exclui();
				dao.close();
				
				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				sharedPreferences.savePreferences(
						"PasswordSet", "", AccessPassword.this);
				
				Intent i = new Intent(AccessPassword.this, Settings.class);
				startActivity(i);
			}
		});
	}

	public boolean validateEmail(String email) {
		Pattern pattern;
		Matcher matcher;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recursos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.fazer_backup:
			i = new Intent(AccessPassword.this, ExportProfile.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(AccessPassword.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void carregaDados() {

		UserDAO dao = new UserDAO(AccessPassword.this);
		Usuario usuario = new Usuario();
		usuario = dao.getUsuario();

		if (!dao.isEmpty()) {
			email.setText(usuario.getEmail());
		}
		dao.close();
	}
}
