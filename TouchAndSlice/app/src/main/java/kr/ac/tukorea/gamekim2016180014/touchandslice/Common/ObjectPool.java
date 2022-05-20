package kr.ac.tukorea.gamekim2016180014.touchandslice.Common;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.gamekim2016180014.touchandslice.GameScene.GameObject;

public class ObjectPool {
    private static HashMap<Class, ArrayList<GameObject>> pool;
    private static HashMap<Class, ArrayList<Boolean>> inUse;
    private static ObjectPool instance;

    private ObjectPool() {
        pool = new HashMap<>();
        inUse = new HashMap<>();
    }

    public static ObjectPool getInstance() {
        if(instance == null) {
            instance = new ObjectPool();
        }
        return instance;
    }

    public GameObject get(Class clazz) {
        if(!pool.containsKey(clazz)) {
            pool.put(clazz, new ArrayList<>());
            inUse.put(clazz, new ArrayList<>());
        }

        ArrayList<GameObject> lst = pool.get(clazz);
        int idx = idxNotInUse(clazz);
        if(idx < 0) return null;
        return lst.get(idx);
    }

    public void add(GameObject obj) {
        ArrayList<GameObject> objList = pool.get(obj.getClass());
        ArrayList<Boolean> useList = inUse.get(obj.getClass());
        objList.add(obj);
        useList.add(true);
    }

    public void retrieve(GameObject obj) {
        ArrayList<GameObject> objList = pool.get(obj.getClass());
        ArrayList<Boolean> useList = inUse.get(obj.getClass());
        for(int i=0;i<objList.size();i++) {
            if(obj == objList.get(i)) {
                useList.set(i, false);
            }
        }
    }

    private int idxNotInUse(Class clazz) {
        ArrayList<Boolean> useList = inUse.get(clazz);
        for(int i=0;i<useList.size();i++) {
            if(!useList.get(i)) {
                useList.set(i, true);
                return i;
            }
        }
        return -1;
    }

    public int getCount(Class clazz) {
        if(pool.containsKey(clazz)) {
            ArrayList<GameObject> list = pool.get(clazz);
            if(list == null) {
                return 0;
            }
            return list.size();
        }
        return 0;
    }
}
