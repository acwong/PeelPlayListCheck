package com.acwong.peelplaylistcheck.playlist;

import java.io.File;

public interface PlayListHelper {
    /**
     * Load Peel video contents data with default resource file.
     *
     * @see #loadVideoContentInfo(String)
     */
    boolean loadVideoContentInfo();

    /**
     * Load video contents data with external content file override.
     *
     * @param resourceFileName Peel Content File in JSON format
     * @return {@code true} on success. {@code false} on file read error.
     */
    boolean loadVideoContentInfo(String resourceFileName);

    PlayLists getPlayLists(String contentId, String aspectRatio);
}
