package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
//import com.google.appengine.repackaged.com.google.common.base.Pair;
import com.udacity.example.jokes.JokeWizard;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import funsolutions.project.lynsychin.myjokes.JokeActivity;


public class MainActivity extends AppCompatActivity {

    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbLoading = findViewById(R.id.progressBar2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        JokeWizard jokeWizard = new JokeWizard();
        //Pair<Context, String> pair = Pair.of(getApplicationContext(), jokeWizard.tellAWizardJoke());
        HashMap<Context, String> improvisedPair = new HashMap<>();
        improvisedPair.put(this, jokeWizard.tellAWizardJoke());
        pbLoading.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(improvisedPair);
    }


    static class EndpointsAsyncTask extends AsyncTask<HashMap<Context, String>, Void, String> {
        private static MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(HashMap<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            Set<Map.Entry<Context, String>> entries = params[0].entrySet();
            context = entries.iterator().next().getKey();
            String name = entries.iterator().next().getValue();

            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Intent takeMeToAJoke = new Intent(context, JokeActivity.class);
            takeMeToAJoke.putExtra(JokeActivity.EXTRA_JOKE, result);
            context.startActivity(takeMeToAJoke);
        }
    }
}
