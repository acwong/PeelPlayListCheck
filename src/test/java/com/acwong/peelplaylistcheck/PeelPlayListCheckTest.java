package com.acwong.peelplaylistcheck;

import com.acwong.peelplaylistcheck.id.ContentId;
import com.acwong.peelplaylistcheck.id.VideoTag;
import com.acwong.peelplaylistcheck.model.Country;
import com.acwong.peelplaylistcheck.playlist.PlayList;
import com.acwong.peelplaylistcheck.playlist.PlayListHelper;
import com.acwong.peelplaylistcheck.util.ExitDeniedSecurityManager;
import com.acwong.peelplaylistcheck.util.ExitDeniedSecurityManager.ExitSecurityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeelPlayListCheckTest {
    private static final String TEST_CONTENT_ID_MI3 = "MI3";
    private static final String TEST_CONTENT_ID_MI5 = "MI5";
    private static final String TEST_CONTENT_ID_UNKNOWN = "Any";

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    private PlayListHelper helper;
    private PrintStream consoleOutput;
    private PrintStream consoleError;
    private SecurityManager defaultSecurityManager;

    @BeforeEach
    public void setUpStreams() {
        consoleOutput = System.out;
        consoleError = System.err;
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @BeforeEach
    void setUpExitExpectation() {
        defaultSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new ExitDeniedSecurityManager());
    }

    @AfterEach
    public void tearDownStreams() {
        System.setErr(consoleError);
        System.setOut(consoleOutput);
    }

    @AfterEach
    void tearDownExitExpectation() {
        System.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testNoInputArgumentError() {
        internalTestInputArgumentsError(new String[]{});
    }

    @Test
    public void testMissingInputArgumentsError() {
        internalTestInputArgumentsError(new String[]{ContentId.MI3.name()});
    }

    private void internalTestInputArgumentsError(String[] args) {
        try {
            PeelPlayListCheck.main(args);
        } catch (ExitSecurityException e) {
            assertEquals(-1, e.getStatus(), "Error exit");
        }

        assertEquals(PeelPlayListCheck.INPUT_ERROR_MESSAGE, errorStream.toString().trim());
        assertEquals("", outputStream.toString());
    }

    @Test
    public void testGeneratePlayListOutputString() {
        assertEquals(PeelPlayListCheck.CONTENT_NOT_SPECIFIED_MESSAGE,
                PeelPlayListCheck.generatePlayListsOutputString((PlayList[]) null));
        assertEquals(PeelPlayListCheck.INCOMPATIBLE_ATTRIBUTES_ERROR_MESSAGE,
                PeelPlayListCheck.generatePlayListsOutputString(new PlayList()));

        assertEquals("Playlist1\n"
                + "{ V1 }",
                PeelPlayListCheck.generatePlayListsOutputString(new PlayList(VideoTag.V1.name())));
        assertEquals("Playlist1\n"
                        + "{ V5, V1 }\n\n"
                        + "Playlist2\n"
                        + "{ V2 }\n\n"
                        + "Playlist3\n"
                        + "{ V6, V7, V3 }",
                PeelPlayListCheck.generatePlayListsOutputString(new PlayList(VideoTag.V5.name(), VideoTag.V1.name()),
                        new PlayList(VideoTag.V2.name()),
                        new PlayList(VideoTag.V6.name(), VideoTag.V7.name(), VideoTag.V3.name())));
    }

    @Test
    public void testDefaultLibraryGetPlayListShowIncompatibleAspectRatio() {
        PeelPlayListCheck.main(new String[]{ContentId.MI3.name(), Country.US.name()});

        assertEquals(PeelPlayListCheck.INCOMPATIBLE_ATTRIBUTES_ERROR_MESSAGE, outputStream.toString().trim());
        assertEquals("", errorStream.toString());
    }

    @Test
    public void testDefaultLibraryGetPlayListShowOneList() {
        PeelPlayListCheck.main(new String[]{ContentId.MI3.name(), Country.CA.name()});

        assertEquals(PeelPlayListCheck.generatePlayListsOutputString(
                new PlayList(VideoTag.V5.name(), VideoTag.V1.name())), outputStream.toString().trim());
        assertEquals("", errorStream.toString());
    }

    @Test
    public void testDefaultLibraryGetPlayListShowTwoLists() {
        PeelPlayListCheck.main(new String[]{ContentId.MI3.name(), Country.UK.name()});

        assertEquals(PeelPlayListCheck.generatePlayListsOutputString(
                new PlayList(VideoTag.V6.name(), VideoTag.V2.name()),
                new PlayList(VideoTag.V7.name(), VideoTag.V3.name())),
                outputStream.toString().trim());
        assertEquals("", errorStream.toString());
    }
}
