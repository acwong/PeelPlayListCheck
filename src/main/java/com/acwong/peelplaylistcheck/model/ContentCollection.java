package com.acwong.peelplaylistcheck.model;

import com.acwong.peelplaylistcheck.playlist.PlayList;
import com.acwong.peelplaylistcheck.playlist.PlayLists;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCollection {
    private final List<Content> contents;
    private final List<PreRoll> preRolls;

    @JsonCreator
    public ContentCollection(@JsonProperty("content") List<Content> contents,
                             @JsonProperty("preroll") List<PreRoll> preRolls) {
        this.contents = Collections.unmodifiableList((contents == null) ? new ArrayList<Content>() : contents);
        this.preRolls = Collections.unmodifiableList((preRolls == null) ? new ArrayList<PreRoll>() : preRolls);
    }

    public List<Content> getContents() {
        return contents;
    }

    public List<PreRoll> getPreRolls() {
        return preRolls;
    }

    /**
     * Note if a content can be played with at least one compatible pre-roll, even there can be another
     * specified pre-roll being with incompatible aspect ratio, the incompatibility is ignored and all other valid
     * playlists are returned.
     *
     * @return {@code null} if {code contentId} is not found. Empty list if
     * due to incompatible aspect ratio.
     */
    public PlayLists getPlayLists(String contentId, String countryCode) {
        if ((contentId == null) || (contentId.isEmpty())) {
            return null;
        }

        List<Content> matchingContents = getVideoGroupWithVideosMatchingCountry(contentId, countryCode, getContents(),
                (videoGroup, videos) -> new Content(videoGroup, videos));
        if (matchingContents == null) {
            return null;
        }

        return getCompatiblePlayLists(matchingContents, countryCode);
    }

    private interface MatchVideoGroupCallback<T> {
        T instantiate(T videoGroup, List<Video> videos);
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
                List<Video> matchingVideos = new ArrayList<Video>();
                ListIterator<Video> videoIterator = videoGroup.getVideos().listIterator();
                while (videoIterator.hasNext()) {
                    Video video = videoIterator.next();
                    boolean hasMatchedCountryCode = false;
                    for (Country country : video.getAttributes().getCountries()) {
                        hasMatchedCountryCode |= countryCode.equals(country.name());
                    }
                    if (hasMatchedCountryCode) {
                        matchingVideos.add(video);
                    }
                }
                if (!matchingVideos.isEmpty()) {
                    T matchingVideoGroup = callback.instantiate(videoGroup, matchingVideos);
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
                    getPreRolls(), (videoGroup, videos) -> new PreRoll(videoGroup, videos)));
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

    @Override
    public String toString() {
        return "ContentCollection{" +
                "contents=" + contents +
                ", preRolls=" + preRolls +
                '}';
    }
}
