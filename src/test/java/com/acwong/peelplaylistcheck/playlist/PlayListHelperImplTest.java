package com.acwong.peelplaylistcheck.playlist;

import com.acwong.peelplaylistcheck.id.ContentId;
import com.acwong.peelplaylistcheck.id.VideoTag;
import com.acwong.peelplaylistcheck.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PlayListHelperImplTest {
    private static final String DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE = "videolibrary1.json";

    private PlayListHelperImpl helper;

    @BeforeEach
    public void setup() {
        helper = new PlayListHelperImpl();
    }

    private File getContentCollectionFile(String fileFullPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileFullPath).getFile());
    }

    @Test
    void loadDefaultVideoContentInfo() {
        assertFalse(helper.getContentCollection() != null);

        helper.loadVideoContentInfo();
        assertTrue(helper.getContentCollection() != null);
    }

    @Test
    void loadVideoContentInfo() {
        assertFalse(helper.getContentCollection() != null);

        helper.loadVideoContentInfo(DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE);
        assertTrue(helper.getContentCollection() != null);
    }

    @Test
    void getPlayListsWithDefaultContentCollection() {
        helper.loadVideoContentInfo();

        PlayLists playLists;
        playLists = helper.getPlayLists(ContentId.UNKNOWN.name(), Country.US.name());
        assertTrue(playLists.isEmpty());

        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.ES.name());
        assertTrue(playLists.isEmpty());

        // Incompatible aspect ratio
        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V5.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.UK.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V2.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V7.name(), VideoTag.V3.name()), playLists.get(1));
    }

    @Test
    void getPlayListsWithContentCollectionOverride() {
        helper.loadVideoContentInfo(DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE);

        PlayLists playLists;

        // MI3
        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V5.name(), VideoTag.V25.name(), VideoTag.V1.name()), playLists.get(0));

        playLists = helper.getPlayLists(ContentId.MI3.name(), Country.UK.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V2.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V7.name(), VideoTag.V26.name(), VideoTag.V3.name()), playLists.get(1));

        // MI4
        playLists = helper.getPlayLists(ContentId.MI4.name(), Country.US.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V24.name(), VideoTag.V1.name()), playLists.get(1));

        playLists = helper.getPlayLists(ContentId.MI4.name(), Country.CA.name());
        assertEquals(2, playLists.size());
        assertEquals(new PlayList(VideoTag.V25.name(), VideoTag.V1.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V25.name(), VideoTag.V5.name(), VideoTag.V1.name()), playLists.get(1));

        playLists = helper.getPlayLists(ContentId.MI4.name(), Country.UK.name());
        assertEquals(3, playLists.size());
        assertEquals(new PlayList(VideoTag.V26.name(), VideoTag.V3.name()), playLists.get(0));
        assertEquals(new PlayList(VideoTag.V6.name(), VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(1));
        assertEquals(new PlayList(VideoTag.V26.name(), VideoTag.V7.name(), VideoTag.V3.name()), playLists.get(2));

        // MI5
        playLists = helper.getPlayLists(ContentId.MI5.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = helper.getPlayLists(ContentId.MI5.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = helper.getPlayLists(ContentId.MI5.name(), Country.UK.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(0));

        // MI6
        playLists = helper.getPlayLists(ContentId.MI6.name(), Country.US.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V14.name(), VideoTag.V8.name()), playLists.get(0));

        playLists = helper.getPlayLists(ContentId.MI6.name(), Country.CA.name());
        assertEquals(1, playLists.size());
        assertTrue(playLists.get(0).isEmpty());

        playLists = helper.getPlayLists(ContentId.MI6.name(), Country.UK.name());
        assertEquals(1, playLists.size());
        assertEquals(new PlayList(VideoTag.V16.name(), VideoTag.V2.name()), playLists.get(0));
    }
}