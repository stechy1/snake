package cz.zcu.fav.ups.snake.model;

import static java.lang.Math.*;

/**
 * Třída představující 2D vektor
 */
@SuppressWarnings({"unused", "SuspiciousNameCombination"})
public final class Vector2D {

    public static final Vector2D ZERO = new Vector2D(0, 0);
    public static final Vector2D UP = new Vector2D(0, -1);
    public static final Vector2D DOWN = new Vector2D(0, 1);
    public static final Vector2D LEFT = new Vector2D(-1, 0);
    public static final Vector2D RIGHT = new Vector2D(1, 0);
    public static final Vector2D ONES = new Vector2D(1, 1);

    public double x;
    public double y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(Vector2D other) {
        this(other.x, other.y);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D RANDOM() {
        int r = (int)round(random()) * 4;

        switch (r) {
            case 0: return UP;
            case 1: return DOWN;
            case 2: return LEFT;
            default: return RIGHT;
        }
    }

    public static Vector2D RANDOM(double min, double max) {
        return RANDOM(min, min, max, max);
    }

    public static Vector2D RANDOM (double minX, double minY, double maxX, double maxY) {
        double x = minX + (int)(random() * maxX);
        double y = minY + (int)(random() * maxY);
        return new Vector2D(x, y);
    }

    public static Vector2D add(Vector2D first, Vector2D second) {
        return add(first, second.x, second.y);
    }

    public static Vector2D add(Vector2D vector, double value) {
        return add(vector, value, value);
    }

    public static Vector2D add(Vector2D vector, double x, double y) {
        return new Vector2D(vector.x + x, vector.y + y);
    }

    public static Vector2D sub(Vector2D first, Vector2D second) {
        return sub(first, second.x, second.y);
    }

    public static Vector2D sub(Vector2D vector, double value) {
        return sub(vector, value, value);
    }

    public static Vector2D sub(Vector2D vector, double x, double y) {
        return new Vector2D(vector.x - x, vector.y - y);
    }

    public static Vector2D mul(Vector2D first, Vector2D second) {
        return mul(first, second.x, second.y);
    }

    public static Vector2D mul(Vector2D vector, double value) {
        return mul(vector, value, value);
    }

    public static Vector2D mul(Vector2D vector, double x, double y) {
        return new Vector2D(vector.x * x, vector.y * y);
    }

    public static Vector2D div(Vector2D first, Vector2D second) {
        return div(first, second.x, second.y);
    }

    public static Vector2D div(Vector2D vector, double value) {
        return div(vector, value, value);
    }

    public static Vector2D div(Vector2D vector, double x, double y) {
        return new Vector2D(vector.x / x, vector.y / y);
    }

    public void set(Vector2D other) {
        set(other.x, other.y);
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D copy() {
        return new Vector2D(this);
    }

    public Vector2D add(Vector2D other) {
        return add(other.x, other.y);
    }

    public Vector2D add(double x) {
        return add(x, x);
    }

    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2D sub(Vector2D other) {
        return sub(other.x, other.y);
    }

    public Vector2D sub(double x) {
        return sub(x, x);
    }

    public Vector2D sub(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2D mul(Vector2D other) {
        return mul(other.x, other.y);
    }

    public Vector2D mul(double x) {
        return mul(x, x);
    }

    public Vector2D mul(double x, double y) {
        this.x *= x;
        this.y *= y;

        return this;
    }

    public Vector2D div(Vector2D other) {
        return div(other.x, other.y);
    }

    public Vector2D div(double x) {
        return div(x, x);
    }

    public Vector2D div(double x, double y) {
        this.x /= x;
        this.y /= y;

        return this;
    }

    public double mag() {
        return Math.sqrt(magSq());
    }

    public double magSq() {
        double x = this.x;
        double y = this.y;

        return (x * x + y * y);
    }

    public double dot(double x, double y) {
        return this.x * x + this.y * y;
    }

    public double cross(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    public double dist(Vector2D other) {
        Vector2D temp = other.copy().sub(this);

        return temp.mag();
    }

    public Vector2D normalize() {
        return this.mag() == 0 ? this : this.div(this.mag());
    }

    public Vector2D limit(double max) {
        double mSq = this.magSq();
        if (mSq > max * max) {
            this.div(Math.sqrt(mSq)); // Normalizace
            this.mul(max);
        }

        return this;
    }

    public Vector2D setMag(double n) {
        return this.normalize().mul(n);
    }

    public Vector2D rotate(double degrees) {
        return rotateRad(degrees * PI / 180);
    }

    public Vector2D rotateRad(double radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        double newX = this.x * cos - this.y * sin;
        double newY = this.x * sin + this.y * cos;

        this.x = newX;
        this.y = newY;

        return this;
    }

    public Vector2D lerp(Vector2D other, double amt) {
        return lerp(other.x, other.y, amt);
    }

    public Vector2D lerp(double x, double y, double amt) {
        this.x += (x - this.x) * amt;
        this.y += (y - this.y) * amt;

        return this;
    }

    public Vector2D constrain(Vector2D min, Vector2D max) {
        return constrain(min.x, min.y, max.x, max.y);
    }

    public Vector2D constrain(double minX, double minY, double maxX, double maxY) {
        if (this.x >= minX && this.x <= maxX && this.y >= minY && this.y <= maxY)
            return this;

        if (this.x < minX)
            this.x = maxX;

        if (this.x > maxX)
            this.x = minX;

        if (this.y < minY)
            this.y = maxY;

        if (this.y > maxY)
            this.y = minY;

        return this;
    }

    @Override
    public String toString() {
        return String.format("Vector2D{x=%s, y=%s}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0) return false;
        return Double.compare(vector2D.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
