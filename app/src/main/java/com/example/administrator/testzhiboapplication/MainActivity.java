package com.example.administrator.testzhiboapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.testzhiboapplication.activity.TestInterfaceActivity;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXRtmpApi;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ITXLivePlayListener {
    TXCloudVideoView video_view;
    EditText a;
    String vodUrl = "";
    Button btn_confirm;
    @BindView(R.id.btn_testInterface)
    Button btnTestInterface;
    private int mCurrentRenderRotation;//shuping
    int renderModeResolution;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_begin)
    Button btnBegin;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    @BindView(R.id.btn_player)
    Button btnPlayer;
    TXLivePlayer mLivePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int[] sdkver = TXRtmpApi.getSDKVersion();
        if (sdkver != null && sdkver.length >= 3) {
            Log.d("rtmpsdk", "rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
        }
        a = (EditText) findViewById(R.id.et_geturl);
        video_view = (TXCloudVideoView) findViewById(R.id.video_view);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;// shu  ping

        renderModeResolution = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;//tian chong
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.setPlayerView(video_view);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vodUrl = a.getText().toString();
                Log.d("MainActivity", vodUrl);
                mLivePlayer.setRenderRotation(mCurrentRenderRotation);
                mLivePlayer.setRenderMode(renderModeResolution);//shipei
                mLivePlayer.startPlay(vodUrl, TXLivePlayer.PLAY_TYPE_VOD_MP4);

            }
        });
        //   String vodUrl = "http://2527.vod.myqcloud.com/xxx.flv";


    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            //stopLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS); //进度（秒数）
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION); //时间（秒数）
//            if (!mStartSeek && mSeekBar != null) {
//                mSeekBar.setProgress(progress);
//            }
//            if (mTextStart != null) {
//                mTextStart.setText(String.format("%2d:%2d",progress/60,progress%60));
//            }
//            if (mTextDuration != null) {
//                mTextDuration.setText(String.format("%2d:%2d",duration/60,duration%60));
//            }
//            if (mSeekBar != null) {
//                mSeekBar.setMax(duration);
//            }
            return;
        }
        // 如下这段代码是处理播放结束的事件
        else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT
                || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
//            stopPlayRtmp();
//            mVideoPlay = false;
        }


    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }


    @OnClick({R.id.btn_pause, R.id.btn_begin, R.id.btn_publish, R.id.btn_player})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_pause:
                changerotaion();
                break;
            case R.id.btn_begin:
                changerenderMode();
                break;
            case R.id.btn_publish:
                startActivity(new Intent(this, TestPublishActivity.class));

                break;
            case R.id.btn_player:
                startActivity(new Intent(this, TestPlayerActivity.class));

                break;
        }
    }

    private void changerenderMode() {


        if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_PORTRAIT) {

            mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
        } else if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {

            mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        }

        mLivePlayer.setRenderRotation(mCurrentRenderRotation);


    }

    private void changerotaion() {


        if (renderModeResolution == TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN) {

            renderModeResolution = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        } else if (renderModeResolution == TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION) {

            renderModeResolution = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        }

        mLivePlayer.setRenderMode(renderModeResolution);//shipei
    }

    @OnClick(R.id.btn_testInterface)
    public void onClick() {
        startActivity(new Intent(this, TestInterfaceActivity.class));
    }
}
