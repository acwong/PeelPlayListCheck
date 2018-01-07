package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VideoGroup {
    @JsonProperty("name")
    private String videoGroupId;

    @JsonProperty("videos")
    private List<Video> videos = new ArrayList<Video>();

    public VideoGroup() {
    }

    /**
     * Elements in {@code videos} of type {@link Video} are not deep-copied.
     */
    public VideoGroup(VideoGroup content) {
        videoGroupId = content.getVideoGroupId();
        videos.addAll(content.getVideos());
    }

    public String getVideoGroupId() {
        return videoGroupId;
    }

    public void setVideoGroupId(String videoGroupId) {
        this.videoGroupId = videoGroupId;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
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
