/*
 * Android Myo library by darken
 * Matthias Urhahn (matthias.urhahn@rwth-aachen.de)
 * mHealth - Uniklinik RWTH-Aachen.
 */
package com.tech7fox.myolink.processor;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.List;
import java.util.UUID;

import com.tech7fox.myolink.services.MyoCharacteristic;

/**
 * Interface for processors to be used with {@link com.tech7fox.myolink.BaseMyo#addProcessor(Processor)}.
 * Also see {@link BaseProcessor}.
 */
public interface Processor {
    /**
     * If new data arrives {@link com.tech7fox.myolink.BaseMyo},
     * will {@link #submit(BaseDataPacket)}, if this list contains the UUID of the characteristic delivered by
     * {@link android.bluetooth.BluetoothGattCallback#onCharacteristicChanged(BluetoothGatt, BluetoothGattCharacteristic)}.
     * <br>
     * Don't modify this list live or {@link com.tech7fox.myolink.BaseMyo} might throw a {@link java.util.ConcurrentModificationException}.
     *
     * @return A list of unique characteristic UUIDs from {@link MyoCharacteristic#getCharacteristicUUID()}
     */
    List<UUID> getSubscriptions();

    /**
     * Will be called when new data arrived that this processor is subscribed for.<br>
     * Don't execute expensive routines inside this method!
     * Spending too much time in this method blocks the
     * {@link android.bluetooth.BluetoothGattCallback#onCharacteristicChanged(BluetoothGatt, BluetoothGattCharacteristic)}
     * of {@link com.tech7fox.myolink.BaseMyo} and can lead to packet loss! <br>
     * It is strongly recommended to just add the packet to a data structure and process it on a
     * different thread.
     *
     * @param packet the packet that {@link com.tech7fox.myolink.BaseMyo} created.
     */
    void submit(BaseDataPacket packet);

    /**
     * Called when the processor is added to a Myo. Use it to e.g., start your workers.
     */
    void onAdded();

    /**
     * Called when the processor is removed from a Myo. Use it to e.g., stop your workers.
     */
    void onRemoved();

}
