package com.tech7fox.myolib.processor.classifier;


import com.tech7fox.myolib.processor.BaseDataPacket;
import com.tech7fox.myolib.tools.ByteHelper;

public class PoseClassifierEvent extends ClassifierEvent {
    public enum Pose {
        REST((short) 0x0000), FIST((short) 0x0001), WAVE_IN((short) 0x0002), WAVE_OUT((short) 0x0003), FINGERS_SPREAD((short) 0x0004), DOUBLE_TAP((short) 0x0005), UNKNOWN((short) 0xFFFF);

        private final short mValue;

        Pose(short value) {
            mValue = value;
        }

        public short getValue() {
            return mValue;
        }
    }

    private Pose mPose = Pose.UNKNOWN;

    public PoseClassifierEvent(BaseDataPacket packet) {
        super(packet, Type.POSE);
        ByteHelper byteHelper = new ByteHelper(packet.getData());
        int typeValue = byteHelper.getUInt8();
        if (getType().getValue() != typeValue)
            throw new RuntimeException("Incompatible BaseDataPacket:" + typeValue);

        int poseValue = byteHelper.getUInt16();
        for (PoseClassifierEvent.Pose pose : PoseClassifierEvent.Pose.values()) {
            if (pose.getValue() == poseValue) {
                mPose = pose;
                break;
            }
        }
    }


    public Pose getPose() {
        return mPose;
    }
}
