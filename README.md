# myolink
![CI workflow](https://github.com/TECH7Fox/myolink/actions/workflows/gradle.yml/badge.svg)

This is a slighly modified fork from [myolib](https://github.com/d4rken/myolib) by [d4rken](https://github.com/d4rken). 
So far I only added a `writeLEDs` function and updated the SDK and some plugins. Check the commits to see the full changes.

**Huge thank you to [d4rken](https://github.com/d4rken) for your work on the original library and letting us use it!**


This is an Android library to communicate with Myo devices.

It was created for a research project at the [mHealth - Uniklinik RWTH Aachen](https://mhealth.imib.rwth-aachen.de) deparment. The projected required receiving sensor data (Gyro, Accl., EMG) from multiple Myo devices simultaneously, which was not possible at that time (August 2015) through the official Android SDK for Myo from Thalmic Labs.

Contributions are welcome. If you submit pull-requests please adhere to the projects current coding style.
If you are using this library, i would love mention your project here, feel free to contact me.

## Setup
Include this in your apps build.gradle file:
```java
implementation 'com.tech7fox.myolink:myolink:0.0.7'
```

## Examples
### Utilizing callbacks
```java
MyoConnector connector = new MyoConnector(getContext());
connector.scan(5000, new MyoConnector.ScannerCallback() {
    @Override
    public void onScanFinished(List<Myo> myos) {
        Myo myo = myos.get(0);
        myo.connect();
        myo.writeUnlock(MyoCmds.UnlockType.HOLD, new Myo.MyoCommandCallback() {
            @Override
            public void onCommandDone(Myo myo, MyoMsg msg) {
                myo.writeVibrate(MyoCmds.VibrateType.LONG, null);
            }
        });
    }
});
```

### Receiving sensor data
```java
/**
 * ...
 */
Myo myo = myos.get(0);
myo.connect();
EmgProcessor emgProcessor = new EmgProcessor();
myo.addProcessor(emgProcessor);
emgProcessor.addListener(new EmgProcessor.EmgDataListener() {
    @Override
    public void onNewEmgData(EmgData emgData) {
        Log.i("EMG-Data", Arrays.toString(emgData.getData()));
    }
});
```

### Advanced use
```java
BaseMyo baseMyo = new BaseMyo(getContext(), bluetoothDevice);
baseMyo.connect();
ReadMsg readMsg = new ReadMsg(Battery.BATTERYLEVEL, new MyoMsg.Callback() {
    @Override
    public void onResult(MyoMsg msg) {
        byte[] result = ((ReadMsg) msg).getValue();
    }
});
baseMyo.submit(readMsg);
```

### Advanced advanced use
```java
BaseMyo baseMyo = new BaseMyo(getContext(), bluetoothDevice);
baseMyo.connect();
WriteMsg writeMsg = new WriteMsg(
        UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"),
        UUID.fromString("00002A00-0000-1000-8000-00805f9b34fb"),
        null,
        new byte[]{ /*....*/},
        new MyoMsg.Callback() {
            @Override
            public void onResult(MyoMsg msg) {
                if(msg.getGattStatus() == BluetoothGatt.GATT_SUCCESS)
                    Log.i("MYOAPP", "Data written!");
            }
        }

);
baseMyo.submit(writeMsg);
```
### Example app
<img src="./exampleapp/exampleapp.png" height="500">

## License
This library is licensed under Apache 2.0, see [LICENSE](./LICENSE)

If you use "Android Myo library by darken" for your publication, please cite the following publication:
 * Kutafina E, Laukamp D, Jonas SM. Wearable Sensors in Medical Education: Supporting Hand Hygiene Training with a Forearm EMG. Stud Health Technol Inform. 2015;211:286-91.
