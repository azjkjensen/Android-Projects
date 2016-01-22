package edu.byu.cs.superasteroids.content;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tyler on 1/30/2015.
 */
public class AudioManagement {

    public AssetManager assets;

    //The singleton instance of this class
    private static volatile AudioManagement instance;
    public static  AudioManagement getInstance() {
        if(instance == null)
            instance = new AudioManagement();
        return instance;
    }

    private AudioManagement(){}

    private int assetIncrement = 0;
    private SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    private Map<Integer, Sound> loadedSounds = new HashMap<>();
    private Map<Integer, MediaPlayer> loopSounds = new HashMap<>();
    private Map<String, MediaPlayer> indexedLoopSounds = new HashMap<>();
    private Map<String, Sound> indexedSounds = new HashMap<>();
    private List<Integer> pausedSounds = new ArrayList<>();

    private SoundPool.OnLoadCompleteListener loadListener = new SoundPool.OnLoadCompleteListener() {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            Sound sound = loadedSounds.get(sampleId);
            if(status == 0) //success
                sound.loading = false;
        }
    };

    /**
     * Loads a looping background sound file into memory. For more details, look at the same function
     * in the content manager.
     * @param filePath
     * @return
     * @throws IOException
     */
    public int loadLoopSound(String filePath) throws IOException {
        if(indexedLoopSounds.containsKey(filePath)) {
            MediaPlayer player = indexedLoopSounds.get(filePath);
            for(Map.Entry<Integer, MediaPlayer> set : loopSounds.entrySet()) {
                if(player.equals(set.getValue()))
                    return set.getKey();
            }
        }
        AssetFileDescriptor afd = assets.openFd(filePath);
        MediaPlayer p = new MediaPlayer();
        p.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        p.setLooping(true);
        afd.close();
        p.prepare();
        loopSounds.put(assetIncrement, p);
        indexedLoopSounds.put(filePath, p);
        int val = assetIncrement++;
        return val;
    }

    /**
     * Plays a looping background sound. For more details, look at the same function in the content
     * manager.
     * @param id
     */
    public void playLoop(int id) {
        if(loopSounds.containsKey(id)) {
            loopSounds.get(id).start();
        }
    }

    /**
     * Pauses a looping background sound. For more details, look at the same function in the content
     * manager.
     * @param id
     */
    public void pauseLoop(int id) {
        if(loopSounds.containsKey(id)) {
            loopSounds.get(id).pause();
        }
    }

    /**
     * Resets a looping background sound. For more details, look at the same function in the content
     * manager.
     * @param id
     */
    public void resetLoop(int id) {
        if(loopSounds.containsKey(id)) {
            loopSounds.get(id).reset();
        }
    }

    /**
     * Unloads a looping background sound from memory. For more details, look at the same function
     * in the content manager.
     * @param id
     */
    public void unloadLoop(int id) {
        if(loopSounds.containsKey(id)) {
            loopSounds.get(id).release();
            loopSounds.remove(id);
        }
    }

    /**
     * Called from the GameActivity's onPause() function. Pauses all loops currently playing.
     */
    public void onPause() {
        pausedSounds.clear();
        for(int i : loopSounds.keySet()) {
            if(loopSounds.get(i).isPlaying()) {
                loopSounds.get(i).pause();
                pausedSounds.add(i);
            }
        }
    }

    /**
     * Called from the GameActivity's onResume() function. Resumes all previously paused loops.
     */
    public void onResume() {
        for(int i : pausedSounds) {
            loopSounds.get(i).start();
        }
    }

    /**
     * Loads a sound effect into memory. For more details, look at the same function
     * in the content manager.
     * @param filePath
     */
    public int loadSound(String filePath) throws IOException {

        soundPool.setOnLoadCompleteListener(loadListener);

        if(indexedSounds.containsKey(filePath)) {
            return indexedSounds.get(filePath).id;
        }
        else {
            AssetFileDescriptor afd = assets.openFd(filePath);
            int id = soundPool.load(afd, 0);
            Sound s = new Sound(id, filePath);
            loadedSounds.put(id, s);
            indexedSounds.put(filePath, s);
            return id;
        }
    }

    /**
     * Unloads a sound effect from memory. For more details, look at the same function
     * in the content manager.
     * @param id
     */
    public void unloadSound(int id) {
        if(loadedSounds.containsKey(id)) {
            soundPool.unload(id);
            indexedSounds.remove(loadedSounds.get(id).filePath);
            loadedSounds.remove(id);
        }
    }

    /**
     * Plays a loaded sound effect. For more details, look at the same function
     * in the content manager.
     * @param id
     */
    public boolean playSound(int id, float volume, float speed) {
       if(loadedSounds.containsKey(id) && !loadedSounds.get(id).loading) {
           //Always play the sound without a loop
           soundPool.play(id, volume, volume, 0, 0, speed);
           return true;
       }
       else {
           return false;
       }
    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }
}
