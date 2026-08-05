package com.upskillx.model;

public class VideoModel {
    private String title;
    private String uploader;
    private String videoUrl;

    public VideoModel(String title, String uploader, String videoUrl) {
        this.title = title;
        this.uploader = uploader;
        this.videoUrl = videoUrl;
    }

    public String getTitle() { return title; }
    public String getUploader() { return uploader; }
    public String getVideoUrl() { return videoUrl; }
}
