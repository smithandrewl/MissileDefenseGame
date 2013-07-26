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
class Missile extends MassedBeing {

    private final PApplet parent;
    private final PImage sprite;
    private final int    speed;

    private PVector target;
    private float   angle;

    Missile(PApplet parent, PVector location, PVector size, PhysicsInfo physicsInfo, String imgPath)  {
        super(new Rectangle(location.x, location.y, size.x, size.y),
              physicsInfo.getInitVel(),
              physicsInfo.getMass(),
              physicsInfo.getElasticity());

        speed       = physicsInfo.getSpeed();
        this.parent = parent;
        target      = null;
        sprite      = parent.loadImage(imgPath);
    }

    public void launch(PVector target) {
        this.target = target;

        // Update the velocity with the correction
        setVelocity(getUpdatedVector());
    }

    PVector getUpdatedVector() {
        PVector targetVector = new PVector(target.x - getX(), target.y - getY());

        // Get the angle(in degrees) of  the current vector and the target vector
        angle = (float) atan2(targetVector.y, targetVector.x);

        // Amount to change the velocity by to be on course to the target
        return new PVector((float) cos(angle) * speed, (float) sin(angle) * speed);
    }

    @Override
    protected void update() {
        super.update();


    }

    @Override
    public void draw() {
        parent.pushMatrix();

        // The original missile angle(90 degrees) plus the angle of correction
        parent.rotate(parent.radians(90) + angle);
        parent.image(sprite, 0, 0, sprite.width, sprite.height);

        parent.popMatrix();
    }
}
