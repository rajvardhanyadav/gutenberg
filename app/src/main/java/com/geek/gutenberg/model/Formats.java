package com.geek.gutenberg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Formats {
    @SerializedName("application/x-mobipocket-ebook")
    @Expose
    private String applicationXMobipocketEbook;
    @SerializedName("application/pdf")
    @Expose
    private String applicationPdf;
    @SerializedName("text/plain; charset=us-ascii")
    @Expose
    private String textPlainCharsetUsAscii;
    @SerializedName("text/plain; charset=utf-8")
    @Expose
    private String textPlainCharsetUtf8;
    @SerializedName("application/rdf+xml")
    @Expose
    private String applicationRdfXml;
    @SerializedName("application/zip")
    @Expose
    private String applicationZip;
    @SerializedName("application/epub+zip")
    @Expose
    private String applicationEpubZip;
    @SerializedName("text/html; charset=utf-8")
    @Expose
    private String textHtmlCharsetUtf8;
    @SerializedName("image/jpeg")
    @Expose
    private String imageJpeg;

    public String getApplicationXMobipocketEbook() {
        return applicationXMobipocketEbook;
    }

    public void setApplicationXMobipocketEbook(String applicationXMobipocketEbook) {
        this.applicationXMobipocketEbook = applicationXMobipocketEbook;
    }

    public String getApplicationPdf() {
        return applicationPdf;
    }

    public void setApplicationPdf(String applicationPdf) {
        this.applicationPdf = applicationPdf;
    }

    public String getTextPlainCharsetUsAscii() {
        return textPlainCharsetUsAscii;
    }

    public void setTextPlainCharsetUsAscii(String textPlainCharsetUsAscii) {
        this.textPlainCharsetUsAscii = textPlainCharsetUsAscii;
    }

    public String getTextPlainCharsetUtf8() {
        return textPlainCharsetUtf8;
    }

    public void setTextPlainCharsetUtf8(String textPlainCharsetUtf8) {
        this.textPlainCharsetUtf8 = textPlainCharsetUtf8;
    }

    public String getApplicationRdfXml() {
        return applicationRdfXml;
    }

    public void setApplicationRdfXml(String applicationRdfXml) {
        this.applicationRdfXml = applicationRdfXml;
    }

    public String getApplicationZip() {
        return applicationZip;
    }

    public void setApplicationZip(String applicationZip) {
        this.applicationZip = applicationZip;
    }

    public String getApplicationEpubZip() {
        return applicationEpubZip;
    }

    public void setApplicationEpubZip(String applicationEpubZip) {
        this.applicationEpubZip = applicationEpubZip;
    }

    public String getTextHtmlCharsetUtf8() {
        return textHtmlCharsetUtf8;
    }

    public void setTextHtmlCharsetUtf8(String textHtmlCharsetUtf8) {
        this.textHtmlCharsetUtf8 = textHtmlCharsetUtf8;
    }

    public String getImageJpeg() {
        return imageJpeg;
    }

    public void setImageJpeg(String imageJpeg) {
        this.imageJpeg = imageJpeg;
    }
}
