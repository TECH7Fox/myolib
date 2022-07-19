/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */

package com.tech7fox.myolib.msgs;

import java.util.Arrays;
import java.util.UUID;

import com.tech7fox.myolib.services.MyoCharacteristic;

/**
 * A subclass of {@link MyoMsg} for reading data.
 */
public class ReadMsg extends MyoMsg {
    private byte[] mValue;

    public ReadMsg(MyoCharacteristic myoCharacteristic, Callback callback) {
        this(myoCharacteristic.getServiceUUID(), myoCharacteristic.getCharacteristicUUID(), callback);
    }

    public ReadMsg(UUID serviceUUID, UUID characteristicUUID, Callback callback) {
        super(serviceUUID, characteristicUUID, callback);
    }

    /**
     * The value that has been read.
     * Initialized with NULL and will be send shortly before the callback is triggered.
     * Depending on {@link #getGattStatus()} this can also be NULL after the callback.
     *
     * @return The value read from the bluetooth device. Can be NULL.
     */
    public byte[] getValue() {
        return mValue;
    }

    /**
     * {@link com.tech7fox.myolib.BaseMyo} will use this method to set the value after the bluetooth transmission returned with {@link android.bluetooth.BluetoothGatt#GATT_SUCCESS}
     * Don't set this yourself.
     *
     */
    public void setValue(byte[] value) {
        mValue = value;
    }

    @Override
    public String toString() {
        return "ReadMsg\n" + "Value: " + Arrays.toString(mValue) + "\n" + super.toString();
    }
}
