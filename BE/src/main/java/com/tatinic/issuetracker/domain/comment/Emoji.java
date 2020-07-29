package com.tatinic.issuetracker.domain.comment;

import lombok.Getter;

@Getter
public enum Emoji {

    THUMBS_UP("U+1F44D", "thumbsup"),
    THUMBS_DOWN("U+1F44E", "thumbsdown"),
    LAUGH("U+1F923", "laugh"),
    HOORAY("U+1F389", "hooray"),
    CONFUSED("U+1F615", "confused"),
    HEART("U+1F493", "heart"),
    ROCKET("U+1F680", "rocket"),
    EYES("U+1F644", "eyes");

    private String unicode;
    private String name;

    Emoji(String unicode, String name) {
        this.unicode = unicode;
        this.name = name;
    }
}
