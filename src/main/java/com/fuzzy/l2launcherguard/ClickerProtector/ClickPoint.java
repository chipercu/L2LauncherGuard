package com.fuzzy.l2launcherguard.ClickerProtector;

import java.util.Objects;

/**
 * Created by a.kiperku
 * Date: 12.09.2023
 */

public class ClickPoint {
    private int x;
    private int y;
    private Long clickTime;

    public ClickPoint(int x, int y, Long clickTime) {
        this.x = x;
        this.y = y;
        this.clickTime = clickTime;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Long getClickTime() {
        return clickTime;
    }

    public void setClickTime(Long clickTime) {
        this.clickTime = clickTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickPoint that = (ClickPoint) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
