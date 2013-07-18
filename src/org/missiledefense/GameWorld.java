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
 * name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from
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

import hermes.World;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

/**
 * User: andrew
 * Date: 7/13/13
 * Time: 6:52 PM
 */
class GameWorld extends World {
    private final PApplet parent;
    private final PImage background;
    private final PImage silo;
    private final MissileGroup missiles;
    private final UfoGroup ufos;

    GameWorld(PApplet parent, int portIn, int portOut) {
        super(portIn, portOut);
        this.parent = parent;

        missiles = new MissileGroup(this, parent);
        ufos = new UfoGroup(this, parent);

        background = parent.loadImage("tower_4.png");
        silo = parent.loadImage("silo.png");
        register(missiles, ufos, new MissileUfoInteractor());
    }

    @Override
    public void setup() {

        Dimension size = parent.getSize();

        Ufo ufo = ufos.addUfo(50, 50);
        missiles.addMissile((size.width / 2) + 51, size.height, ufo.getPosition());
    }

    @Override
    public void draw() {
        parent.image(background, 0, 0);
        super.draw();
        parent.image(silo, 0, 0);
    }
}
