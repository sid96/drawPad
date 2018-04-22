package gesturetest.com.example.android.drawpad;

/**
 * Created by This Pc on 1/22/2018.
 */

import android.graphics.Path;

public class FingerPath {

    public int color;
    public int strokeWidth;
    public Path path;

    public FingerPath(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
