package com.github.morningwn.tools.entity;

import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/20 10:22
 */
public class BingImageItemEntity {
    private String startdate;
    private String fullstartdate;
    private String enddate;
    private String url;
    private String urlbase;
    private String copyright;
    private String copyrightlink;
    private String title;
    private String quiz;
    private Boolean wp;
    private String hsh;
    private Integer drk;
    private Integer top;
    private Integer bot;
    private List<String> hs;

    public BingImageItemEntity() {
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getFullstartdate() {
        return fullstartdate;
    }

    public void setFullstartdate(String fullstartdate) {
        this.fullstartdate = fullstartdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlbase() {
        return urlbase;
    }

    public void setUrlbase(String urlbase) {
        this.urlbase = urlbase;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyrightlink() {
        return copyrightlink;
    }

    public void setCopyrightlink(String copyrightlink) {
        this.copyrightlink = copyrightlink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public Boolean getWp() {
        return wp;
    }

    public void setWp(Boolean wp) {
        this.wp = wp;
    }

    public String getHsh() {
        return hsh;
    }

    public void setHsh(String hsh) {
        this.hsh = hsh;
    }

    public Integer getDrk() {
        return drk;
    }

    public void setDrk(Integer drk) {
        this.drk = drk;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getBot() {
        return bot;
    }

    public void setBot(Integer bot) {
        this.bot = bot;
    }

    public List<String> getHs() {
        return hs;
    }

    public void setHs(List<String> hs) {
        this.hs = hs;
    }

    @Override
    public String toString() {
        return "BingImageItemEntity{" +
                "startdate='" + startdate + '\'' +
                ", fullstartdate='" + fullstartdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", url='" + url + '\'' +
                ", urlbase='" + urlbase + '\'' +
                ", copyright='" + copyright + '\'' +
                ", copyrightlink='" + copyrightlink + '\'' +
                ", title='" + title + '\'' +
                ", quiz='" + quiz + '\'' +
                ", wp=" + wp +
                ", hsh='" + hsh + '\'' +
                ", drk=" + drk +
                ", top=" + top +
                ", bot=" + bot +
                ", hs=" + hs +
                '}';
    }
}
