package com.tech7fox.myolib.processor.classifier;

import java.util.Arrays;

import com.tech7fox.myolib.processor.BaseDataPacket;
import com.tech7fox.myolib.processor.BaseProcessor;
import com.tech7fox.myolib.services.Classifier;
import com.tech7fox.myolib.tools.ByteHelper;
import com.tech7fox.myolib.tools.Logy;

/**
 * @see <a href="https://github.com/thalmiclabs/myo-bluetooth/blob/master/myohw.h#L345">Myo Bluetooth Protocol</a>
 */
public class ClassifierProcessor extends BaseProcessor {
    private static final String TAG = "MyoLib:ClassifierProcessor";

    public ClassifierProcessor() {
        super();
        getSubscriptions().add(Classifier.CLASSIFIEREVENT.getCharacteristicUUID());
    }

    @Override
    protected void doProcess(BaseDataPacket packet) {
        Logy.v(TAG, Arrays.toString(packet.getData()));

        ByteHelper byteHelper = new ByteHelper(packet.getData());
        int typeValue = byteHelper.getUInt8();
        ClassifierEvent event = null;

        if (typeValue == ClassifierEvent.Type.ARM_SYNCED.getValue()) {
            event = new ArmSyncedClassifierEvent(packet);
        } else if (typeValue == ClassifierEvent.Type.ARM_UNSYNCED.getValue()) {
            event = new ClassifierEvent(packet, ClassifierEvent.Type.ARM_UNSYNCED);
        } else if (typeValue == ClassifierEvent.Type.POSE.getValue()) {
            event = new PoseClassifierEvent(packet);
        } else if (typeValue == ClassifierEvent.Type.UNLOCKED.getValue()) {
            event = new ClassifierEvent(packet, ClassifierEvent.Type.UNLOCKED);
        } else if (typeValue == ClassifierEvent.Type.LOCKED.getValue()) {
            event = new ClassifierEvent(packet, ClassifierEvent.Type.LOCKED);
        } else if (typeValue == ClassifierEvent.Type.SYNC_FAILED.getValue()) {
            event = new SyncFailedClassifierEvent(packet);
        } else if (typeValue == ClassifierEvent.Type.WARM_UP_RESULT.getValue()) {
            event = new WarmUpResultClassifierEvent(packet);
        }
        if (event == null) {
            Logy.e(TAG, "Unknown classifier event type!");
            return;
        }
        for (DataListener listener : getDataListeners()) {
            ClassifierEventListener motionEventListener = (ClassifierEventListener) listener;
            motionEventListener.onClassifierEvent(event);
        }
    }

    public interface ClassifierEventListener extends DataListener {

        void onClassifierEvent(ClassifierEvent classifierEvent);

    }

    public void addListener(ClassifierEventListener listener) {
        super.addDataListener(listener);
    }
}
