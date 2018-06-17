package com.balbadak.nexquickpro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

public class KeyActivity extends AppCompatActivity {

    Context ctx = this;
    LinearLayout ll1;
    MyView m;
    String url;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      values = new ContentValues();

        setContentView(R.layout.activity_key);
        LinearLayout ll = new LinearLayout(this);// 메인 컨테이너
        ll.setOrientation(LinearLayout.VERTICAL);

        ll1 = new LinearLayout(this);// 캔버스가 담길 레이아웃
        ll1.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll2 = new LinearLayout(this); // 버튼이 담길 레이아웃
        ll2.setOrientation(LinearLayout.HORIZONTAL);

        ll1.setBackgroundColor(Color.WHITE); // 캔버스 바탕

        m = new MyView(ctx); // 커스텀 캔버스

        ll1.addView(m); // 리니어레이아웃에 포함시킴


        Button submitBtn = new Button(ctx); // 제출버튼
        Button clearBtn = new Button(ctx); // 클리어버튼
        submitBtn.setText("SUBMIT");
        clearBtn.setText("CLEAR");

        ll2.addView(submitBtn); //레이아웃에 버튼들 추가
        ll2.addView(clearBtn);

        ll1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200, 1f)); // 레이아웃에 너비, 높이, 가중치 옵션 넣기
        ll2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        ll.addView(ll1); //레이아웃 집어넣기
        ll.addView(ll2);

        setContentView(ll);

        //제출버튼 누르면 동작할 것들
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
                //이거 하고 어느 액티비티로 이동해야할까....? 다시 그 상세보기 있는 그 액티비티로?

                Intent gIntent = getIntent();


                if(gIntent.getIntExtra("callNum",0)==0){ //callNum이 오지 않은 인도
                    Log.e("INFO","인도다!!!");


                        url="http://70.12.109.173:9090/NexQuick/list/updateOrderAfterConfirmbySign.do";
                        values.put("orderNum",gIntent.getIntExtra("orderNum",0));

                    NetworkTask networkTask = new NetworkTask(url, values);
                    networkTask.execute();


                } else { //callNum이 왔다...
                    Log.e("INFO","인수다!!!");
                    url="http://70.12.109.164:9090/NexQuick/list/updateCallAfterConfirmbySign.do";
                    values.put("callNum",gIntent.getIntExtra("callNum",0));
                    NetworkTask networkTask = new NetworkTask(url, values);
                    networkTask.execute();

                }


                Intent i = new Intent(KeyActivity.this,MainActivity.class);
                startActivity(i);


            }
        });

        //클리어 버튼 누르면 동작할 것들
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll1.removeAllViews();
                m = new MyView(ctx); // 커스텀 캔버스
                m.setDrawingCacheEnabled(true);
                ll1.addView(m);
            }
        });

    } // end of onCreate


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }




} // end of class

class MyView extends View {
    Paint paint = new Paint();
    Path path = new Path();    // 자취를 저장할 객체

    public MyView(Context context) {
        super(context);
        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setStrokeWidth(10f); // 선의 굵기 지정
    }

    @Override
    protected void onDraw(Canvas canvas) { // 화면을 그려주는 메서드
        canvas.drawPath(path, paint); // 저장된 path 를 그려라
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // path.lineTo(x, y);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // 자취에 그리지 말고 위치만 이동해라
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y); // 자취에 선을 그려라
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate(); // 화면을 다시그려라

        return true;
    }




}