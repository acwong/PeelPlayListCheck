package com.acwong.peelplaylistcheck.util;

import com.acwong.peelplaylistcheck.PeelPlayListCheck;
import com.acwong.peelplaylistcheck.model.ContentCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {
    private static final String DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE = "videolibrary1.json";

    private FileLoader fileLoader;

    @BeforeEach
    public void setUp() {
        fileLoader = new FileLoader();
    }

    @Test
    public void testLoadFromJson() {
        assertNull(fileLoader.loadFromJson(null, ContentCollection.class));

        assertNotNull(fileLoader.loadFromJson(PeelPlayListCheck.DEFAULT_VIDEO_CONTENT_INFO_FILE,
                ContentCollection.class));
        assertNotNull(fileLoader.loadFromJson(DEFAULT_TEST_VIDEO_CONTENT_INFO_FILE, ContentCollection.class));
    }
}
