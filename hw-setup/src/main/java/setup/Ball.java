/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package setup;

import java.util.Comparator;

/**
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
public class Ball {

    /**
     * The volume of the Ball.
     */
    private double volume;

    /**
     * Constructor that creates a new ball object with the specified volume.
     *
     * @param volume Volume of the new object.
     */
    public Ball(double volume) {
        this.volume = volume;
    }

    /**
     * Returns the volume of the Ball.
     *
     * @return the volume of the Ball.
     */
    public double getVolume() {
        return volume;
    }

}

/**
 * A subclass for a Comparator that will be used to compare balls for sorting.
 */
class BallComp implements Comparator<Ball> {

    @Override
    public int compare(Ball o1, Ball o2) {
        return Double.compare(o1.getVolume(), o2.getVolume());
    }
}
