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

import hermes.hshape.Rectangle;
import hermes.physics.MassedBeing;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static java.lang.Math.*;

/**
 * User: andrew
 * Date: 7/13/13
 * Time: 7:02 PM
 */
class Missile extends MassedBeing{

    private static final int   WIDTH  = 19;
    private static final int   HEIGHT = 49;
    private static final float SCALE  = 1.0f;

    private final PApplet parent;
    private final PImage  sprite;
    private final PVector target;
    private float angle;

    public Missile(PApplet parent, int x, int y, PVector target) {
        super(new Rectangle(x, y, WIDTH, HEIGHT), new PVector(0, 0), 10, 10);

        this.parent = parent;
        this.target = target;
        this.angle  = 0;

        sprite = parent.loadImage("missile.png");
    }

    @Override
    protected void update() {
       // Get a vector from missile to target
       PVector vec = new PVector(target.x - getX(), target.y - getY());

       // Get the angle(in degrees) of  the current vector and the target vector
       angle = (float) atan2(vec.y, vec.x);

       // Amount to change the velocity by to be on course to the target
       PVector correction = new PVector((float) cos(angle), (float) sin(angle));

       // Update the velocity with the correction
       setVelocityX((getVelocityX() + correction.x) * SCALE);
       setVelocityY((getVelocityY() + correction.y) * SCALE);
    }

    @Override
    public void draw() {
        parent.image(sprite, 0, 0, WIDTH, HEIGHT);
    }
}
