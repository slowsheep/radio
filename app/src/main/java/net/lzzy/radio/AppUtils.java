package net.lzzy.radio;

import android.app.Application;
import android.media.AudioManager;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author lzzy_gxy on 2019/5/31.
 * Description:
 */
public class AppUtils extends Application {
    private static IjkMediaPlayer player;

    public static IjkMediaPlayer getPlayer(){
        if (player == null){
            player = new IjkMediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return player;
    }
}
