/******************************************************************************
 *  Compilation:  javac OneSimpleAttractor.java
 *  Execution:    java OneSimpleAttractor
 *  Dependencies: StdDraw.java
 *
 *  A particle is attracted to the current location of the mouse.
 *  Incorporates drag.
 *
 *
 *  % java OneSimpleAttractor
 *
 *
 *  Credits:  Jeff Traer-Bernstein
 *
 ******************************************************************************/



public class OneSimpleAttractor {
    public static void main(String[] args) {
        double rx = 0.0, ry = 0.0;   // position
        double vx = 0.0, vy = 0.0;   // velocity
        double mass = 1.0;           // mass
        double dt   = 0.5;           // time quantum
        double drag = 0.1;           // mess around with this a bit
        double attractionStrength = 0.01;

        // do the animation
        StdDraw.enableDoubleBuffering();
        while (true) {

            // compute the attractive force to the mouse, accounting for drag
            double dx = StdDraw.mouseX() - rx;
            double dy = StdDraw.mouseY() - ry;
            double fx = (dx * attractionStrength) - (drag * vx);
            double fy = (dy * attractionStrength) - (drag * vy);

            // Euler step: update velocity, then position
            vx += fx * dt / mass;
            vy += fy * dt / mass;
            rx += vx * dt;
            ry += vy * dt;

            // draw particle
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledCircle(rx, ry, 0.02);
            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}