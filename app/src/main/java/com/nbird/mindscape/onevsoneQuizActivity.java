package com.nbird.mindscape;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class onevsoneQuizActivity extends AppCompatActivity {
    Dialog loadingDialog,expert1dialog;
//1344
    TextView questionTextView,scoreBoard;
    Button option1,option2,option3,option4,nextButton;
    LinearLayout linearLayout;
    private int count;
    private List<questionHolder> list,listsecondary;
    private List<mainMenuFactsHolder> list4;
    CountDownTimer countDownTimer,countDownTimer47,countDownTimer50,countDownTimerMine;
    int position=0;
    private int score=0;
    private int currentPage;
    int category;
    private int setNo;
    long milliHolder;
    int kong;
    int minMine,secMine;
    int bull=0;
    int car=0;
    int a1,a2;
    String mine;
    private TextView[] mDots;
    private onevsoneonlineAdapter sliderAdapter;
    ActionBarDrawerToggle mToggle;
    private LinearLayout dotLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    TextView timerText;
    String imageurl;
    int jack=1;
    int num=0;
    ImageView expertImage;
    int expertnum=0;
    int swapnum=0;
    int audiencenum=0;
    public ViewPager slideViewPager;
    int manupulator=0;
    int manupulator1=0;
    int fiftyfiftynum=0;
    int leader;
    TextView titleText;
    int yo1;
    int yo2;
    int yo3;
    int yo4;
    int minman,secman;
    String userName;
    int selectNum;
    int lifelineSum=0;
    int myArrPos=1;
    CardView audienceLL,expertAdviceLL,fiftyfiftyLL,swapTheQuestionLL;
    LinearLayout linearLayoutexpert,linearLayoutAudience,linearLayoutFiftyFifty,linearLayoutSwap;

    int minutes=2;
    int second=59;
    String minutestext,myProPicUrl,myName;
    String secondtext;
    int cou=0;
    int questionNumber=1;
    int num123;
    String opponentUID,opponentUsername,opponentimageUrl;
    int binaryPosition=1;
    int opponentAnswer;
    LottieAnimationView anim1,anim2,anim3,anim4,anim5,anim6,anim7,anim8,anim9,anim10;
    ImageView opponentPic;
    TextView opponentName;
    int isComplete;
    ImageView propic;
    TextView yourName,timeTaken,ratio,accuracy,lifeLines;
    AlertDialog.Builder builder;
    View view1;
    int oppoScoreCounter=0;
    int oppoWrongAnsCounter=0;
    int myArr[];
    List<Integer> arrlist,arroppo;
    List<Integer> arrlist12345;
    int min12345,sec12345;
    int milliHolder47;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onevsone_quiz);

        builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);
        view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.waiting_dialog_layout,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        arrlist12345 = new ArrayList<>(11);
        opponentPic=(ImageView) findViewById(R.id.opponentPic);
        opponentName=(TextView) findViewById(R.id.opponentName);
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
        linearLayoutSwap=(LinearLayout) findViewById(R.id.linearLayoutSwap);
        mShimmerViewContainer = (ShimmerFrameLayout) view1.findViewById(R.id.shimmer_view_container);
        slideViewPager=(ViewPager) view1.findViewById(R.id.slideViewPager);
        dotLayout=(LinearLayout) view1.findViewById(R.id.dotLayout);

        anim1=(LottieAnimationView) findViewById(R.id.anim1);
        anim2=(LottieAnimationView) findViewById(R.id.anim2);
        anim3=(LottieAnimationView) findViewById(R.id.anim3);
        anim4=(LottieAnimationView) findViewById(R.id.anim4);
        anim5=(LottieAnimationView) findViewById(R.id.anim5);
        anim6=(LottieAnimationView) findViewById(R.id.anim6);
        anim7=(LottieAnimationView) findViewById(R.id.anim7);
        anim8=(LottieAnimationView) findViewById(R.id.anim8);
        anim9=(LottieAnimationView) findViewById(R.id.anim9);
        anim10=(LottieAnimationView) findViewById(R.id.anim10);

        arrlist = new ArrayList<>(12);
        arroppo = new ArrayList<>(12);

        userNameFunction();

        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_screen);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);


        list=new ArrayList<>();
        list4=new ArrayList<>();
        listsecondary=new ArrayList<>();




        category=getIntent().getIntExtra("category",1);
        setNo=getIntent().getIntExtra("setNo",10);
        opponentUID=getIntent().getStringExtra("opponentUID");
        opponentimageUrl=getIntent().getStringExtra("opponentImageUrl");
        opponentUsername=getIntent().getStringExtra("opponentUserName");
        myProPicUrl=getIntent().getStringExtra("mypropic");
        myName=getIntent().getStringExtra("myName");
        leader=getIntent().getIntExtra("leader",0);
        arrlist12345=getIntent().getIntegerArrayListExtra("arrList12345");




        opponentName.setText(opponentUsername);

        Glide.with(getBaseContext())
                .load(opponentimageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(opponentPic);




        loadingDialog.show();
        proPicFunction();


        countDownTimerFun();
        countDownTimerFun47();
        countDownTimerFun50();
        for(int i=0;i<12;i++){
            try{
                // create instance of Random class
                fireBaseData(arrlist12345.get(i));
            }catch (Exception e){
                Random rand = new Random();
                // Generate random integers in range 0 to 29
                final int setNumber = rand.nextInt(29)+1;  //NEED TO CHANGE HERE
                //NEED TO CHANGE HERE
                fireBaseData(setNumber);
            }

        }


        fiftyfiftyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fiftyfiftynum==0) {
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

                    AlertDialog.Builder builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Sorry Lucy! You Have Used Your FIFTY-FIFTY Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

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
                            try{
                                yo2 = rand2.nextInt(100 - yo1);
                                yo3 = rand2.nextInt(100 - yo1 - yo2);
                            }catch (Exception e){
                                yo2 = rand2.nextInt(100 - yo1+1);
                                yo3 = rand2.nextInt(100 - yo1 - yo2+1);
                            }
                            yo4 = 100 - yo1 - yo2 - yo3;
                            break;
                        case 2:
                            yo2 = setMax;
                            try{
                                yo1 = rand2.nextInt(100 - yo2);
                                yo3 = rand2.nextInt(100 - yo2 - yo1);
                            }catch (Exception e){
                                yo1 = rand2.nextInt(100 - yo2+1);
                                yo3 = rand2.nextInt(100 - yo2 - yo1+1);
                            }
                            yo4 = 100 - yo2 - yo1 - yo3;
                            break;
                        case 3:
                            yo3 = setMax;
                            try{
                                yo2 = rand2.nextInt(100 - yo3);
                                yo1 = rand2.nextInt(100 - yo3 - yo2);
                            }catch (Exception e){
                                yo2 = rand2.nextInt(100 - yo3+1);
                                yo1 = rand2.nextInt(100 - yo3 - yo2+1);
                            }

                            yo4 = 100 - yo3 - yo2 - yo1;
                            break;
                        case 4:
                            yo4 = setMax;
                            try{
                                yo2 = rand2.nextInt(100 - yo4);
                                yo1 = rand2.nextInt(100 - yo4 - yo2);
                            }catch (Exception e){
                                yo2 = rand2.nextInt(100 - yo4+1);
                                yo1 = rand2.nextInt(100 - yo4 - yo2+1);
                            }

                            yo3 = 100 - yo4 - yo2 - yo1;
                            break;
                    }


                    AlertDialog.Builder builder = new AlertDialog.Builder(onevsoneQuizActivity.this, R.style.AlertDialogTheme);

                    final View view1 = LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.audience_layout, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Earn 10 Paper Notes By Entering Your Friends Referral Code!");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    BarChart barChart = ((BarChart) view1.findViewById(R.id.barChart));


                    final ArrayList<BarEntry> visitors = new ArrayList<>();
                    visitors.add(new BarEntry(1, yo1));
                    visitors.add(new BarEntry(2, yo2));
                    visitors.add(new BarEntry(3, yo3));
                    visitors.add(new BarEntry(4, yo4));


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
                    alertDialog.show();

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }else{

                    AlertDialog.Builder builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Sorry Lucy! You Have Used Your AUDIENCE POLL Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

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

                    AlertDialog.Builder builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    ((TextView) view1.findViewById(R.id.textTitle)).setText("Sorry Lucy! You Have Used Your SWAP Life Line Once.");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

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
                    lifelineSum++;
                    expertnum=1;
                    linearLayoutexpert.setBackgroundResource(R.drawable.usedicon);
                    String answerByExpert=list.get(position).getCorrectAnswer();


                    AlertDialog.Builder builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.expertadvicelayout,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    titleText=((TextView) view1.findViewById(R.id.textTitle));
                    ((TextView) view1.findViewById(R.id.textMessage)).setText(userName+" I Think It's : \n'"+answerByExpert+"'");
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    expertImage=((ImageView) view1.findViewById(R.id.imageIcon));
                    expertAdviceImageManupulator();


                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });


                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(onevsoneQuizActivity.this,R.style.AlertDialogTheme);

                    final View view1= LayoutInflater.from(onevsoneQuizActivity.this).inflate(R.layout.sorry_layout_for_helplines,(ConstraintLayout) findViewById(R.id.layoutDialogContainer));
                    builder.setView(view1);
                    builder.setCancelable(false);
                    titleText=((TextView) view1.findViewById(R.id.textTitle));
                    ((Button) view1.findViewById(R.id.buttonYes)).setText("OKAY");
                    expertAdviceImageManupulator();

                    final AlertDialog alertDialog=builder.create();
                    if(alertDialog.getWindow()!=null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();

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
    public void fireBaseData(int setNumber){
        myRef.child("SETS").child(String.valueOf(category)).child("questions").orderByChild("sets").equalTo(setNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    list.add(snapshot1.getValue(questionHolder.class));
                    num++;
                }
                if(num==10) {
                    if (list.size() > 0) {
                        for (int i = 0; i < 4; i++) {
                            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(View view) {
                                    checkAnswer((Button) view);
                                }
                            });
                        }
                        playAnim(questionTextView, 0, list.get(position).getQuestionTextView());

                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View view) {
                                nextButton.setEnabled(false);
                                nextButton.setAlpha(0.7f);
                                enableOption(true);
                                position++;
                                LLTrueManupulator();
                                battleGroundFor1vs1Function(num123);


                                if(swapnum==0){
                                    if (position == 10) {
                                        scoreBoard.setAlpha(0);
                                        questionTextView.setAlpha(0);
                                        option1.setAlpha(0);
                                        option2.setAlpha(0);
                                        option3.setAlpha(0);
                                        option4.setAlpha(0);
                                        min12345=minutes;
                                        sec12345=second;
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                       waitingFunction();
                                    }else{
                                        count = 0;

                                        playAnim(questionTextView, 0, list.get(position).getQuestionTextView());
                                    }

                                }else {
                                    if (position == 11) {
                                        scoreBoard.setAlpha(0);
                                        questionTextView.setAlpha(0);
                                        option1.setAlpha(0);
                                        option2.setAlpha(0);
                                        option3.setAlpha(0);
                                        option4.setAlpha(0);
                                        min12345=minutes;
                                        sec12345=second;
                                        if(countDownTimer!=null){
                                            countDownTimer.cancel();}
                                       waitingFunction();
                                    }else{
                                        count = 0;

                                        playAnim(questionTextView, 0, list.get(position).getQuestionTextView());
                                    }
                                }



                            }
                        });
                    } else {
                        finish();
                        Toast.makeText(onevsoneQuizActivity.this, "No Questions", Toast.LENGTH_SHORT).show();

                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(onevsoneQuizActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
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


            num123=1;
            arrlist.add(1);
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1FF88")));   //green color
            selectedOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            selectedOption.setShadowLayer(3,1,1,R.color.lightgreen);
            score++;
        }else {
            //incorrect

            num123=0;
            arrlist.add(2);
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF8888")));     //red color
            selectedOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            selectedOption.setShadowLayer(3,1,1,R.color.lightgreen);
            Button correctOption = (Button) linearLayout.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1FF88")));     //green color
            correctOption.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));
            correctOption.setShadowLayer(3,1,1,R.color.lightred);
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



    public void countDownTimerFun47(){   //Clock Algo
        countDownTimer47=new CountDownTimer(60000*3, 1000) {


            public void onTick(long millisUntilFinished) {



                milliHolder47= (int) millisUntilFinished;

                if(second==0){
                    minutes--;
                    minutestext="0"+String.valueOf(minutes);
                    second=59;
                    if(second<10){
                        secondtext="0"+String.valueOf(second);
                    }else{
                        secondtext=String.valueOf(second);
                    }
                    timerText.setText(" Timer "+minutestext+":"+secondtext+" ");

                }else{
                    minutestext="0"+String.valueOf(minutes);
                    if(second<10){
                        secondtext="0"+String.valueOf(second);
                    }else{
                        secondtext=String.valueOf(second);
                    }
                    timerText.setText(" Timer "+minutestext+":"+secondtext+" ");
                    second--;
                }

            }
            public void onFinish() {
                Toast.makeText(onevsoneQuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                Intent scoreIntent = new Intent(onevsoneQuizActivity.this, onevsoneOnlineScoreCard.class);

                myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).removeValue();
                myRef.child("User").child(mAuth.getCurrentUser().getUid()).child("1vs1onlineOpponentUID").removeValue();
                myRef.child("User").child(opponentUID).child("1vs1onlineOpponentUID").removeValue();
                opponentUID=getIntent().getStringExtra("opponentUID");
                opponentimageUrl=getIntent().getStringExtra("opponentImageUrl");
                opponentUsername=getIntent().getStringExtra("opponentUserName");
                myProPicUrl=getIntent().getStringExtra("mypropic");
                myName=getIntent().getStringExtra("myName");

                myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child("isComplete").removeValue();
                scoreIntent.putExtra("opponentUID",opponentUID);
                scoreIntent.putExtra("opponentImageUrl",opponentimageUrl);
                scoreIntent.putExtra("opponentUserName",opponentUsername);
                scoreIntent.putExtra("mypropic",myProPicUrl);
                scoreIntent.putExtra("myName",myName);
                scoreIntent.putExtra("score", score);
                scoreIntent.putExtra("lifeline",lifelineSum);
                scoreIntent.putExtra("minutes",minutes);
                scoreIntent.putExtra("seconds",second);
                scoreIntent.putExtra("mine",mine);
                scoreIntent.putExtra("minman",0);
                scoreIntent.putExtra("secman",0);
                scoreIntent.putExtra("minutestext",minutestext);
                scoreIntent.putExtra("secondtext",secondtext);
                scoreIntent.putExtra("milliholder",milliHolder);
                scoreIntent.putExtra("category",category);
                scoreIntent.putExtra("imageurl",imageurl);
                scoreIntent.putExtra("oppoScoreCounter",oppoScoreCounter);
                scoreIntent.putIntegerArrayListExtra("myArr", (ArrayList<Integer>) arrlist);
                scoreIntent.putIntegerArrayListExtra("oppoArr", (ArrayList<Integer>) arroppo);
                startActivity(scoreIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                if(countDownTimer47!=null){
                    countDownTimer47.cancel();}
                finish();

            }

        }.start();
    }


    public void countDownTimerFun(){   //Clock Algo
        countDownTimer=new CountDownTimer(60000*3, 1000) {


            public void onTick(long millisUntilFinished) {


                milliHolder=millisUntilFinished;


            }
            public void onFinish() {



            }

        }.start();
    }


    public void countDownTimerFun50(){   //Clock Algo
        countDownTimer50=new CountDownTimer(60000*3, 2000) {


            public void onTick(long millisUntilFinished) {

                opponentDataRetrieving();



            }
            public void onFinish() {



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

    public void onBackPressed() {
        onevsoneQuizActivity.super.onBackPressed();
        finish();

    }

    public void expertAdviceImageManupulator(){     //Aakash changes in this functions are to be done
        Random rand = new Random();
        int num = rand.nextInt(11)+1;

        switch (num){
            case 1:
                expertImage.setBackgroundResource(R.drawable.expert1female);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 2:
                expertImage.setBackgroundResource(R.drawable.expert2male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 3:
                expertImage.setBackgroundResource(R.drawable.expert3male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 4:
                expertImage.setBackgroundResource(R.drawable.expert4male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 5:
                expertImage.setBackgroundResource(R.drawable.expert5male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 6:
                expertImage.setBackgroundResource(R.drawable.expert6female);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 7:
                expertImage.setBackgroundResource(R.drawable.expert7male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 8:
                expertImage.setBackgroundResource(R.drawable.expert8female);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 9:
                expertImage.setBackgroundResource(R.drawable.expert9female);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 10:
                expertImage.setBackgroundResource(R.drawable.expert10male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 11:
                expertImage.setBackgroundResource(R.drawable.expert11male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;
            case 12:
                expertImage.setBackgroundResource(R.drawable.expert12male);
                titleText.setText("Dr. Harry (PhD) is Expert for the day");break;

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
    }



   public void battleGroundFor1vs1Function(int status){

        myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child(String.valueOf(questionNumber)).setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                questionNumber++;
            }
        });

   }

   public void opponentDataRetrieving(){

       myRef.child("battleGround").child("onevsoneOnline").child(opponentUID).child(String.valueOf(binaryPosition)).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               try{
                   opponentAnswer=snapshot.getValue(Integer.class);
                   animManupulation();
                   binaryPosition++;


                  if(bull==1&&binaryPosition==11){
                      try{
                          myRef.child("battleGround").child("onevsoneOnline").child(opponentUID).removeValue();


                      }catch (Exception e){

                      }


                      try{
                          myRef.child("User").child(opponentUID).child("1vs1onlineOpponentUID").removeValue();
                      }catch (Exception e){

                      }


                              Intent scoreIntent = new Intent(onevsoneQuizActivity.this, onevsoneOnlineScoreCard.class);
                              myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child("isComplete").removeValue();
                              scoreIntent.putExtra("opponentUID",opponentUID);
                              scoreIntent.putExtra("opponentImageUrl",opponentimageUrl);
                              scoreIntent.putExtra("opponentUserName",opponentUsername);
                              scoreIntent.putExtra("mypropic",myProPicUrl);
                              scoreIntent.putExtra("myName",myName);
                              scoreIntent.putExtra("score", score);
                              scoreIntent.putExtra("lifeline",lifelineSum);
                              scoreIntent.putExtra("minutes",minman);
                      scoreIntent.putExtra("mine",mine);
                      scoreIntent.putExtra("minman",minman);
                      scoreIntent.putExtra("secman",secman);
                              scoreIntent.putExtra("seconds",secman);
                              scoreIntent.putExtra("minutestext",minutestext);
                              scoreIntent.putExtra("secondtext",secondtext);
                              scoreIntent.putExtra("milliholder",kong);
                              scoreIntent.putExtra("category",category);
                              scoreIntent.putExtra("imageurl",imageurl);
                              scoreIntent.putExtra("oppoScoreCounter",oppoScoreCounter);
                              scoreIntent.putIntegerArrayListExtra("myArr", (ArrayList<Integer>) arrlist);
                              scoreIntent.putIntegerArrayListExtra("oppoArr", (ArrayList<Integer>) arroppo);
                              startActivity(scoreIntent);
                              overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                      if(countDownTimerMine!=null){
                          countDownTimerMine.cancel();}
                              if(countDownTimer47!=null){
                                  countDownTimer47.cancel();}
                              finish();



                  }




                   }catch(Exception e){

                   }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

   }

   public void animManupulation(){
        switch (binaryPosition){
            case 1:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim1.setAnimation(R.raw.tickanim);
                    anim1.playAnimation();
                    anim1.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim1.setAnimation(R.raw.wronganim);
                    anim1.playAnimation();
                    anim1.loop(false);
                }break;
            case 2:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim2.setAnimation(R.raw.tickanim);
                    anim2.playAnimation();
                    anim2.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim2.setAnimation(R.raw.wronganim);
                    anim2.playAnimation();
                    anim2.loop(false);
                }break;
            case 3:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim3.setAnimation(R.raw.tickanim);
                    anim3.playAnimation();
                    anim3.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim3.setAnimation(R.raw.wronganim);
                    anim3.playAnimation();
                    anim3.loop(false);
                }break;
            case 4:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim4.setAnimation(R.raw.tickanim);
                    anim4.playAnimation();
                    anim4.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim4.setAnimation(R.raw.wronganim);
                    anim4.playAnimation();
                    anim4.loop(false);
                }break;
            case 5:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim5.setAnimation(R.raw.tickanim);
                    anim5.playAnimation();
                    anim5.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim5.setAnimation(R.raw.wronganim);
                    anim5.playAnimation();
                    anim5.loop(false);
                }break;
            case 6:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim6.setAnimation(R.raw.tickanim);
                    anim6.playAnimation();
                    anim6.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim6.setAnimation(R.raw.wronganim);
                    anim6.playAnimation();
                    anim6.loop(false);
                }break;
            case 7:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim7.setAnimation(R.raw.tickanim);
                    anim7.playAnimation();
                    anim7.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim7.setAnimation(R.raw.wronganim);
                    anim7.playAnimation();
                    anim7.loop(false);
                }break;
            case 8:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim8.setAnimation(R.raw.tickanim);
                    anim8.playAnimation();
                    anim8.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim8.setAnimation(R.raw.wronganim);
                    anim8.playAnimation();
                    anim8.loop(false);
                }break;
            case 9:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim9.setAnimation(R.raw.tickanim);
                    anim9.playAnimation();
                    anim9.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim9.setAnimation(R.raw.wronganim);
                    anim9.playAnimation();
                    anim9.loop(false);
                }break;
            case 10:
                if(this.opponentAnswer ==1){
                    arroppo.add(1);
                    oppoScoreCounter++;
                    anim10.setAnimation(R.raw.tickanim);
                    anim10.playAnimation();
                    anim10.loop(false);
                }else{
                    arroppo.add(2);
                    oppoWrongAnsCounter++;
                    anim10.setAnimation(R.raw.wronganim);
                    anim10.playAnimation();
                    anim10.loop(false);
                }break;
        }
   }

   public void waitingFunction(){
        myRef.child("battleGround").child("onevsoneOnline").child(opponentUID).child("isComplete").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    isComplete=snapshot.getValue(Integer.class);

                    myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child("isComplete").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent scoreIntent = new Intent(onevsoneQuizActivity.this, onevsoneOnlineScoreCard.class);
                            myRef.child("battleGround").child("onevsoneOnline").child(opponentUID).removeValue();
                            try{
                                myRef.child("User").child(opponentUID).child("1vs1onlineOpponentUID").removeValue();
                            }catch (Exception e){

                            }
                            minman=2-minutes;
                            secman=59-second;
                            mine=" Time Taken : "+(2-minutes)+"min "+(59-second)+"sec ";

                            myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child("isComplete").removeValue();
                            scoreIntent.putExtra("opponentUID",opponentUID);
                            scoreIntent.putExtra("opponentImageUrl",opponentimageUrl);
                            scoreIntent.putExtra("opponentUserName",opponentUsername);
                            scoreIntent.putExtra("mypropic",myProPicUrl);
                            scoreIntent.putExtra("myName",myName);
                            scoreIntent.putExtra("score", score);
                            scoreIntent.putExtra("lifeline",lifelineSum);
                            scoreIntent.putExtra("minutes",minutes);
                            scoreIntent.putExtra("seconds",second);
                            scoreIntent.putExtra("minutestext",minutestext);
                            scoreIntent.putExtra("secondtext",secondtext);
                            scoreIntent.putExtra("milliholder",milliHolder);
                            scoreIntent.putExtra("category",category);
                            scoreIntent.putExtra("imageurl",imageurl);
                            scoreIntent.putExtra("mine",mine);
                            scoreIntent.putExtra("minman",a1);
                            scoreIntent.putExtra("secman",a2);
                            scoreIntent.putExtra("minman",minman);
                            scoreIntent.putExtra("secman",secman);
                            scoreIntent.putExtra("oppoScoreCounter",oppoScoreCounter);
                            scoreIntent.putExtra("oppoWrongAnsCounter",oppoWrongAnsCounter);
                            scoreIntent.putIntegerArrayListExtra("myArr", (ArrayList<Integer>) arrlist);
                            scoreIntent.putIntegerArrayListExtra("oppoArr", (ArrayList<Integer>) arroppo);
                            startActivity(scoreIntent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            if(countDownTimer47!=null){
                                countDownTimer47.cancel();}
                            finish();
                        }
                    });
                }catch (Exception e){
                    myRef.child("battleGround").child("onevsoneOnline").child(mAuth.getCurrentUser().getUid()).child("isComplete").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogBoxManupulation();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   }

   public void countdownMine(){

       countDownTimerMine=new CountDownTimer(1000 * 60 * 3, 1000) {
           @Override
           public void onTick(long l) {
               if(car==60){
                   car=0;
                   minMine++;
               }else{
                   car++;
                   minMine++;
               }
           }

           @Override
           public void onFinish() {

           }
       };
   }

   public void dialogBoxManupulation(){
           builder.setView(view1);
       builder.setCancelable(false);
       propic=((ImageView) view1.findViewById(R.id.exam_img_id));
       yourName=((TextView) view1.findViewById(R.id.userName123));
       ratio=((TextView) view1.findViewById(R.id.ratio));
       timeTaken=((TextView) view1.findViewById(R.id.timeTaken));
       accuracy=((TextView) view1.findViewById(R.id.accuracy));
       lifeLines=((TextView) view1.findViewById(R.id.lifeLines));

       for(int i=1;i<=3;i++){
           dataForHorizontalSlide();
       }


       Glide.with(getBaseContext())
               .load(myProPicUrl)
               .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
               .into(propic);

       yourName.setText(myName);
       int wr=10-score;
       minman=2-minutes;
       secman=59-second;
       a1=minman;
       a2=secman;
       kong= (int) milliHolder;
       mine=" Time Taken : "+(a1)+"min "+(a2)+"sec ";
       ratio.setText(" Correct/Wrong : "+score+"/"+wr+" ");
       timeTaken.setText(mine);
       int acc=score*10;
       accuracy.setText(" Accuracy : "+acc+"% ");
       lifeLines.setText(" Total Life-Lines Used : "+lifelineSum+"/4 ");
       bull=1;

       final AlertDialog alertDialog=builder.create();

       if(alertDialog.getWindow()!=null){
           alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
       }
       alertDialog.show();

   }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    public void dataForHorizontalSlide(){

        // create instance of Random class
        Random rand = new Random();

        // Generate random integers in range 0 to 999

        final int categoryRandomNumber = rand.nextInt(3)+1;  //NEED TO CHANGE HERE
        int setRandomNumber = rand.nextInt(4)+1;   //NEED TO CHANGE HERE



        myRef.child("Facts").child(String.valueOf(categoryRandomNumber)).orderByChild("set").equalTo(setRandomNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    list4.add(dataSnapshot1.getValue(mainMenuFactsHolder.class));
                    cou++;
                }

                if(cou==3){
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    AdapterManupulation();
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(onevsoneQuizActivity.this,"Facts Data Can't be Loaded", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void AdapterManupulation(){
        sliderAdapter=new onevsoneonlineAdapter(onevsoneQuizActivity.this,list4);
        slideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListner);
        sliderAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void addDotsIndicator(int position){
        mDots=new TextView[3];
        dotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.lightgrey));
            dotLayout.addView(mDots[i]);

        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    ViewPager.OnPageChangeListener viewListner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



}