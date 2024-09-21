package byog.Core;
import java.util.Objects;

public class Position {
    final int x, y;  // package-private
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position() {
        this(0, 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    /** Return middle position. */
    public Position middlePosition(Position des) {
        int middleX = des.x - this.x;
        int middleY = des.y - this.y;
        return new Position((int) (this.x + 0.5 * middleX), (int) (this.y + 0.5 * middleY));
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }
}
