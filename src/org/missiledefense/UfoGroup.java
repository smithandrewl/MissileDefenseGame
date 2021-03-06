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

import hermes.Group;
import hermes.hshape.HShape;
import processing.core.PApplet;

import java.util.Random;

/**
 * User: andrew
 * Date: 7/15/13
 * Time: 6:43 PM
 */
class UfoGroup extends Group<Ufo> {
    private final PApplet parent;
    private final Random  random;

    UfoGroup(GameWorld w, PApplet parent) {
        super(w);

        this.parent = parent;
        this.random = new Random();
    }

    public void addRandomUfos(int count, int minX, int maxX, int minY, int maxY, int minXVel, int maxXVel) {
        for(int i = 0; i < count; i++) {
            addRandomUfo(minX, maxX, minY, maxY, minXVel, maxXVel);
        }
    }

    public Ufo addRandomUfo(int minX, int maxX, int minY, int maxY, int minXVel, int maxXVel) {
        int y    = random.nextInt((maxY    - minY))   + minY;
        int x    = random.nextInt((maxX    - minX))   + minX;
        int xVel = random.nextInt((maxXVel - minXVel) + minXVel);

        return addUfo(x, y, xVel, 0);
    }

    public Ufo addUfo(int x, int y, int xVel, int yVel) {
        Ufo ufo = new Ufo(parent, x, y, xVel, yVel);

        _world.register(ufo);
        add(ufo);

        return ufo;
    }

    public Ufo addUfo(int x, int y) {
        return addUfo(x, y, 0, 0);
    }

    public Ufo getAt(int x, int y) {
        for(Ufo ufo : getObjects()) {
            HShape shape = ufo.getShape();

            if(shape.contains(x, y)) return ufo;
        }

        return null;
    }
}
