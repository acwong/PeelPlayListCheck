package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoGroup {
    private final String videoGroupId;
    private final List<Video> videos;

    @JsonCreator
    public VideoGroup(@JsonProperty("name") String videoGroupId, @JsonProperty("videos") List<Video> videos) {
        this.videoGroupId = videoGroupId;
        this.videos = Collections.unmodifiableList(videos);
    }

    /**
     * Elements in {@code videos} of type {@link Video} are not deep-copied.
     */
    public VideoGroup(VideoGroup videoGroup, List<Video> videos) {
        videoGroupId = videoGroup.getVideoGroupId();
        this.videos = Collections.unmodifiableList((videos == null) ? videoGroup.getVideos() : videos);
    }

    public String getVideoGroupId() {
        return videoGroupId;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        VideoGroup that = (VideoGroup) o;
        return Objects.deepEquals(videoGroupId, that.videoGroupId) &&
                Objects.deepEquals(videos, that.videos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoGroupId, videos);
    }

    @Override
    public String toString() {
        return "VideoGroup{" +
                "videoGroupId='" + videoGroupId + '\'' +
                ", videos=" + videos +
                '}';
    }
}
