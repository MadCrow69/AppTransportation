package Model;

import android.database.Cursor;

public class Item {

    private String text, subText;
    public Cursor cursor;
    private boolean isExpandable;

    public Item(String text, String subText, boolean isExpandable) {
        this.text = text;
        this.subText = subText;
        this.isExpandable = isExpandable;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
