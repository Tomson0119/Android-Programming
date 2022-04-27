package kr.ac.tukorea.gamekim2016180014.dragonflight.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    private static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();
    public static void init() {
        recycleBin.clear();
    }

    public static Recyclable get(Class clazz) {
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if(bin == null || bin.size() == 0) {
            return null;
        }
        return bin.remove(0);
    }

    public static void add(Recyclable object) {
        Class clazz = object.getClass();
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if(bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if(bin.indexOf(object) >= 0) {
            return;
        }
        bin.add(object);
    }
}
