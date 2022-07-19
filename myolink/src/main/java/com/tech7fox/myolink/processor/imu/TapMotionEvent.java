package com.tech7fox.myolink.processor.imu;

import com.tech7fox.myolink.processor.DataPacket;

public class TapMotionEvent extends MotionEvent {
    private int mTapCount;

    public TapMotionEvent(DataPacket packet) {
        super(packet, Type.TAP);
    }

    public int getTapCount() {
        return mTapCount;
    }

    public void setTapCount(int tapCount) {
        mTapCount = tapCount;
    }
}
