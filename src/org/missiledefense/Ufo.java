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

import hermes.hshape.Rectangle;
import hermes.physics.MassedBeing;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * User: andrew
 * Date: 7/14/13
 * Time: 4:08 PM
 */
public class Ufo extends MassedBeing {
    private static final int WIDTH  = 102;
    private static final int HEIGHT = 38;

    private final PApplet parent;
    private final PImage  sprite;

    public Ufo(PApplet parent, int x, int y) {
        this(parent, x, y, 0, 0);
    }

    public Ufo(PApplet parent, int x, int y, int xVel, int yVel) {
        super(new Rectangle(x, y, WIDTH, HEIGHT),
              new PVector(xVel, yVel), 10, 10);

        this.parent = parent;
        this.sprite = parent.loadImage("ufo_pr.png");
    }
    @Override
    public void draw() {
        parent.image(sprite, 0, 0, WIDTH, HEIGHT);
    }
}
