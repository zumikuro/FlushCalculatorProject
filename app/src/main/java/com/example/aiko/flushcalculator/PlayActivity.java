package com.example.aiko.flushcalculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_KEY = "key";
    public static final int GAME_MODE_1 = 0;        //2桁4・4
    public static final int GAME_MODE_2 = 1;        //2桁5・５
    public static final int GAME_MODE_3 = 2;        //1桁４・４
    public static final int GAME_MODE_4 = 3;        //1桁5・5

    public static final String KEY_ANSWER = "answer";
    public final String TAG = "Flash";

    private final int QUESTION_NUMBER = 4;
    private final int TIME_MILLIS_PER_Q = 1000;
    private final int TIME_MILLIS_TOTAL = TIME_MILLIS_PER_Q * QUESTION_NUMBER + 100; //4000

    private final int QUESTION_NUMBER_T = 5;
    private final int TIME_MILLIS_PER_QT = 1000;
    private final int TIME_MILLIS_TOTAL_T = QUESTION_NUMBER_T * TIME_MILLIS_PER_QT + 1000; //5000


    private TextView mTextViewFlash;
    private Button mBack;

    private CountDown mStartCountDown;
    private CountDown mCountDown;
    private int[] mQuestions = new int[QUESTION_NUMBER];
    private int[] mQuestions_t = new int[QUESTION_NUMBER_T];
    private int mAnswer = 0;
    private int nAnswer = 0;
    private int mCurrentNum = 0;
    private int nCurrentNum = 0;

    private int gameMode = GAME_MODE_1;

    //Handlerの宣言
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

    /*    final Button next =(Button)findViewById(R.id.button7);
        next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mStartCountDown.start();
            }

                                });*/
        handler = new Handler();

        mBack = (Button) findViewById(R.id.backbt);
        mBack.setVisibility(View.INVISIBLE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SelectActivity.class);
                startActivity(intent);
            }

        });

//        nextBt=(Button)findViewById(R.id.button7);
//        nextBt.setVisibility(View.INVISIBLE);
//        nextBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(getApplication(),PlayActivity.class);
//                intent.putExtra(PlayActivity.EXTRA_KEY, PlayActivity.);
//                startActivity(intent);
//            }
//        });

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        gameMode = intent.getIntExtra(EXTRA_KEY, GAME_MODE_1);

        initViews();

        initQuestions();

        final int countLag = 340;
        mStartCountDown = new CountDown(3000 + countLag, 100);
        mStartCountDown.setOnTickListerner(new CountDown.OnTickListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < countLag) {
                    // start
                } else {
                    millisUntilFinished -= countLag;                    //mill = mill - countLag
                    int s = (int) (millisUntilFinished / 1000) + 1;     //millisUntilFinished→残り時間(ミリ秒)
                    mTextViewFlash.setText(String.valueOf(s));
                    mTextViewFlash.setTextColor(Color.RED);
                }
            }
        });
        mStartCountDown.setOnFinishListener(new CountDown.OnFinishListener() {
            @Override
            public void onFinish() {
                try {
                    //1000ミリ秒Sleepする
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                gameStart();
                mTextViewFlash.setVisibility(View.VISIBLE);
                mTextViewFlash.setTextColor(Color.CYAN);
                mTextViewFlash.setTextSize(120);

            }
        });


        if (gameMode == GAME_MODE_1 || gameMode == GAME_MODE_3) {
            mCountDown = new CountDown(TIME_MILLIS_TOTAL, TIME_MILLIS_PER_Q);
            mCountDown.setOnTickListerner(new CountDown.OnTickListener() {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(TAG, "" + millisUntilFinished);

                    mTextViewFlash.setVisibility(View.INVISIBLE);

                    // Handler に次の処理を登録する。
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 500 ミリ秒 sleep : 止める
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }
                            // そのあとに、 text を見えるようにする。
                            mTextViewFlash.setVisibility(View.VISIBLE);
                        }
                    });

                    showQuestion();
                    nextQuestion();

                }
            });
            mCountDown.setOnFinishListener(new CountDown.OnFinishListener() {
                @Override
                public void onFinish() {
                    try {
                        //1000ミリ秒Sleepする
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    mTextViewFlash.setText(String.valueOf(mAnswer));
                    imageView.setVisibility(View.VISIBLE);
                    mBack.setVisibility(View.VISIBLE);
                    //   nextBt.setVisibility(View.VISIBLE);

                }
            });

            mStartCountDown.start();
        } else if (gameMode == GAME_MODE_2 || gameMode == GAME_MODE_4) {
            mCountDown = new CountDown(TIME_MILLIS_TOTAL_T, TIME_MILLIS_PER_QT);
            mCountDown.setOnTickListerner(new CountDown.OnTickListener() {
                @Override
                public void onTick(long millisUntilFinished) {                  //ここらへんのコードをキレイにしたい
                    Log.d(TAG, "" + millisUntilFinished);

                    mTextViewFlash.setVisibility(View.INVISIBLE);

                    // Handler に次の処理を登録する。
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 500 ミリ秒 sleep : 止める
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }
                            // そのあとに、 text を見えるようにする。
                            mTextViewFlash.setVisibility(View.VISIBLE);
                        }
                    });

                    showQuestionfive();
                    nextQuestionfive();
                }
            });
            mCountDown.setOnFinishListener(new CountDown.OnFinishListener() {
                @Override
                public void onFinish() {
                    try {
                        //1000ミリ秒Sleepする
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    mTextViewFlash.setText(String.valueOf(nAnswer));
                    imageView.setVisibility(View.VISIBLE);
                    mBack.setVisibility(View.VISIBLE);
                    //    nextBt.setVisibility(View.VISIBLE);

                }
            });

            mStartCountDown.start();
        }

    }


    private void gameStart() {
        mCountDown.start();
    }


    private void initViews() {
        mTextViewFlash = (TextView) findViewById(R.id.scoretext);
        // mButtonNext=(Button)findViewById(R.id.button7);
        // mButtonStart=(Button)findViewById(R.id.button3);
    }

    private void initQuestions() {
        Random random = new Random();

        if (gameMode == GAME_MODE_1) {
            /*for (int i = 0; i < QUESTION_NUMBER; i++) {
                mQuestions[i] = random.nextInt(90) + 10;
                mAnswer += mQuestions[i]; */

            for (int i = 0; i < QUESTION_NUMBER; i++) {
                mQuestions[i] = random.nextInt(90) + 10;
                for (int j = 0; j < i; j++) {
                    if (mQuestions[i] == mQuestions[j])
                        i--; // If it is duplicated, create number again
                }

                mAnswer += mQuestions[i];
                Log.d("for", "que = " + mQuestions[i]);
                Log.d("for", "ans : " + mAnswer);
            }

        } else if (gameMode == GAME_MODE_3) {
            for (int i = 0; i < QUESTION_NUMBER; i++) {
                mQuestions[i] = random.nextInt(8) + 1;
                for (int j = 0; j < i; j++) {
                    if (mQuestions[i] == mQuestions[j])
                        i--; // If it is duplicated, create number again
                }

                mAnswer += mQuestions[i];
                Log.d("for", "que = " + mQuestions[i]);
                Log.d("for", "ans : " + mAnswer);
            }
        } else if (gameMode == GAME_MODE_2) {
            for (int i = 0; i < QUESTION_NUMBER_T; i++) {
                mQuestions_t[i] = random.nextInt(89) + 10;
                for (int j = 0; j < i; j++) {
                    if (mQuestions_t[i] == mQuestions_t[j])
                        i--; // If it is duplicated, create number again
                }

                nAnswer += mQuestions_t[i];
                Log.d("for", "que = " + mQuestions_t[i]);
                Log.d("for", "ans : " + nAnswer);

            }
        } else if (gameMode == GAME_MODE_4) {
            for (int i = 0; i < QUESTION_NUMBER_T; i++) {
                mQuestions_t[i] = random.nextInt(8) + 1;
                for (int j = 0; j < i; j++) {
                    if (mQuestions_t[i] == mQuestions_t[j])
                        i--; // If it is duplicated, create number again
                }

                nAnswer += mQuestions_t[i];
                Log.d("for", "que = " + mQuestions_t[i]);
                Log.d("for", "ans : " + nAnswer);
            }
        }
    }

    private void nextQuestion() {
            mCurrentNum++;
        }



    private void nextQuestionfive(){
            nCurrentNum++;

    }

    private void showQuestion() {
      /*  try {
            mTextViewFlash.setVisibility(View.INVISIBLE);
            Thread.sleep(500);
            mTextViewFlash.setVisibility(View.VISIBLE);
        } catch (InterruptedException e) {
        }   */
            if (mCurrentNum < QUESTION_NUMBER) {
                mTextViewFlash.setText(String.valueOf(mQuestions[mCurrentNum]));

        }

    }

    private void showQuestionfive(){
        if(nCurrentNum<QUESTION_NUMBER_T){
            mTextViewFlash.setText(String.valueOf(mQuestions_t[nCurrentNum]));
        }
    }
}





