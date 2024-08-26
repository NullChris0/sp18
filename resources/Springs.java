/******************************************************************************
 *  Compilation:  javac Springs.java
 *  Execution:    java Springs N
 *  Dependencies: StdDraw.java
 *
 *  Simulates a system of springs.
 *
 *  % java Springs 15
 *
 *  Credits:  Jeff Traer-Bernstein
 *
 ******************************************************************************/


public class Springs {
    public static void main(String[] args) {
        // mess around with this, try 7, 8, 9, 10, 11, 12, 15
        // probably have to turn down the spring force to keep it stable after that...
        int n = 5;

        double[] rx = new double[n];
        double[] ry = new double[n];
        double[] vy = new double[n];
        double[] vx = new double[n];
        double particleMass = 1.0;
        double drag = 0.1;
        double springStrength = 0.1;
        double springLength = 30;
        double gravity = 1.0;
        double timeStep = 0.5;

        // set up the drawing area
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.0025);
        StdDraw.enableDoubleBuffering();

        // initialize the particle positions randomly
        for (int i = 0; i < n; i++) {
            rx[i] = 100 * Math.random();
            ry[i] = 100 * Math.random();
        }


        // do the animation
        while (true) {

            // clear all the forces
            double[] fx = new double[n];
            double[] fy = new double[n];

            // spring forces act between every pairing of particles
            // spring force is proportional to the difference between the rest length of the spring
            // and the distance between the 2 particles it's acting on
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (i == j) continue;

                    // calculate distance between particles i and j
                    double dx = rx[j] - rx[i];
                    double dy = ry[j] - ry[i];
                    double length = Math.sqrt(dx*dx + dy*dy);

                    // figure out the force
                    double force = springStrength * (length - springLength);
                    double springForceX = force * dx / length;
                    double springForceY = force * dy / length;

                    // update the force
                    fx[i] += springForceX;
                    fy[i] += springForceY;
                }
            }

            // add drag force
            // drag is proportional to velocity but in the opposite direction
            for (int i = 0; i < n; i++) {
                fx[i] += -drag * vx[i];
                fy[i] += -drag * vy[i];
            }

            // add gravity forces
            // just add some force pointing down to all of them
            for (int i = 0; i < n; i++) {
                fy[i] += -gravity;
            }

            // fix particle 1 at the mouse position
            rx[0] = StdDraw.mouseX();
            ry[0] = StdDraw.mouseY();
            vx[0] = 0.0;
            vy[0] = 0.0;
            fx[0] = 0.0;
            fy[0] = 0.0;

            // update positions using approximation
            for (int i = 0; i < n; i++) {
                vx[i] += fx[i] * timeStep/particleMass;
                vy[i] += fy[i] * timeStep/particleMass;
                rx[i] += vx[i] * timeStep;
                ry[i] += vy[i] * timeStep;
            }


            // clear
            StdDraw.clear();

            // draw everything
            for (int i = 0; i < n; i++) {
                // draw a circle for each node
                StdDraw.filledCircle(rx[i], ry[i], 1.0);

                // draw the connections between every 2 nodes
                for (int j = 0; j < i; j++) {
                    StdDraw.line(rx[i], ry[i], rx[j], ry[j]);
                }
            }

            // show and wait
            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}