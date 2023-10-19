package com.coloring.page;

public class Data {
    int a;
    public boolean clicked = false;
    public int colorSeq;
    public int imageId;
    public String txt;

    public Data(int i, int i2, String str, int i3) {
        this.imageId = i2;
        this.txt = str;
        this.colorSeq = i;
        this.a = i3;
        this.clicked = false;
    }

    public int getColorId() {
        return this.a;
    }

    public int getColorSeq() {
        return this.colorSeq;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getTxt() {
        return this.txt;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setClicked(boolean z) {
        this.clicked = z;
    }

    public void setColorId(int i) {
        this.a = i;
    }

    public void setColorSeq(int i) {
        this.colorSeq = i;
    }

    public void setImageId(int i) {
        this.imageId = i;
    }

    public void setTxt(String str) {
        this.txt = str;
    }
}
