package com.hrd.util;

public class ObjectActiveAndCreatedDate {
    public static void registerObject(CommonObjectCreatedDate object){
        registerObject(object, 0L);
    }

    public static void registerObject(CommonObjectCreatedDate object, Long userId){
        object.setStatus(0);
        object.setCreatedBy(userId);
        object.setUpdatedBy(userId);
    }

    public static void updateObject(CommonObjectCreatedDate object, Long userId){
        object.setUpdatedBy(userId);
    }

    public static void deleteObject(CommonObjectCreatedDate object, Long userId){
        object.setStatus(1);
        object.setUpdatedBy(userId);
    }
}
