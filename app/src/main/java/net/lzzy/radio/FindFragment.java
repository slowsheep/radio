package net.lzzy.radio;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author Administrator
 */
public class FindFragment extends Fragment {
    private IjkMediaPlayer player;
    private TextView tvStart;
    private TextView tvEnd;
    private SeekBar bar;
    private ImageView imgPrevious;
    private ImageView imgPlay;
    private ImageView imgNext;
    private String[] urls = {"http://lcache.qingting.fm/cache/20190531/4875/4875_20190531_000000_010000_24_0.aac",
            "http://lcache.qingting.fm/cache/20190531/4875/4875_20190531_060000_073000_24_0.aac",
            "http://lcache.qingting.fm/cache/20190531/4875/4875_20190531_073000_090000_24_0.aac"};
    private int position = 0;
    private String playUrl = urls[position];
    private SeekBarHandler handler = new SeekBarHandler();

    private class SeekBarHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (player != null){
                long pos = player.getCurrentPosition();
                setSeekBar(pos);
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        player = AppUtils.getPlayer();
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        initViews(view);
        configPlayer();
        playMusic();
        return view;
    }

    private void setSeekBar(long position){
        bar.setProgress((int) position);
        String pastTime = formatPlayerTime((int) position);
        tvStart.setText(pastTime);
    }

    private void configPlayer() {
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = playOrPause() ? android.R.drawable.ic_media_play
                        : android.R.drawable.ic_media_pause;
                imgPlay.setImageResource(res);
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMusic(false);
            }
        });
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMusic(true);
            }
        });
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void switchMusic(boolean backward) {
        if (backward) {
            if (position > 0) {
                position--;
            } else {
                Toast.makeText(getContext(), "no previous music!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (position < urls.length - 1) {
                position++;
            } else {
                Toast.makeText(getContext(), "it's already the last music", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        playUrl = urls[position];
        playMusic();
    }

    private void playMusic() {
        try {
            player.reset();
            player.setDataSource(playUrl);
            player.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    switchMusic(false);
                }
            });
            player.prepareAsync();
            player.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer p) {
                    Toast.makeText(getContext(), "playing", Toast.LENGTH_SHORT).show();
                    p.start();
                    int max = (int) p.getDuration();
                    if (max > 0) {
                        bar.setMax(max);
                    }
                    String allTime = formatPlayerTime(max);
                    tvEnd.setText(allTime);
                    handler.sendEmptyMessage(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatPlayerTime(int max) {
        max /= 1000;
        int min = max / 60;
        int seconds = max % 60;
        return String.format(Locale.CHINA, "%d:%02d", min, seconds);
    }

    private boolean playOrPause() {
        if (player.isPlaying()) {
            player.pause();
            return true;
        } else {
            player.start();
            return false;
        }
    }

    private void initViews(View view) {
        tvStart = view.findViewById(R.id.tv_start);
        tvEnd = view.findViewById(R.id.tv_end);
        bar = view.findViewById(R.id.bar);
        imgPrevious = view.findViewById(R.id.img_previous);
        imgPlay = view.findViewById(R.id.img_play);
        imgNext = view.findViewById(R.id.img_next);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
