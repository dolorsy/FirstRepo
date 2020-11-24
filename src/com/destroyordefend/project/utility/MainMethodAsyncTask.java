package com.destroyordefend.project.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MainMethodAsyncTask {

    static List<Runnable> doMainThingQueue = new ArrayList<>();
    public static void addMethod(Runnable methodCall) {
        doMainThingQueue.add(methodCall);
    }
    /**
     * From any Class We Will Store Methods Like The Following
     * Runnable methodCall = () ->  MyMethodCall;
     * UpdateMapAsyncTask.addMethod(methodCall);
     *
     * Example
     * Runnable method = () -> invokeUpdatePosition();
     *         updatePositionQueue.add(method);
     */

    public static void invokeMainMethods() {
        for(Runnable updatePosition : doMainThingQueue) {
            updatePosition.run();
        }
    }
    public static void clearQueue(){
        doMainThingQueue.clear();
    }

}
