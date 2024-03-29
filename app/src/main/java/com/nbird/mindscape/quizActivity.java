package com.nbird.mindscape;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class quizActivity extends AppCompatActivity {

    Dialog loadingDialog,expert1dialog;

    TextView questionTextView,scoreBoard;
    Button option1,option2,option3,option4,nextButton;
    LinearLayout linearLayout;
    private int count;
    private List<questionHolder> list,listsecondary;
    CountDownTimer countDownTimer;
    private int position=0;
    private int score=0;
     int category;
    private int setNo;
    long milliHolder;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    TextView timerText;
    String imageurl;
    int num=0;
    ImageView expertImage;
    int expertnum=0;
    int swapnum=0;
    int audiencenum=0;

    int manupulator=0;
    int manupulator1=0;
    int fiftyfiftynum=0;

    TextView titleText;
    int yo1;
    int yo2;
    int yo3;
    int yo4;
    String userName;
    int selectNum;
    int lifelineSum=0;

    CardView audienceLL,expertAdviceLL,fiftyfiftyLL,swapTheQuestionLL;
    LinearLayout linearLayoutexpert,linearLayoutAudience,linearLayoutFiftyFifty,linearLayoutSwap;

    int minutes=0;
    int second=0;
    String minutestext;
    String secondtext;
    int mainfinder=0;
    int myPosition=0;
    LottieAnimationView anim11,anim12,anim13,anim14,anim15,anim16,anim17,anim18,anim19,anim20;
    ImageView myPic,speakerImage;
    TextView myName;

    GridView gridView;
    private InterstitialAd mInterstitialAd;

    private void loadAds(){

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    songActivity songActivity;


    public void songStopperAndResumer(){
        final SharedPreferences songStopper = this.getSharedPreferences("SongRemember", 0);
        final SharedPreferences.Editor editorsongStopper = songStopper.edit();

        final Boolean songDetect = songStopper.getBoolean("IsPlaying",true);
        CardView cardViewSpeaker=(CardView) findViewById(R.id.cardViewSpeaker);
        final ImageView speakerImage=(ImageView) findViewById(R.id.speakerImage);
        final LinearLayout Speaker=(LinearLayout) findViewById(R.id.Speaker);
        if(songDetect){
            songActivity=new songActivity(this);
            songActivity.startMusic();
        }else{
            Speaker.setBackgroundResource(R.drawable.usedicon);
            speakerImage.setBackgroundResource(R.drawable.speakeroff);
        }

        cardViewSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean songDetect9 = songStopper.getBoolean("IsPlaying",true);
                if(songDetect9){
                    songActivity.songStop();
                    Speaker.setBackgroundResource(R.drawable.usedicon);
                    speakerImage.setBackgroundResource(R.drawable.speakeroff);
                    editorsongStopper.putBoolean("IsPlaying", false);
                    editorsongStopper.commit();
                }else{
                    songActivity=new songActivity(quizActivity.this);
                    songActivity.startMusic();
                    Speaker.setBackgroundResource(R.drawable.whitewithblackstroke);
                    speakerImage.setBackgroundResource(R.drawable.speakeron);
                    editorsongStopper.putBoolean("IsPlaying", true);
                    editorsongStopper.commit();
                }




            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        loadAds();
        songStopperAndResumer();


        questionTextView=findViewById(R.id.question);
        scoreBoard=findViewById(R.id.questionNumber);
        option1=(Button) findViewById(R.id.button1);
        option2=(Button) findViewById(R.id.button2);
        option3=(Button) findViewById(R.id.button3);
        option4=(Button) findViewById(R.id.button4);
        nextButton=(Button) findViewById(R.id.nextbutton);
        linearLayout=(LinearLayout) findViewById(R.id.linearButtonlayout);
        timerText=(TextView) findViewById(R.id.timer);
        audienceLL=(CardView) findViewById(R.id.audience);
        expertAdviceLL=(CardView) findViewById(R.id.expert);
        fiftyfiftyLL=(CardView) findViewById(R.id.fiftyfifty);
        swapTheQuestionLL=(CardView) findViewById(R.id.swap);
        linearLayoutexpert=(LinearLayout) findViewById(R.id.linearLayoutexpert) ;
        linearLayoutAudience=(LinearLayout) findViewById(R.id.linearLayoutAudience) ;
        linearLayoutFiftyFifty=(LinearLayout) findViewById(R.id.linearLayoutfiftyfifty) ;
        linearLayoutSwap=(LinearLayout) findViewById(R.id.linearLayoutSwap) ;
        anim11=(LottieAnimationView) findViewById(R.id.anim11);
        anim12=(LottieAnimationView) findViewById(R.id.anim12);
        anim13=(LottieAnimationView) findViewById(R.id.anim13);
        anim14=(LottieAnimationView) findViewById(R.id.anim14);
        anim15=(LottieAnimationView) findViewById(R.id.anim15);
        anim16=(LottieAnimationView) findViewById(R.id.anim16);
        anim17=(LottieAnimationView) findViewById(R.id.anim17);
        anim18=(LottieAnimationView) findViewById(R.id.anim18);
        anim19=(LottieAnimationView) findViewById(R.id.anim19);
        anim20=(LottieAnimationView) findViewById(R.id.anim20);
        myPic=(ImageView) findViewById(R.id.myPic);

        userNameFunction();

        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_screen);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);


        list=new ArrayList<>();
        listsecondary=new ArrayList<>();

        category=getIntent().getIntExtra("category",1);
        setNo=getIntent().getIntExtra("setNo",10);
        mainfinder=getIntent().getIntExtra("mainfinder",0);

        loadingDialog.show();
        proPicFunction();


        countDownTimerFun();


        for(int i=0;i<11;i++){
            // create instance of Random class
            Random rand = new Random();

            // Generate random integers in range 0 to 29
            int setNumber;
            switch (category){
                case 1:
                case 3: case 4: case 5: case 6: case 9: case 10: case 11: case 12: case 17:
                      setNumber = rand.nextInt(299)+1;
                    fireBaseData(setNumber);break;
                case 2: case 14:
                    setNumber = rand.nextInt(499)+1;
                    fireBaseData(setNumber);break;
                case 7:
                    setNumber = rand.nextInt(401)+1;
                    fireBaseData(setNumber);break;
                case 8: case 18:
                    setNumber = rand.nextInt(339)+1;
                    fireBaseData(setNumber);break;
                case 13: case 15: case 16:
                    setNumber = rand.nextInt(249)+1;
                    fireBaseData(setNumber);break;
                case 19:
                    setNumber = rand.nextInt(399)+1;
                    fireBaseData(setNumber);break;
                default:
                    setNumber = rand.nextInt(6326)+1;
                    fireBaseData2(setNumber);break;
            }
            //NEED TO CHANGE HERE
              //NEED TO CHANGE HERE
        }


        fiftyfiftyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fiftyfiftynum==0) {
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelinemusic);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    lifelineSum++;
                    fiftyfiftynum = 1;
                    linearLayoutFiftyFifty.setBackgroundResource(R.drawable.usedicon);
                    if (option1.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator1 = 1;
                    } else if (option2.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator1 = 2;
                    } else if (option3.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator1 = 3;
                    } else {
                        manupulator1 = 4;
                    }

                    Random rand3 = new Random();
                    int runStopper = 0;
                    while (runStopper == 0) {

                        selectNum = rand3.nextInt(4) + 1;

                        if (manupulator1 != selectNum) {
                            runStopper = 1;

                        }
                    }

                    switch (selectNum) {
                        case 1:
                            switch (manupulator1) {
                                case 2:
                                    option3.setText("");
                                    option4.setText("");
                                    break;
                                case 3:
                                    option2.setText("");
                                    option4.setText("");
                                    break;
                                case 4:
                                    option2.setText("");
                                    option3.setText("");
                                    break;
                            }
                            break;
                        case 2:
                            switch (manupulator1) {
                                case 1:
                                    option3.setText("");
                                    option4.setText("");
                                    break;
                                case 3:
                                    option1.setText("");
                                    option4.setText("");
                                    break;
                                case 4:
                                    option3.setText("");
                                    option1.setText("");
                                    break;
                            }
                            break;
                        case 3:
                            switch (manupulator1) {
                                case 1:
                                    option2.setText("");
                                    option4.setText("");
                                    break;
                                case 2:
                                    option1.setText("");
                                    option4.setText("");
                                    break;
                                case 4:
                                    option2.setText("");
                                    option1.setText("");
                                    break;
                            }
                            break;
                        case 4:
                            switch (manupulator1) {
                                case 1:
                                    option3.setText("");
                                    option2.setText("");
                                    break;
                                case 2:
                                    option1.setText("");
                                    option3.setText("");
                                    break;
                                case 3:
                                    option2.setText("");
                                    option1.setText("");
                                    break;
                            }
                            break;
                    }

                }else{
                    MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelineused);
                    musicNav.start();
                    AlertDialog.Builder builder=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(quizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Oops! You Have Used Your FIFTY-FIFTY Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                }



            }
        });

        audienceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(audiencenum==0) {
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelinemusic);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    lifelineSum++;
                    audiencenum=1;
                    linearLayoutAudience.setBackgroundResource(R.drawable.usedicon);

                    if (option1.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator = 1;
                    } else if (option2.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator = 2;
                    } else if (option3.getText().toString().equals(list.get(position).getCorrectAnswer())) {
                        manupulator = 3;
                    } else {
                        manupulator = 4;
                    }

                    Random rand2 = new Random();


                    int setMax = 100 - rand2.nextInt(60);

                    switch (manupulator) {
                        case 1:
                            yo1 = setMax;
                            yo2 = rand2.nextInt(100 - yo1);
                            yo3 = rand2.nextInt(100 - yo1 - yo2);
                            yo4 = 100 - yo1 - yo2 - yo3;
                            break;
                        case 2:
                            yo2 = setMax;
                            yo1 = rand2.nextInt(100 - yo2);
                            yo3 = rand2.nextInt(100 - yo2 - yo1);
                            yo4 = 100 - yo2 - yo1 - yo3;
                            break;
                        case 3:
                            yo3 = setMax;
                            yo2 = rand2.nextInt(100 - yo3);
                            yo1 = rand2.nextInt(100 - yo3 - yo2);
                            yo4 = 100 - yo3 - yo2 - yo1;
                            break;
                        case 4:
                            yo4 = setMax;
                            yo2 = rand2.nextInt(100 - yo4);
                            yo1 = rand2.nextInt(100 - yo4 - yo2);
                            yo3 = 100 - yo4 - yo2 - yo1;
                            break;
                    }



                    AlertDialog.Builder builder = new AlertDialog.Builder(quizActivity.this, R.style.AlertDialogTheme);

                    final View view1 = LayoutInflater.from(quizActivity.this).inflate(R.layout.audience_layout, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText(" MindScapers from across the world have casted their votes above. Choose your option! ");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    BarChart barChart = ((BarChart) view1.findViewById(R.id.barChart));


                    final ArrayList<BarEntry> visitors = new ArrayList<>();
                    visitors.add(new BarEntry(1, yo1));
                    visitors.add(new BarEntry(2, yo2));
                    visitors.add(new BarEntry(3, yo3));
                    visitors.add(new BarEntry(4, yo4));

                    AdView mAdView = view1.findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);

                    BarDataSet barDataSet = new BarDataSet(visitors, "Bar Data");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                    barDataSet.setValueTextColor(Color.BLUE);
                    barDataSet.setValueTextSize(11f);

                    BarData barData = new BarData(barDataSet);

                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Audience Poll");
                    barChart.animateY(2000);


                    final AlertDialog alertDialog = builder.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }else{
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelineused);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    AlertDialog.Builder builder=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(quizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Oops! You Have Used Your AUDIENCE POLL Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });



        swapTheQuestionLL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if(swapnum==0){
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelinemusic);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    lifelineSum++;
                    swapnum=1;
                    linearLayoutSwap.setBackgroundResource(R.drawable.usedicon);
                    nextButton.setEnabled(false);
                    nextButton.setAlpha(0.7f);
                    enableOption(true);
                    position++;
                    LLTrueManupulator();


                    count = 0;
                    playAnim(questionTextView, 0, list.get(position).getQuestionTextView());
                }else{

                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelineused);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    AlertDialog.Builder builder=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(quizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Oops! You Have Used Your SWAP Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                }

            }
        });





        expertAdviceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(expertnum==0){
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelinemusic);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });

                    lifelineSum++;
                    expertnum=1;
                    linearLayoutexpert.setBackgroundResource(R.drawable.usedicon);
                    String answerByExpert=list.get(position).getCorrectAnswer();


                    AlertDialog.Builder builder=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(quizActivity.this).inflate(R.layout.expertadvicelayout,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    titleText=((TextView) view1.findViewById(R.id.textTitle));
                    ((TextView) view1.findViewById(R.id.textMessage)).setText(userName+" I feel you should go for  : '"+answerByExpert+"'");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    expertImage=((ImageView) view1.findViewById(R.id.imageIcon));
                    AdView mAdView = view1.findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    expertAdviceImageManupulator();


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });


                }else{
                    final MediaPlayer musicNav;
                    musicNav = MediaPlayer.create(quizActivity.this, R.raw.lifelineused);
                    musicNav.start();
                    musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
                    AlertDialog.Builder builder=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(quizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Oops! You Have Used Your EXPERT ADVICE Life Line Once.");
                    expertAdviceImageManupulator();

                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    try{
                        alertDialog.show();
                    }catch (Exception e){

                    }

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });





    }

    public void fireBaseData2(int setNumber){
        myRef.child("NormalQuizBIGJSON").child(String.valueOf(setNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.add(snapshot.getValue(questionHolder.class));
                num++;

                if(num==10) {
                    if (list.size() > 0) {
                        for (int i = 0; i < 4; i++) {
                            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(View view) {
                                    try{
                                        checkAnswer((Button) view);
                                    }catch (Exception e){
                                        //        Toast.makeText(quizActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                        playAnim(questionTextView, 0, list.get(position).getQuestionTextView());

                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View view) {
                                final MediaPlayer musicNav;
                                musicNav = MediaPlayer.create(quizActivity.this, R.raw.buttonmusic);
                                musicNav.start();
                                musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        musicNav.reset();
                                        musicNav.release();
                                    }
                                });

                                nextButton.setEnabled(false);
                                nextButton.setAlpha(0.7f);
                                enableOption(true);
                                position++;
                                LLTrueManupulator();



                                if(swapnum==0){
                                    if (position == 10) {

                                        mInterstitialAd.setAdListener(new AdListener(){
                                            public void onAdClosed(){
                                                super.onAdClosed();
                                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                                scoreIntent.putExtra("score", score);
                                                scoreIntent.putExtra("lifeline",lifelineSum);
                                                scoreIntent.putExtra("minutes",minutes);
                                                scoreIntent.putExtra("seconds",second);
                                                scoreIntent.putExtra("minutestext",minutestext);
                                                scoreIntent.putExtra("secondtext",secondtext);
                                                scoreIntent.putExtra("milliholder",milliHolder);
                                                scoreIntent.putExtra("category",category);
                                                scoreIntent.putExtra("imageurl",imageurl);
                                                startActivity(scoreIntent);
                                                if(countDownTimer!=null){
                                                    countDownTimer.cancel();}
                                                overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                                finish();
                                            }

                                        });

                                        if(mInterstitialAd.isLoaded()){
                                            try{
                                                songActivity.songStop();
                                            }catch (Exception e){

                                            }
                                            mInterstitialAd.show();
                                            return;
                                        }


                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }

                                        Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                        scoreIntent.putExtra("score", score);
                                        scoreIntent.putExtra("lifeline",lifelineSum);
                                        scoreIntent.putExtra("minutes",minutes);
                                        scoreIntent.putExtra("seconds",second);
                                        scoreIntent.putExtra("minutestext",minutestext);
                                        scoreIntent.putExtra("secondtext",secondtext);
                                        scoreIntent.putExtra("milliholder",milliHolder);
                                        scoreIntent.putExtra("category",category);
                                        scoreIntent.putExtra("imageurl",imageurl);
                                        startActivity(scoreIntent);
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                        overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                        finish();
                                        return;
                                    }

                                }else {
                                    if (position == 11) {



                                        mInterstitialAd.setAdListener(new AdListener(){
                                            public void onAdClosed(){
                                                super.onAdClosed();
                                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                                scoreIntent.putExtra("score", score);
                                                scoreIntent.putExtra("lifeline",lifelineSum);
                                                scoreIntent.putExtra("minutes",minutes);
                                                scoreIntent.putExtra("seconds",second);
                                                scoreIntent.putExtra("minutestext",minutestext);
                                                scoreIntent.putExtra("secondtext",secondtext);
                                                scoreIntent.putExtra("milliholder",milliHolder);
                                                scoreIntent.putExtra("category",category);
                                                scoreIntent.putExtra("imageurl",imageurl);
                                                startActivity(scoreIntent);
                                                if(countDownTimer!=null){
                                                    countDownTimer.cancel();}
                                                overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                                finish();
                                            }

                                        });

                                        if(mInterstitialAd.isLoaded()){
                                            try{
                                                songActivity.songStop();
                                            }catch (Exception e){

                                            }
                                            mInterstitialAd.show();
                                            return;
                                        }

                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }


                                        Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                        scoreIntent.putExtra("score", score);
                                        scoreIntent.putExtra("lifeline",lifelineSum);
                                        scoreIntent.putExtra("minutes",minutes);
                                        scoreIntent.putExtra("seconds",second);
                                        scoreIntent.putExtra("minutestext",minutestext);
                                        scoreIntent.putExtra("secondtext",secondtext);
                                        scoreIntent.putExtra("category",category);
                                        scoreIntent.putExtra("milliholder",milliHolder);
                                        scoreIntent.putExtra("imageurl",imageurl);
                                        startActivity(scoreIntent);
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                        overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                        finish();
                                        return;
                                    }
                                }

                                count = 0;

                                playAnim(questionTextView, 0, list.get(position).getQuestionTextView());

                            }
                        });
                    } else {
                        finish();
                        Toast.makeText(quizActivity.this, "No Questions", Toast.LENGTH_SHORT).show();

                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quizActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }

    public void fireBaseData(int setNumber){
        myRef.child("SETS").child(String.valueOf(category)).child("questions").child(String.valueOf(setNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    list.add(snapshot.getValue(questionHolder.class));
                    num++;

                if(num==10) {
                    if (list.size() > 0) {
                        for (int i = 0; i < 4; i++) {
                            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(View view) {
                                    try{
                                        checkAnswer((Button) view);
                                    }catch (Exception e){
                                //        Toast.makeText(quizActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                        playAnim(questionTextView, 0, list.get(position).getQuestionTextView());

                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View view) {
                                final MediaPlayer musicNav;
                                musicNav = MediaPlayer.create(quizActivity.this, R.raw.buttonmusic);
                                musicNav.start();
                                musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        musicNav.reset();
                                        musicNav.release();
                                    }
                                });

                                nextButton.setEnabled(false);
                                nextButton.setAlpha(0.7f);
                                enableOption(true);
                                position++;
                                LLTrueManupulator();



                                if(swapnum==0){
                                    if (position == 10) {
                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }
                                        mInterstitialAd.setAdListener(new AdListener(){
                                            public void onAdClosed(){
                                                super.onAdClosed();
                                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                                scoreIntent.putExtra("score", score);
                                                scoreIntent.putExtra("lifeline",lifelineSum);
                                                scoreIntent.putExtra("minutes",minutes);
                                                scoreIntent.putExtra("seconds",second);
                                                scoreIntent.putExtra("minutestext",minutestext);
                                                scoreIntent.putExtra("secondtext",secondtext);
                                                scoreIntent.putExtra("milliholder",milliHolder);
                                                scoreIntent.putExtra("category",category);
                                                scoreIntent.putExtra("imageurl",imageurl);
                                                startActivity(scoreIntent);

                                                if(countDownTimer!=null){
                                                    countDownTimer.cancel();}
                                                overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                                finish();
                                            }

                                        });

                                        if(mInterstitialAd.isLoaded()){
                                            try{
                                                songActivity.songStop();
                                            }catch (Exception e){

                                            }
                                            mInterstitialAd.show();
                                            return;
                                        }


                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }

                                        Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                        scoreIntent.putExtra("score", score);
                                        scoreIntent.putExtra("lifeline",lifelineSum);
                                        scoreIntent.putExtra("minutes",minutes);
                                        scoreIntent.putExtra("seconds",second);
                                        scoreIntent.putExtra("minutestext",minutestext);
                                        scoreIntent.putExtra("secondtext",secondtext);
                                        scoreIntent.putExtra("milliholder",milliHolder);
                                        scoreIntent.putExtra("category",category);
                                        scoreIntent.putExtra("imageurl",imageurl);
                                        startActivity(scoreIntent);
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                        overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                        finish();
                                       return;
                                    }

                                }else {
                                    if (position == 11) {

                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }

                                        mInterstitialAd.setAdListener(new AdListener(){
                                            public void onAdClosed(){
                                                super.onAdClosed();
                                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                                Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                                scoreIntent.putExtra("score", score);
                                                scoreIntent.putExtra("lifeline",lifelineSum);
                                                scoreIntent.putExtra("minutes",minutes);
                                                scoreIntent.putExtra("seconds",second);
                                                scoreIntent.putExtra("minutestext",minutestext);
                                                scoreIntent.putExtra("secondtext",secondtext);
                                                scoreIntent.putExtra("milliholder",milliHolder);
                                                scoreIntent.putExtra("category",category);
                                                scoreIntent.putExtra("imageurl",imageurl);
                                                startActivity(scoreIntent);
                                                if(countDownTimer!=null){
                                                    countDownTimer.cancel();}
                                                overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                                finish();
                                            }

                                        });

                                        if(mInterstitialAd.isLoaded()){
                                            try{
                                                songActivity.songStop();
                                            }catch (Exception e){

                                            }
                                            mInterstitialAd.show();
                                            return;
                                        }

                                        try{
                                            songActivity.songStop();
                                        }catch (Exception e){

                                        }


                                        Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                                        scoreIntent.putExtra("score", score);
                                        scoreIntent.putExtra("lifeline",lifelineSum);
                                        scoreIntent.putExtra("minutes",minutes);
                                        scoreIntent.putExtra("seconds",second);
                                        scoreIntent.putExtra("minutestext",minutestext);
                                        scoreIntent.putExtra("secondtext",secondtext);
                                        scoreIntent.putExtra("category",category);
                                        scoreIntent.putExtra("milliholder",milliHolder);
                                        scoreIntent.putExtra("imageurl",imageurl);
                                        startActivity(scoreIntent);
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                        overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                                        finish();
                                        return;
                                    }
                                }

                                count = 0;

                                playAnim(questionTextView, 0, list.get(position).getQuestionTextView());

                            }
                        });
                    } else {
                        finish();
                        Toast.makeText(quizActivity.this, "No Questions", Toast.LENGTH_SHORT).show();

                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(quizActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }


    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationStart(Animator animator) {
                if(value==0 && count<4){
                    String option="";
                    if(count==0){
                        option=list.get(position).getOption1();
                        option1.setTextColor(Color.parseColor("#ffffff"));
                        linearLayout.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                    }else if(count==1){
                        option=list.get(position).getOption2();
                        option2.setTextColor(Color.parseColor("#ffffff"));
                        linearLayout.getChildAt(1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                    }else if(count==2){
                        option=list.get(position).getOption3();
                        option3.setTextColor(Color.parseColor("#ffffff"));
                        linearLayout.getChildAt(2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                    }else if(count==3){
                        option=list.get(position).getOption4();
                        option4.setTextColor(Color.parseColor("#ffffff"));
                        linearLayout.getChildAt(3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                    }
                    playAnim(linearLayout.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        if(swapnum==0){
                            scoreBoard.setText(" Question "+(position+1)+"/10 ");
                        }else{
                            scoreBoard.setText(" Question "+(position)+"/10 ");
                        }

                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void LLFalseManupulator(){        //Setting all the lifelines enable:False when an option is selected.
        audienceLL.setClickable(false);
        audienceLL.setEnabled(false);
        audienceLL.setAlpha(0.8f);

        expertAdviceLL.setClickable(false);
        expertAdviceLL.setEnabled(false);
        expertAdviceLL.setAlpha(0.8f);

        fiftyfiftyLL.setClickable(false);
        fiftyfiftyLL.setEnabled(false);
        fiftyfiftyLL.setAlpha(0.8f);

        swapTheQuestionLL.setClickable(false);
        swapTheQuestionLL.setEnabled(false);
        swapTheQuestionLL.setAlpha(0.8f);

    }

    public void LLTrueManupulator(){       //Setting all the lifelines enable:False when next button is pressed.
        audienceLL.setClickable(true);
        audienceLL.setEnabled(true);
        audienceLL.setAlpha(1.0f);

        expertAdviceLL.setClickable(true);
        expertAdviceLL.setEnabled(true);
        expertAdviceLL.setAlpha(1.0f);

        fiftyfiftyLL.setClickable(true);
        fiftyfiftyLL.setEnabled(true);
        fiftyfiftyLL.setAlpha(1.0f);

        swapTheQuestionLL.setClickable(true);
        swapTheQuestionLL.setEnabled(true);
        swapTheQuestionLL.setAlpha(1.0f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextButton.setEnabled(true);
        nextButton.setAlpha(1);

        LLFalseManupulator();

        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){


            //correct
            final MediaPlayer musicNav;
            musicNav = MediaPlayer.create(quizActivity.this, R.raw.correctmusic);
            musicNav.start();
             musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
            myanimManuCorrect();
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1FF88")));   //green color
            selectedOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            selectedOption.setShadowLayer(3,1,1,R.color.lightgreen);
            score++;
        }else {
            //incorrect
            final MediaPlayer musicNav;
            musicNav = MediaPlayer.create(quizActivity.this, R.raw.wrongansfinal);
            musicNav.start();
             musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            musicNav.reset();
                            musicNav.release();
                        }
                    });
            myanimManuWrong();
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF8888")));     //red color
            selectedOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            selectedOption.setShadowLayer(3,1,1,R.color.lightgreen);
            Button correctOption = (Button) linearLayout.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1FF88")));     //green color
            correctOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            correctOption.setShadowLayer(3,1,1,R.color.lightred);
        }
    }

    public void myanimManuCorrect(){
        myPosition++;
        switch (myPosition){
            case 1:
                anim11.setAnimation(R.raw.tickanim);
                anim11.playAnimation();
                anim11.loop(false);break;
            case 2:
                anim12.setAnimation(R.raw.tickanim);
                anim12.playAnimation();
                anim12.loop(false);break;
            case 3:
                anim13.setAnimation(R.raw.tickanim);
                anim13.playAnimation();
                anim13.loop(false);break;
            case 4:
                anim14.setAnimation(R.raw.tickanim);
                anim14.playAnimation();
                anim14.loop(false);break;
            case 5:
                anim15.setAnimation(R.raw.tickanim);
                anim15.playAnimation();
                anim15.loop(false);break;
            case 6:
                anim16.setAnimation(R.raw.tickanim);
                anim16.playAnimation();
                anim16.loop(false);break;
            case 7:
                anim17.setAnimation(R.raw.tickanim);
                anim17.playAnimation();
                anim17.loop(false);break;
            case 8:
                anim18.setAnimation(R.raw.tickanim);
                anim18.playAnimation();
                anim18.loop(false);break;
            case 9:
                anim19.setAnimation(R.raw.tickanim);
                anim19.playAnimation();
                anim19.loop(false);break;
            case 10:
                anim20.setAnimation(R.raw.tickanim);
                anim20.playAnimation();
                anim20.loop(false);break;
        }
    }

    public void myanimManuWrong(){
        myPosition++;
        switch (myPosition){
            case 1:
                anim11.setAnimation(R.raw.wronganim);
                anim11.playAnimation();
                anim11.loop(false);break;
            case 2:
                anim12.setAnimation(R.raw.wronganim);
                anim12.playAnimation();
                anim12.loop(false);break;
            case 3:
                anim13.setAnimation(R.raw.wronganim);
                anim13.playAnimation();
                anim13.loop(false);break;
            case 4:
                anim14.setAnimation(R.raw.wronganim);
                anim14.playAnimation();
                anim14.loop(false);break;
            case 5:
                anim15.setAnimation(R.raw.wronganim);
                anim15.playAnimation();
                anim15.loop(false);break;
            case 6:
                anim16.setAnimation(R.raw.wronganim);
                anim16.playAnimation();
                anim16.loop(false);break;
            case 7:
                anim17.setAnimation(R.raw.wronganim);
                anim17.playAnimation();
                anim17.loop(false);break;
            case 8:
                anim18.setAnimation(R.raw.wronganim);
                anim18.playAnimation();
                anim18.loop(false);
                break;
            case 9:
                anim19.setAnimation(R.raw.wronganim);
                anim19.playAnimation();
                anim19.loop(false);break;
            case 10:
                anim20.setAnimation(R.raw.wronganim);
                anim20.playAnimation();
                anim20.loop(false);break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean enable){
        for (int i=0;i<4;i++) {
            linearLayout.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E4E4E4")));
            }
        }
    }

    public void countDownTimerFun(){   //Clock Algo
        countDownTimer=new CountDownTimer(60000*10, 1000) {

            CardView Timer = (CardView) findViewById(R.id.cardView3);


            public void onTick(long millisUntilFinished) {
                milliHolder=millisUntilFinished;
                if(second==60){
                    second=0;
                    minutes++;

                    minutestext="0"+String.valueOf(minutes);

                    if(second<10){
                        secondtext="0"+String.valueOf(second);
                    }else{
                        secondtext=String.valueOf(second);
                    }
                    timerText.setText(" Timer "+minutestext+":"+secondtext+" ");
                    second++;
                }else{
                        minutestext="0"+String.valueOf(minutes);
                    if(second<10){
                        secondtext="0"+String.valueOf(second);
                    }else{
                        secondtext=String.valueOf(second);
                    }
                    timerText.setText(" Timer "+minutestext+":"+secondtext+" ");
                    second++;
                }

                //Last 15 seconds end animation
                if(minutes==9 && second>45){

                    timerText.setTextColor(getResources().getColor(R.color.av_red));

                    //Continuous zoomIn - zoomOut
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(Timer, "scaleX", 0.9f, 1f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(Timer, "scaleY", 0.9f, 1f);

                    scaleX.setRepeatCount(ObjectAnimator.INFINITE);
                    scaleX.setRepeatMode(ObjectAnimator.REVERSE);

                    scaleY.setRepeatCount(ObjectAnimator.INFINITE);
                    scaleY.setRepeatMode(ObjectAnimator.REVERSE);

                    AnimatorSet scaleAnim = new AnimatorSet();
                    scaleAnim.setDuration(500);
                    scaleAnim.play(scaleX).with(scaleY);

                    scaleAnim.start();
                }

            }
            public void onFinish() {
                try{
                    songActivity.songStop();
                }catch (Exception e){

                }
                Toast.makeText(quizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                mInterstitialAd.setAdListener(new AdListener(){
                    public void onAdClosed(){
                        super.onAdClosed();
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());


                        Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);

                        scoreIntent.putExtra("score", score);
                        scoreIntent.putExtra("lifeline",lifelineSum);
                        scoreIntent.putExtra("minutes",minutes);
                        scoreIntent.putExtra("seconds",second);
                        scoreIntent.putExtra("minutestext",minutestext);
                        scoreIntent.putExtra("secondtext",secondtext);
                        scoreIntent.putExtra("milliholder",milliHolder);
                        scoreIntent.putExtra("category",category);
                        scoreIntent.putExtra("imageurl",imageurl);
                        startActivity(scoreIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        if(countDownTimer!=null){
                            countDownTimer.cancel();}
                        finish();
                    }

                });

                if(mInterstitialAd.isLoaded()){
                    try{
                        songActivity.songStop();
                    }catch (Exception e){

                    }
                    mInterstitialAd.show();
                    return;
                }


                Intent scoreIntent = new Intent(quizActivity.this, scoreActivity.class);
                try{
                    songActivity.songStop();
                }catch (Exception e){

                }
                scoreIntent.putExtra("score", score);
                scoreIntent.putExtra("lifeline",lifelineSum);
                scoreIntent.putExtra("minutes",minutes);
                scoreIntent.putExtra("seconds",second);
                scoreIntent.putExtra("minutestext",minutestext);
                scoreIntent.putExtra("secondtext",secondtext);
                scoreIntent.putExtra("milliholder",milliHolder);
                scoreIntent.putExtra("category",category);
                scoreIntent.putExtra("imageurl",imageurl);
                startActivity(scoreIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                if(countDownTimer!=null){
                    countDownTimer.cancel();}
                finish();
            }

        }.start();
    }

    public void proPicFunction(){


        myRef.child("User").child(mAuth.getCurrentUser().getUid()).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageurl = (String) snapshot.getValue();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void expertAdviceImageManupulator(){     //Aakash changes in this functions are to be done
        Random rand = new Random();
        int num = rand.nextInt(11)+1;

        switch (num){
            case 1:               //If possible, avatars can match the facial descriptions
                expertImage.setBackgroundResource(R.drawable.expert1female);
                titleText.setText(" Dorjana Sirola: Highest woman scorer in World Quizzing Championship, Croatian linguist and anglicist! is Expert for the day");
                break;            //white complexion,short hair
            case 2:
                expertImage.setBackgroundResource(R.drawable.expert2male);
                titleText.setText("Dr.Neil deGrasse Tyson: Astrophysicist, Planetory scientist, Author and Science communicator! is Expert for the day");
                break;            //Dark complexion
            case 3:
                expertImage.setBackgroundResource(R.drawable.expert3male);
                titleText.setText("Kevin Ashman: Six times World Quizzing Championship winner and Five times British Quizing Champion! is Expert for the day");
                break;            //white complexion
            case 4:
                expertImage.setBackgroundResource(R.drawable.expert4male);
                titleText.setText("Derek O'Brian: Quiz Master, Indian politician and television personality! is Expert for the day");
                break;            //white complexion, spects
            case 5:
                expertImage.setBackgroundResource(R.drawable.expert5male);
                titleText.setText("Pat Gibson: Multiple World champion in quizzing, Software developer and professional Irish quizzer! is Expert for the day");
                break;             // white complexion, spectacles
            case 6:
                expertImage.setBackgroundResource(R.drawable.expert6female);
                titleText.setText("Elsie Kaufmann: Quiz mistress, Ghanaian academic and Biomedical engineer! is Expert for the day.");
                break;          //Dark complexion
            case 7:
                expertImage.setBackgroundResource(R.drawable.expert7male);
                titleText.setText("Olav Bjortomt: Four time World champion and English international quiz star player! is Expert for the day");
                break;          //White complexion
            case 8:
                expertImage.setBackgroundResource(R.drawable.expert8female);
                titleText.setText("Anne Hegerty: English quizzer and famous UK television personality! is Expert for the day");
                break;              //White complexion,short hair,fat face
            case 9:
                expertImage.setBackgroundResource(R.drawable.expert9female);
                titleText.setText("Seema Chari: Quiz mistress, author, anchor and knowledge media professional! is Expert for the day");
                break;          //curly hair
            case 10:
                expertImage.setBackgroundResource(R.drawable.expert10male);
                titleText.setText("Siddhartha Basu: Father of Indian television quizzing, producer-director and quiz show host! is Expert for the day");
                break;          //almost no hair,fair complexion
            case 11:
                expertImage.setBackgroundResource(R.drawable.expert11male);
                titleText.setText("Tom Trogh: Belgian quiz player and European quizzing champion! is Expert for the day");
                break;            //White complexion
            case 12:
                expertImage.setBackgroundResource(R.drawable.expert12male);
                titleText.setText("Ravi Avva: 2020 World Quizzing champion, Singaporean hailing from India and an Engineer! is Expert for the day");
                break;          //Fair complexion,spectacles

        }


    }
    public void userNameFunction(){
        myRef.child("User").child(mAuth.getCurrentUser().getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("User").child(mAuth.getCurrentUser().getUid()).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str=snapshot.getValue(String.class);
                Glide.with(getBaseContext())
                        .load(str)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(myPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void onBackPressed() {
            cancelDialogFunction();
    }


    public void cancelDialogFunction(){
        AlertDialog.Builder builderRemove=new AlertDialog.Builder(quizActivity.this,R.style.AlertDialogTheme);
        View viewRemove1= LayoutInflater.from(quizActivity.this).inflate(R.layout.quit_asker_layout,(ConstraintLayout) findViewById(R.id.layoutDialogContainer),false);
        builderRemove.setView(viewRemove1);
        builderRemove.setCancelable(false);
        Button yesButton=(Button) viewRemove1.findViewById(R.id.buttonYes);
        Button noButton=(Button) viewRemove1.findViewById(R.id.buttonNo);





        final AlertDialog alertDialog=builderRemove.create();
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        try{
            alertDialog.show();
        }catch (Exception e){

        }


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer musicNav;
                musicNav = MediaPlayer.create(quizActivity.this, R.raw.finalbuttonmusic);
                musicNav.start();
                musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        musicNav.reset();
                        musicNav.release();
                    }
                });
                if(countDownTimer!=null){
                    countDownTimer.cancel();
                }
                alertDialog.cancel();
                Intent i=new Intent(quizActivity.this,mainMenuActivity.class);
                startActivity(i);
                try{
                    songActivity.songStop();
                }catch (Exception e){

                }
                overridePendingTransition(R.anim.fadeinmain, R.anim.fadeoutmain);
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer musicNav;
                musicNav = MediaPlayer.create(quizActivity.this, R.raw.finalbuttonmusic);
                musicNav.start();
                musicNav.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        musicNav.reset();
                        musicNav.release();
                    }
                });
                alertDialog.dismiss();
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try{
            songActivity.songStop();
        }catch (Exception e){

        }


        if(countDownTimer!=null){
            countDownTimer.cancel();}

        Runtime.getRuntime().gc();
    }


}