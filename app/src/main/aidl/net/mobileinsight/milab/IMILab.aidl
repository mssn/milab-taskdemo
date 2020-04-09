// IMILab.aidl
package net.mobileinsight.milab;

// Declare any non-default types here with import statements

interface IMILab {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void pauseMI();
    void resumeMI();
    void sendMsg(String strMsg);
    void insertCustomMsg(String strMsg);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
