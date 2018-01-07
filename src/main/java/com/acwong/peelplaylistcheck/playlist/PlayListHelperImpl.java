package com.acwong.peelplaylistcheck.playlist;

import com.acwong.peelplaylistcheck.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PlayListHelperImpl implements PlayListHelper {
    private static final String DEFAULT_VIDEO_CONTENT_INFO_FILE = "videolibrary.json";

    private ContentCollection contentCollection;

    /* package */ ContentCollection getContentCollection() {
        return contentCollection;
    }

    @Override
    public boolean loadVideoContentInfo() {
        return loadVideoContentInfo(DEFAULT_VIDEO_CONTENT_INFO_FILE);
    }

    @Override
    public boolean loadVideoContentInfo(String resourceFileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceFileName);

        try {
            ObjectMapper mapper = new ObjectMapper();
            contentCollection = mapper.readValue(inputStream, ContentCollection.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Note if a content can be played with at least one compatible pre-roll, even there can be another
     * specified pre-roll being with incompatible aspect ratio, the incompatibility is ignored and all other valid
     * playlists are returned.
     *
     * @return {@code null} if {code contentId} is not found. Empty list if
     * due to incompatible aspect ratio.
     */
    @Override
    public PlayLists getPlayLists(String contentId, String countryCode) {
        if ((contentCollection == null) || (contentId == null) || (contentId.isEmpty())) {
            return null;
        }

        List<Content> matchingContents = getVideoGroupWithVideosMatchingCountry(contentId, countryCode, contentCollection.getContents(),
                videoGroup -> new Content(videoGroup));
        if (matchingContents == null) {
            return null;
        }

        return getCompatiblePlayLists(matchingContents, countryCode);
    }

    private interface MatchVideoGroupCallback<T> {
        T instantiate(T videoGroup);
    }

    /**
     * @return {@code null} on no suitable content found.
     */
    private <T extends VideoGroup> List<T> getVideoGroupWithVideosMatchingCountry(String videoGroupId,
                                                                                  String countryCode,
                                                                                  List<T> videoGroups,
                                                                                  MatchVideoGroupCallback<T> callback) {
        List<T> matchingVideoGroups = new ArrayList<T>();

        for (T videoGroup : videoGroups) {
            if (videoGroup.getVideoGroupId().equals(videoGroupId)) {
                T matchingVideoGroup = callback.instantiate(videoGroup);
                ListIterator<Video> videoIterator = matchingVideoGroup.getVideos().listIterator();
                while (videoIterator.hasNext()) {
                    Video video = videoIterator.next();
                    boolean hasMatchedCountryCode = false;
                    for (Country country : video.getAttributes().getCountries()) {
                        hasMatchedCountryCode |= countryCode.equals(country.name());
                    }
                    if (!hasMatchedCountryCode) {
                        videoIterator.remove();
                    }
                }
                if (!matchingVideoGroup.getVideos().isEmpty()) {
                    matchingVideoGroups.add(matchingVideoGroup);
                }
            }
        }

        return matchingVideoGroups;
    }

    private PlayLists getCompatiblePlayLists(List<Content> contents, String countryCode) {
        PlayLists allPlayLists = new PlayLists();

        boolean hasIncompatibleAttribute = false;
        for (Content content : contents) {
            for (Video contentVideo : content.getVideos()) {
                PlayList compatiblePlayList = getCompatiblePlayList(contentVideo, content.getPreRollIds(), countryCode);

                hasIncompatibleAttribute |= (compatiblePlayList != null) && compatiblePlayList.isEmpty();

                if ((compatiblePlayList != null) && !compatiblePlayList.isEmpty()) {
                    allPlayLists.add(compatiblePlayList);
                }
            }
        }

        if (allPlayLists.isEmpty() && hasIncompatibleAttribute) {
            allPlayLists.add(new PlayList());
        }

        return allPlayLists;
    }

    private PlayList getCompatiblePlayList(Video contentVideo, List<PreRollId> preRollIds, String countryCode) {
        PlayList playList = new PlayList();

        List<PreRoll> preRolls = new ArrayList<PreRoll>();
        for (PreRollId preRollId : preRollIds) {
            preRolls.addAll(getVideoGroupWithVideosMatchingCountry(preRollId.getVideoGroupId(), countryCode,
                    contentCollection.getPreRolls(), videoGroup -> new PreRoll(videoGroup)));
        }

        // Check preRolls aspect ratio and language
        for (PreRoll preRoll : preRolls) {
            for (Video preRollVideo : preRoll.getVideos()) {
                if (contentVideo.getAttributes().getAspectRatio().equals(preRollVideo.getAttributes().getAspectRatio())
                        && contentVideo.getAttributes().getLanguage().equals(preRollVideo.getAttributes().getLanguage())) {
                    playList.add(preRollVideo.getTag());
                }
            }
        }

        if (!preRolls.isEmpty() && !playList.isEmpty()) {
            // Finally add contentVideo
            playList.add(contentVideo.getTag());
        }

        return playList;
    }
}
