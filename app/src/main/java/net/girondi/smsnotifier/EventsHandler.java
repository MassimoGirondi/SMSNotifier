package net.girondi.smsnotifier;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EventsHandler {

    public EventsHandler()
    {}

    public void handle(Context ctx, String title, String message)
    {

        PreferencesManager pm = new PreferencesManager(ctx);

        Toast.makeText(ctx, title + "\n" + message, Toast.LENGTH_LONG).show();


        SendMail sm = new SendMail(ctx,
                pm.get("sender"), pm.get("recipient"),
                title, message,
                pm.get("server"),
                pm.get("password"),
                Integer.parseInt(pm.get("port")));


        sm.execute();

    }
}



class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;


    private String subject;
    private String message;
    private String recipient;
    private String sender;
    private int port;
    private String password;
    private String server;


    public SendMail(Context context, String sender, String recipient, String subject, String message, String server, String password, int port){
        //Initializing variables
        this.context = context;
        this.subject = subject;
        this.message = message;
        this.port=port;
        this.server=server;
        this.recipient=recipient;
        this.sender=sender;
        this.password=password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.i("sender", sender);
        Log.i("recipient", recipient);
        Log.i("server", server);
        Log.i("port", port+"");
        Log.i("password", password);
        Log.i("message", message);


        //Creating properties
        Properties props = new Properties();

        props.put("mail.smtp.host", server);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        //TODO: put these as another input in the activity
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(sender));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            //Sending email
            Transport.send(mm);
            Log.i("email", "sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
