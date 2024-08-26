public class BouncingBallDeluxe {
    public static void main(String[] args) {

        // simulation dimensions
        double width = 343720;
        double height = 233333;

        // initial values
        double rx = -width / 2.0, ry = height / 2.0;  // start from top-left corner
        double vx = 1500, vy = -1700;             // velocity
        double radius = 1;                       // radius

        // store initial position
        double initialRx = rx;
        double initialRy = ry;

        // total distance traveled
        double totalDistance = 0.0;

        // counter for total wall collisions
        int collisionCount = 0;

        // set the scale of the coordinate system
        StdDraw.setXscale(-width / 2.0, width / 2.0);
        StdDraw.setYscale(-height / 2.0, height / 2.0);
        StdDraw.enableDoubleBuffering();

        // main animation loop
        while (true) {

            // bounce off wall according to law of elastic collision
            if (Math.abs(rx + vx) + radius > width / 2.0 || Math.abs(rx + vx) - radius < -width / 2.0) {
                vx = -vx;
                collisionCount++;  // count horizontal wall collision
            }
            if (Math.abs(ry + vy) + radius > height / 2.0 || Math.abs(ry + vy) - radius < -height / 2.0) {
                vy = -vy;
                collisionCount++;  // count vertical wall collision
            }

            // calculate distance traveled since last update
            double distanceTraveled = Math.sqrt(vx * vx + vy * vy);
            totalDistance += distanceTraveled;

            // update position
            rx = rx + vx;
            ry = ry + vy;

            // check if the ball has returned to the starting position
            if (Math.abs(rx - initialRx) < 1e-5 && Math.abs(ry - initialRy) < 1e-5) {
                System.out.println("Ball returned to the starting position: (" + rx + ", " + ry + ")");
                System.out.println("Total wall collisions: " + collisionCount);
                System.out.println("Total distance traveled: " + totalDistance);
                break; // exit the loop and end the program
            }

            // draw ball on the screen
            StdDraw.picture(rx, ry, "sun.gif");

            // display and pause for 20ms
            StdDraw.show();
        }
    }
}
