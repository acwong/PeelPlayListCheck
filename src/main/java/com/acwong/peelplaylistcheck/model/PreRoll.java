package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PreRoll extends VideoGroup {
    @SuppressWarnings("unused")
    public PreRoll() {
    }

    public PreRoll(PreRoll content) {
        super(content);
    }
}
