package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import kr.ac.tukorea.gamekim2016180014.touchandslice.R;

public class AudioPlayer {
    private static final float DEFAULT_VOLUME = 50.0f;
    private static final int MAX_STREAM = 5;
    private static SoundPool soundPool;
    private static HashMap<String, Integer> soundIds;
    private static AudioPlayer instance;

    private AudioPlayer() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(MAX_STREAM).build();
        } else {
            soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
        }
        soundIds = new HashMap<>();
    }

    public static AudioPlayer getInstance() {
        if(instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public void addAudio(Context context, String name, int rawId) {
        int id = soundPool.load(context, rawId, 1);
        soundIds.put(name, id);
    }

    public void playAudio(String name) {
        if(soundIds.containsKey(name)) {
            int id = soundIds.get(name);
            int stream = soundPool.play(id, DEFAULT_VOLUME, DEFAULT_VOLUME, 1, 0, 1.0f);
            soundPool.setVolume(stream, DEFAULT_VOLUME, DEFAULT_VOLUME);
        }
    }


}
