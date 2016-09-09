package com.geekmode.marvelcomics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView bishopCardTextView = (TextView)findViewById(R.id.bishop_card_text);
        bishopCardTextView.setText(
                "The mutant known only as Bishop was born in the 21st Century A.D. of alternate " +
                        "timeline Earth-1191.\n" +
                        "Bishop can absorb most types of energy, including magic and psychic, " +
                        "directed toward him. The nature of his powers makes it difficult to " +
                        "damage him with energy-based attacks.\n" +
                        "Bishop first appears as a member of Xavier's Security Enforcers " +
                        "(initially called the Xavier School Enforcers), a mutant police force " +
                        "from a dystopian future of the Marvel Universe. He travels to the 20th " +
                        "century and joins the X-Men, a team he knew only as legends."
        );
    }
}
