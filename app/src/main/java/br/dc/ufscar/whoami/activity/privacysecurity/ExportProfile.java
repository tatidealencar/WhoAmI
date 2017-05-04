/*Tela que permite ao usuário exportar seu perfil de usuário para um arquivo em formato JSON
	e enviá-lo por e-mail
 */

package br.dc.ufscar.whoami.activity.privacysecurity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import br.dc.ufscar.whoami.parser.DatabaseToJSONParser;
import br.dc.ufscar.whoami.utils.SharedPreferencesUtil;

public class ExportProfile extends Activity {

	private static final String username = "whoami.userprofile@gmail.com";
	private static final String password = "Whoami?app";
	private TextView textViewEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exporta_perfil);
		setTitle("Fazer backup");

		textViewEmail = (TextView) findViewById(R.id.email_exportar);
		TextView label = (TextView) findViewById(R.id.textViewLabelEmail);

		SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
		if (sharedPreferences.loadSavedPreferences("PasswordSet",
				ExportProfile.this).equals("set")) {
			textViewEmail.setVisibility(View.GONE);
			label.setVisibility(View.GONE);
		} else {
			textViewEmail.setVisibility(View.VISIBLE);
			label.setVisibility(View.VISIBLE);
		}

		Button botaoExportar = (Button) findViewById(R.id.exportar);
		botaoExportar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				UserDAO dao = new UserDAO(ExportProfile.this);
				String json = "{\"mac_address\":\"\",\"profile_fields\": [\"Abilities\", \"Demographics\", \"Health\", \"Characteristics\", \"Interest\", \"InterfacePreferences\", \"InterfacePreferences\"]}";
				JSONObject obj = new JSONObject();
				DatabaseToJSONParser parser = new DatabaseToJSONParser(json,
						ExportProfile.this);

				obj = parser.parse();

				String email = new String();

				SharedPreferencesUtil sharedPreferences = new SharedPreferencesUtil();
				if (sharedPreferences.loadSavedPreferences("PasswordSet",
						ExportProfile.this).equals("set")) {
					email = dao.getEmail();
				} else {
					email = textViewEmail.getText().toString();
				}
				if (!validateEmail(email)) {
					Toast.makeText(ExportProfile.this,
							"Informe um e-mail válido", Toast.LENGTH_LONG)
							.show();
				} else {
					String subject = "Who Am I? - Backup do perfil de usuário";

					try {
						sendMail(email, subject,
								"<p>Segue anexo o backup do seu perfil Who Am I?.</p>"
										+ "<p>Atenciosamente,</p>"
										+ "<p>Equipe Who Am I?</p>",
								obj.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

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

	private void sendMail(String email, String subject, String messageBody,
			String attachment) throws IOException {
		Session session = createSessionObject();

		try {
			Message message = createMessage(email, subject, messageBody,
					attachment, session);
			new SendMailTask().execute(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private Message createMessage(String email, String subject,
			String messageBody, String attach, Session session)
			throws MessagingException, IOException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("whoami.userprofile@gmail.com",
				"Who Am I?"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				email, email));
		message.setSubject(subject);
		message.setText(messageBody);

		String path = this.getFilesDir().getAbsolutePath();
		File file = new File(path + "profile.json");
		FileOutputStream stream = new FileOutputStream(file);
		try {
			stream.write(attach.getBytes());
		} finally {
			stream.close();
		}

		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		MimeBodyPart attachPart = new MimeBodyPart();
		
		messageBodyPart.setContent(messageBody, "text/html; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);

		DataSource source = new FileDataSource(file);
		attachPart.setDataHandler(new DataHandler(source));
		attachPart.setFileName("profile.json");
		multipart.addBodyPart(attachPart);

		message.setContent(multipart);

		return message;
	}

	private Session createSessionObject() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		return Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	private class SendMailTask extends AsyncTask<Message, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(ExportProfile.this, "Aguarde",
					"Enviando e-mail", true, false);
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			progressDialog.dismiss();
			Toast.makeText(ExportProfile.this, "E-mail enviado com sucesso!",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(ExportProfile.this, Settings.class);
			startActivity(i);
		}

		@Override
		protected Void doInBackground(Message... messages) {
			try {
				Transport.send(messages[0]);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return null;
		}
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
			i = new Intent(ExportProfile.this, AccessPassword.class);
			startActivity(i);
			break;
		case R.id.politica_privacidade:
			i = new Intent(ExportProfile.this, PrivacyPolicy.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
