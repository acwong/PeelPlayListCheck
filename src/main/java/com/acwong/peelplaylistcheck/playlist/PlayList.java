package com.acwong.peelplaylistcheck.playlist;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayList extends ArrayList<String> {
    public PlayList(String... contentIds) {
        super(Arrays.asList(contentIds));
    }
}
