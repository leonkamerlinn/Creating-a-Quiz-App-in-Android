package com.leon.creatingaquizappinandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mAnswer = Integer.MIN_VALUE;
    private AppCompatButton mSubmitButton;
    private QuestionGenerator mQuestionGenerator;
    private TextView mLable;
    private RadioGroup mRadioGroup;
    private AppCompatRadioButton mFirstRadio;
    private AppCompatRadioButton mSecondRadio;
    private AppCompatRadioButton mThirdRadio;
    private TextView mCorrectTextView;
    private TextView mIncorrectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindViews();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnswer = Integer.MIN_VALUE;
                mRadioGroup.clearCheck();

                if (mAnswer == Integer.MIN_VALUE) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.toast_no_selected), Toast.LENGTH_LONG).show();
                } else {
                    if (mQuestionGenerator.checkAnswer(mAnswer)) {
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.toast_correct_answer), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.toast_incorrect_answer), Toast.LENGTH_LONG).show();
                    }
                    mQuestionGenerator.generate();
                }


                bindViews();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                AppCompatRadioButton checkedRadioButton = radioGroup.findViewById(i);
                if (checkedRadioButton == null)return;

                mAnswer = Integer.valueOf(checkedRadioButton.getText().toString());
            }
        });
    }

    private void bindViews() {
        mLable.setText(mQuestionGenerator.toString());
        List<Integer> answers = mQuestionGenerator.getAnswers();
        mFirstRadio.setText(String.valueOf(answers.get(0)));
        mSecondRadio.setText(String.valueOf(answers.get(1)));
        mThirdRadio.setText(String.valueOf(answers.get(2)));
        mCorrectTextView.setText(String.format("Correct: %d", mQuestionGenerator.getCorrectAnswerCount()));
        mIncorrectTextView.setText(String.format("Incorrect: %d", mQuestionGenerator.getIncorrectAnswerCount()));
    }

    private void init() {
        mCorrectTextView = findViewById(R.id.correct);
        mIncorrectTextView = findViewById(R.id.incorrect);
        mSubmitButton = findViewById(R.id.submit);
        mLable = findViewById(R.id.label);
        mRadioGroup = findViewById(R.id.radio_group);
        mFirstRadio = findViewById(R.id.first);
        mSecondRadio = findViewById(R.id.second);
        mThirdRadio = findViewById(R.id.third);
        mQuestionGenerator = QuestionGenerator.getInstance();
    }


}
