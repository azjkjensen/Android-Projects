package edu.byu.cs.superasteroids.content;

/**
 * Created by Tyler on 1/30/2015.
 *
 * A simple sound class used to help manage the loading and playing of sound files.
 * */
 public class Sound {

     public int id;
     public boolean loading;
     public String filePath;
     public Sound(int id, String filePath) {
        this.filePath = filePath;
        this.id = id;
        loading = true;
     }
 }
