package ca.uoit.dmancini.lab5;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GPLListener{

    private final String LICENSE_LOOKUP_URL = "https://www.gnu.org/licenses/gpl.txt";
    public static final String LICENSE = "ca.uoit.dmancini.lab5.LICENSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.license_agreement);
        SpannableString content = new SpannableString(getString(R.string.license_agreement));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLicenseAgreement(view);
            }
        });
        textView.setText(content);
    }

    public void viewLicenseAgreement(View view) {
        DownloadTask task = new DownloadTask(this);
        task.execute(LICENSE_LOOKUP_URL);
        Log.i("AsyncDemo", "pulling license");
    }

    @Override
    public void handleLicense(String license) {
        Log.i("AsyncDemo", "got the license:" + license);
        // update the UI
        Intent intent = new Intent(this, ShowLicense.class);
        intent.putExtra(LICENSE, license);
        startActivity(intent);
    }

    class DownloadTask extends AsyncTask<String, Void, String> {
        private String licenseText = "";
        private Exception exception = null;
        private GPLListener listener = null;

        public DownloadTask(GPLListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // download the XML data from the service
                URL url = new URL(params[0]);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = null;

                while ((line = in.readLine()) != null) {
                    //get lines
                    licenseText+=line + "\n";
                }
                in.close();

                // remember the data
                return licenseText;
            } catch (Exception e) {
                exception = e;
                licenseText = "";
            } finally {
                return licenseText;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // handle any error
            if (exception != null) {
                exception.printStackTrace();
                return;
            }

            // show the data
            listener.handleLicense(result);
        }
    }
}
