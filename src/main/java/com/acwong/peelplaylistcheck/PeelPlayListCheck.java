package com.acwong.peelplaylistcheck;

import com.acwong.peelplaylistcheck.model.ContentCollection;
import com.acwong.peelplaylistcheck.playlist.PlayList;
import com.acwong.peelplaylistcheck.playlist.PlayLists;
import com.acwong.peelplaylistcheck.util.FileLoader;

import static java.lang.System.exit;

public class PeelPlayListCheck {
    public static final String DEFAULT_VIDEO_CONTENT_INFO_FILE = "videolibrary.json";

    /* package */ static final String INPUT_ERROR_MESSAGE = "Input error!";
    /* package */ static final String CONTENT_NOT_SPECIFIED_MESSAGE = "Content not specified or unavailable!";
    /* package */ static final String PLAYLIST_INTERNAL_ERROR_MESSAGE = "Playlist internal error!";

    /**
     * This error message also covers incompatibility with other video attributes apart from aspect ratio.
     */
    /* package */ static final String INCOMPATIBLE_ASPECT_RATIO_ERROR_MESSAGE = "(No legal playlist possible because " +
            "the Pre-Roll Video isn't compatible with the aspect ratio of the Content Video for the US)";
    /* package */ static final String INCOMPATIBLE_ATTRIBUTES_ERROR_MESSAGE = INCOMPATIBLE_ASPECT_RATIO_ERROR_MESSAGE;

    private static final String PLAYLIST_PREFIX = "Playlist";

    public static String generatePlayListsOutputString(PlayList... playLists) {
        if ((playLists == null) || (playLists.length == 0)) {
            return CONTENT_NOT_SPECIFIED_MESSAGE;
        }
        if ((playLists.length == 1) && playLists[0].isEmpty()) {
            return INCOMPATIBLE_ATTRIBUTES_ERROR_MESSAGE;
        }

        String outputString = "";
        int playListIndex = 0;
        for (PlayList playList : playLists) {
            if (playList.isEmpty()) {
                continue;
            }
            playListIndex++;
            outputString += PLAYLIST_PREFIX + playListIndex + "\n"
                    + "{ " + String.join(", ", playList) + " }";

            if (playListIndex < playLists.length) {
                outputString += "\n\n";
            }
        }
        if (playListIndex == 0) {
            return PLAYLIST_INTERNAL_ERROR_MESSAGE;
        }

        return outputString;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println(INPUT_ERROR_MESSAGE);
            exit(-1);
        }

        ContentCollection contentCollection = new FileLoader().loadFromJson(DEFAULT_VIDEO_CONTENT_INFO_FILE,
                ContentCollection.class);
        if (contentCollection == null) {
            System.out.println(CONTENT_NOT_SPECIFIED_MESSAGE);
            return;
        }

        PlayLists playLists = contentCollection.getPlayLists(args[0], args[1]);
        System.out.println(generatePlayListsOutputString(playLists == null ? null :
                playLists.toArray(new PlayList[0])));
    }
}
