package com.acwong.peelplaylistcheck.model;

import com.acwong.peelplaylistcheck.PeelPlayListCheck;
import com.acwong.peelplaylistcheck.id.ContentId;
import com.acwong.peelplaylistcheck.id.VideoTag;
import com.acwong.peelplaylistcheck.playlist.PlayList;
import com.acwong.peelplaylistcheck.playlist.PlayLists;
import com.acwong.peelplaylistcheck.util.FileLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentCollectionTest {
    private static final String DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE = "videolibrary1.json";

    @Test
    public void getPlayListsWithDefaultContentCollection() {
        ContentCollection contentCollection = new FileLoader().loadFromJson(
                PeelPlayListCheck.DEFAULT_VIDEO_CONTENT_INFO_FILE, ContentCollection.class);

        PlayLists playLists;
        playLists = contentCollection.getPlayLists(ContentId.UNKNOWN.name(), Country.US.name());
        assertTrue(playLists.isEmpty());

        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.ES.name());
        assertTrue(playLists.isEmpty());

        // Incompatible aspect ratio
        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V5.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.UK.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V2.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V7.name(), VideoTag.V3.name()), playLists.get(1));
    }

    @Test
    public void getPlayListsWithContentCollectionOverride() {
        ContentCollection contentCollection = new FileLoader().loadFromJson(DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE,
                ContentCollection.class);

        PlayLists playLists;

        // MI3
        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V5.name(), VideoTag.V25.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = contentCollection.getPlayLists(ContentId.MI3.name(), Country.UK.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V2.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V7.name(), VideoTag.V26.name(), VideoTag.V3.name()), playLists.get(1));

        // MI4
        playLists = contentCollection.getPlayLists(ContentId.MI4.name(), Country.US.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(1));

        playLists = contentCollection.getPlayLists(ContentId.MI4.name(), Country.CA.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V25.name(), VideoTag.V1.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V25.name(), VideoTag.V5.name(), VideoTag.V1.name()), playLists.get(1));

        playLists = contentCollection.getPlayLists(ContentId.MI4.name(), Country.UK.name());
        assertEquals(3, playLists.size());
        assertEquals(new PlayList(VideoTag.V26.name(), VideoTag.V3.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(1));
        assertEquals(new PlayList(VideoTag.V26.name(), VideoTag.V7.name(), VideoTag.V3.name()), playLists.get(2));

        // MI5
        playLists = contentCollection.getPlayLists(ContentId.MI5.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = contentCollection.getPlayLists(ContentId.MI5.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = contentCollection.getPlayLists(ContentId.MI5.name(), Country.UK.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(0));

        // MI6
        playLists = contentCollection.getPlayLists(ContentId.MI6.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V14.name(), VideoTag.V8.name()), playLists.get(0));

        playLists = contentCollection.getPlayLists(ContentId.MI6.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = contentCollection.getPlayLists(ContentId.MI6.name(), Country.UK.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(0));
    }
}
