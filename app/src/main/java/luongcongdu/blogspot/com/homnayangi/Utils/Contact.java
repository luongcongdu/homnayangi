package luongcongdu.blogspot.com.homnayangi.Utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Admin on 3/1/2018.
 */

public class Contact {
    Context context;

    public Contact(Context context) {
        this.context = context;
    }

    public void Contact(String emailTo, String subject, String hint) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/html");
        String[] emailToDev = new String[]{emailTo};
        intent.putExtra("android.intent.extra.EMAIL", emailToDev);
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.TEXT", hint);
        this.context.startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
