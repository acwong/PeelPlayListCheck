package com.acwong.peelplaylistcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContentCollection {
    @JsonProperty("content")
    private List<Content> contents = new ArrayList<Content>();

    @JsonProperty("preroll")
    private List<PreRoll> preRolls = new ArrayList<PreRoll>();

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<PreRoll> getPreRolls() {
        return preRolls;
    }

    public void setPreRolls(List<PreRoll> preRolls) {
        this.preRolls = preRolls;
    }

    @Override
    public String toString() {
        return "ContentCollection{" +
                "contents=" + contents +
                ", preRolls=" + preRolls +
                '}';
    }
}
