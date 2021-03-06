package org.woozle.penaltytimer;

import android.content.Context;
import android.widget.Button;
import android.view.View;
import android.view.View.*;

public class JammerButton extends TimerButton
    implements OnClickListener, OnLongClickListener
{
    public boolean penalized = false;
    private JammerButton peer;

    public JammerButton(Context context, Button btn, long now) {
        super(context, btn, now);
    }

    public String str(long remain, boolean tenths) {
        return bstr(remain, tenths) + " ★";
    }

    public void setOther(JammerButton other) {
        peer = other;
    }

    public void expireHook() {
        penalized = true;
    }

    public void onClick(View v) {
        if (peer.running) {
            penalized = true;

            if (peer.penalized) {
                // 6.4.1 -- Add penaltyTime
            } else {
                // 6.4 -- Take penaltyTime away from the other side
                // if it goes negative, that's our time.
                long orem = peer.remaining() - (penaltyTime * 1000);

                if (orem < 0) {
                    set(-orem);
                    start();
                    peer.set(0);
                    peer.stop();
                } else {
                    peer.set(orem);
                }
                return;
            }
        }
        super.onClick(v);
    }

    public void pause() {
        super.pause();
        penalized = false;
    }

    public void pulse(long now) {
        if ((! running) && (! peer.running)) {
            penalized = false;
        }
        super.pulse(now);
    }
}
