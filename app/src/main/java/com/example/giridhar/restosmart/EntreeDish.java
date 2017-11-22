package com.example.giridhar.restosmart;

/**
 * Created by giridhar on 4/29/17.
 */

public class EntreeDish implements EntreeInterface {

    public final String heading;
    public final String subHeading;

    public EntreeDish(String heading, String subHeading)
    {
        this.heading = heading;
        this.subHeading = subHeading;
    }

    @Override
    public boolean isCategory() {
        return false;
    }

    @Override
    public boolean isDish() {
        return true;
    }
}
