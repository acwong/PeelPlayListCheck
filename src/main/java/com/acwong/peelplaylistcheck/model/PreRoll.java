package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreRoll extends VideoGroup {
    @JsonCreator
    public PreRoll(@JsonProperty("name") String videoGroupId, @JsonProperty("videos") List<Video> videos) {
        super(videoGroupId, videos);
    }

    public PreRoll(PreRoll preRoll, List<Video> videos) {
        super(preRoll, videos);
    }
}
