/**
 * For Gold Point, You can:
 * Support elastic (or inelastic) collisions.
 * Add the ability to programmatically generate planet images (rather than relying on input image files).
 */
public class NBody {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java NBody <filename>");
            return;
        }
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        Planet[] allOfPlanets = readPlanets(fileName);
        double universR = readRadius(fileName);

        StdAudio.play("../proj0/audio/2001.mid");
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-universR, universR);
        StdDraw.clear();

        for (double time = 0 - dt; time < T; time += dt) {
            double[] xForces = getNetForce(allOfPlanets, 'x');
            double[] yForces = getNetForce(allOfPlanets, 'y');
            for (int i = 0; i < allOfPlanets.length; i++)
                allOfPlanets[i].update(time >= 0 ? dt : 0, xForces[i], yForces[i]);
            StdDraw.picture(0 ,0, "./images/starfield.jpg");
            for (Planet p : allOfPlanets)
                p.draw();
            StdDraw.show();
            StdDraw.pause(50);
        }

        StdOut.printf("%d\n", allOfPlanets.length);
        StdOut.printf("%.2e\n", universR);
        for (Planet allOfPlanet : allOfPlanets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    allOfPlanet.xxPos, allOfPlanet.yyPos, allOfPlanet.xxVel,
                    allOfPlanet.yyVel, allOfPlanet.mass, allOfPlanet.imgFileName);
        }
    }

    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readDouble();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        Planet[] planets = new Planet[in.readInt()];
        in.readDouble();
        for (int i = 0; i < planets.length; i++) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    private static double[] getNetForce(Planet[] planet, char mark) {
        double[] forces = new double[planet.length];
        for (int i = 0; i < planet.length; i++) {
            if (mark == 'x') forces[i] = planet[i].calcNetForceExertedByX(planet);
            else forces[i] = planet[i].calcNetForceExertedByY(planet);
        }
        return forces;
    }
}
