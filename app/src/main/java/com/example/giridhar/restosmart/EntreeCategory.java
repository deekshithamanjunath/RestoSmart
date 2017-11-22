package com.example.giridhar.restosmart;

/**
 * Created by giridhar on 4/29/17.
 */

public class EntreeCategory implements EntreeInterface {

    private final String heading;

    public EntreeCategory(String heading)
    {
        this.heading = heading;
    }

    public String getHeading()
    {
        return heading;
    }

    @Override
    public boolean isCategory() {
        return true;
    }

    @Override
    public boolean isDish() {
        return false;
    }
}
