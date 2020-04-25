package funsolutions.project.lynsychin.myjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "theJoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView tvJoke = findViewById(R.id.joke);

        if(getIntent().hasExtra(EXTRA_JOKE)){
            if(!getIntent().getStringExtra(EXTRA_JOKE).isEmpty())
                tvJoke.setText(getIntent().getStringExtra(EXTRA_JOKE));
            else
                tvJoke.setText(R.string.default_joke);
        } else {
            tvJoke.setText(R.string.default_joke);
        }
    }
}
