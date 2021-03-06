/*
 * Copyright (c) 2013, Andrew Smith
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided with the distribution. Neither the
 * name of MissileDefenseGame nor the names of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.missiledefense;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import hermes.Group;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

/**
 * User: andrew
 * Date: 7/15/13
 * Time: 5:43 PM
 */
class MissileGroup extends Group<Missile> {
    private final PApplet     parent;
    private final Dimension   screenSize;
    private final AudioPlayer player;

    MissileGroup(GameWorld w, PApplet parent) {
        super(w);

        this.parent = parent;
        screenSize  = parent.getSize();

        Minim minim = new Minim(parent);
        player = minim.loadFile("flaunch.wav");
    }

    @Override
    public void update() {
        for(Missile missile : getObjects()) {
            PVector position = missile.getPosition();

            boolean offScreen = (position.x > screenSize.getWidth())
                             || (position.y > screenSize.getHeight())
                             || (position.x < 0)
                             || (position.y < 0);

            if(offScreen) {
                ((GameWorld) _world).newEscapee();
                _world.delete(missile);
            }
        }
    }

    Missile addBasicMissile(int x, int y) {
        Missile missile = new BasicMissile(parent, x, y);

        _world.register(missile);
        add(missile);

        return missile;
    }

    Missile addGuidedMissile(int x, int y) {
        Missile missile = new GuidedMissile(parent, x, y);

        _world.register(missile);
        add(missile);

        return missile;
    }

    void playSound() {
        player.rewind();
        player.play();
    }
}
