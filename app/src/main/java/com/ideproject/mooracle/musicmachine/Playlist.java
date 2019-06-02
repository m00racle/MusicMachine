package com.ideproject.mooracle.musicmachine;

import com.ideproject.mooracle.musicmachine.models.Song;

/**
 * 3:22
 * Now instead of downloading a single song, let's say we want to download a playlist.
 * In fact, there's an array of songs in the teacher's notes for us to use.
 * Let's go back to the code and create a new class called Playlist.*/

public class Playlist {
    //3:48
    //Then, let's copy and paste the songs array into our new class.
    //Making the songs array public static means we'll be able to access it from
    //anywhere in our app.
    public static Song[] songs = {
            new Song(1, "Little Talks", 180, "Of Monsters and Men", "Label1", 2012, 100, false),
            new Song(2, "The Underdog", 190, "Spoon", "Label2", 2005, 101, false),
            new Song(3, "Diane", 200, "Guster", "Label3", 2002, 102, false),
            new Song(4, "Live and Die", 210, "The Avett Brothers", "Label4", 2013, 103, false),
            new Song(5, "Just the Way You Are", 220, "Billy Joel", "Label5", 1987, 104, false),
            new Song(6, "Down By the Water", 230, "The Decemberists", "Label6", 2013, 105, false),
            new Song(7, "Let It Go", 240, "Idina Menzel", "Disney", 2014, 106, false)
    };
}
