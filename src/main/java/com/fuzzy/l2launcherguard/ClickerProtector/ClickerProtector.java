package com.fuzzy.l2launcherguard.ClickerProtector;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a.kiperku
 * Date: 12.09.2023
 */

public class ClickerProtector implements NativeMouseListener {

    private ClickPoint tempPoint;
    private Integer onePointClick = 0;

    private final Map<ClickPoint, Integer> clickMapEqualsClick = new HashMap<>();
    private Long scriptingClickCheckTime = System.currentTimeMillis();

    public static void main(String[] args) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseListener(new ClickerProtector());
    }


    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        final ClickPoint point = new ClickPoint(nativeEvent.getX(), nativeEvent.getY(), System.currentTimeMillis());
        if (tempPoint == null) {
            tempPoint = point;
            return;
        }
        final double distance = calculateDistance(point, tempPoint);
        final long l = point.getClickTime() - tempPoint.getClickTime();

//        if (distance > 100 && l < 100) {
//            System.out.println("Обнаружен кликер");
//        }
        if (point.equals(tempPoint)) {
            onePointClick++;
            if (onePointClick > 100) {
                System.out.println("Кликер в одну точку");
                onePointClick = 0;
            }
        }
        if (!clickMapEqualsClick.containsKey(point)) {
            clickMapEqualsClick.put(point, 0);
        } else {
            Integer integer = clickMapEqualsClick.get(point);
            clickMapEqualsClick.put(point, ++integer);

            if (System.currentTimeMillis() > scriptingClickCheckTime + 10000){
                final long count = clickMapEqualsClick.entrySet().stream().filter(c -> c.getValue() > 10).count();
                if (count > 2){
                    clickMapEqualsClick.clear();
                    System.out.println("Обнаружены клики по скрипту");
                }
                System.out.println("проверка на скрипт-клики");
                scriptingClickCheckTime = System.currentTimeMillis();
            }
        }
        tempPoint = point;
    }

    private static double calculateDistance(ClickPoint currentPoint, ClickPoint tempPoint) {
        int deltaX = tempPoint.getX() - currentPoint.getX();
        int deltaY = tempPoint.getY() - currentPoint.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
