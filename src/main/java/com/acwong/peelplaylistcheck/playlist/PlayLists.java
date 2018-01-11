package com.acwong.peelplaylistcheck.playlist;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayLists extends ArrayList<PlayList> {
    public PlayLists(PlayList... playLists) {
        super(Arrays.asList(playLists));
    }
}
