package app;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import utils.Classes;
import utils.Question;
import utils.Topics;
import utils.URLS;
import utils.User;

public class MakeQuestion extends AppCompatActivity {

    private Button submit;
    EditText textQuestion, titleQuestion;
    private SharedPreferences mPreferences;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        submit = (Button) findViewById(R.id.submitQuestion);
        titleQuestion = (EditText) findViewById(R.id.titleQuestion);
        textQuestion = (EditText) findViewById(R.id.enterQuestion);

        final String inputTitle = titleQuestion.getText().toString(); //question title
        final String inputDetails = textQuestion.getText().toString(); //question details

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        Bundle bundle = getIntent().getExtras();
        final String topicID = bundle.getString("topicsID");
        final Topics topic = currentUser.getSingleTopic(topicID);

        // Click the submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question temp=new Question();

                //current user's id
                temp.setOwnerID(currentUser.getId());
                //current username
                temp.setQuestionUsername(currentUser.getUsername());
                //topic this should fall under
                temp.setParent(topic);
                temp.setQuestionTitle(inputTitle);
                temp.setQuestionDescription(inputDetails);
                temp.add_question_to_database();
            }
        });

    }
}