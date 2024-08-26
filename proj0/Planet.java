public class Planet {
    public double xxPos, yyPos, xxVel, yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p) {
        this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
    }

    public double calcDistance(Planet p) {
        // remember is p -> this force.
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        return G * mass * p.mass / Math.pow(calcDistance(p), 2);
    }

    public double calcForceExertedByX(Planet p) {
        double divisor = (p.xxPos - xxPos) / calcDistance(p);
        return calcForceExertedBy(p) * divisor;
    }

    public double calcForceExertedByY(Planet p) {
        double divisor = (p.yyPos - yyPos) / calcDistance(p);
        return calcForceExertedBy(p) * divisor;
    }

    public double calcNetForceExertedByX(Planet[] plist) {
        double netForce = 0;
        for(Planet p : plist) {
            if (this.equals(p)) continue;
            netForce += calcForceExertedByX(p);
        }
        return netForce;
    }

    public double calcNetForceExertedByY(Planet[] plist) {
        double netForce = 0;
        for(Planet p : plist) {
            if (this.equals(p)) continue;
            netForce += calcForceExertedByY(p);
        }
        return netForce;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel += dt * aX;
        yyVel += dt * aY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
