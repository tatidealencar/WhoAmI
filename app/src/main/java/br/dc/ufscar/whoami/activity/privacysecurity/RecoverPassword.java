//Tela que permite ao usuário recuperar a senha cadastrada (uma nova senha é enviada por e-mail)
package br.dc.ufscar.whoami.activity.privacysecurity;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.dc.ufscar.whoami.R;
import br.dc.ufscar.whoami.dao.UserDAO;
import br.dc.ufscar.whoami.utils.ComputeHashMD5;

public class RecoverPassword extends Activity {

	private static final String username = "whoami.userprofile@gmail.com";
	private static final String password = "Whoami?app";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuperar_senha);
		
		setTitle("Recuperação de senha");

		Button botaoSalvar = (Button) findViewById(R.id.enviar_senha);
		botaoSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				UserDAO dao = new UserDAO(RecoverPassword.this);

				String email = dao.getEmail();
				String subject = "Who Am I? - Recuperação de senha";

				sendMail(
						email,
						subject,
						"Segue a nova senha de acesso ao seu perfil:\n\n"
								+ resetPassword()
								+ "\n\nPara a sua maior segurança, troque a senha apó o primeiro acesso.\n\nAtenciosamente,\n\nEquipe Who Am I?");

			}
		});
	}

	public String resetPassword() {
		
		UserDAO dao = new UserDAO(RecoverPassword.this);
		
		String[] newPassword = new String[6];
		Random generator = new Random();
		for (int x = 0; x < 6; x++) {
			char newKey = (char) (generator.nextInt(25) + 97);
			newPassword[x] = String.valueOf(newKey);
		}
		StringBuilder sb = new StringBuilder();
		for (String s : newPassword) {
			sb.append(s);
		}
		
		String password = sb.toString();
		dao.alteraSenha(ComputeHashMD5.compute(password));
		dao.close();
		
		return password;
	}

	private void sendMail(String email, String subject, String messageBody) {
		Session session = createSessionObject();

		try {
			Message message = createMessage(email, subject, messageBody,
					session);
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
			String messageBody, Session session) throws MessagingException,
			UnsupportedEncodingException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("whoami.userprofile@gmail.comm",
				"Who Am I?"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				email, email));
		message.setSubject(subject);
		message.setText(messageBody);
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
			progressDialog = ProgressDialog.show(RecoverPassword.this,
					"Aguarde", "Enviando e-mail", true, false);
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			progressDialog.dismiss();
			Toast.makeText(RecoverPassword.this, "Senha enviada com sucesso!",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(RecoverPassword.this, Login.class);
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
}
