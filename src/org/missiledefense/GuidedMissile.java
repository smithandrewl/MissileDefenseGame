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

import processing.core.PApplet;
import processing.core.PVector;

/**
 * User: andrew
 * Date: 7/25/13
 * Time: 8:02 PM
 */
public class GuidedMissile extends Missile {

    private static int WIDTH      = 19;
    private static int HEIGHT     = 49;
    private static int MASS       = 01;
    private static int ELASTICITY = 01;
    private static int SPEED      = 300;
    private static int INIT_X_VEL = 00;
    private static int INIT_Y_VEL = 00;

    private static String IMG_PATH = "missile.png";

    GuidedMissile(PApplet parent, int x, int y) {

        super(parent,
              new PVector(x, y),
              new PVector(WIDTH, HEIGHT),
              new PhysicsInfo(new PVector(INIT_X_VEL, INIT_Y_VEL), MASS, ELASTICITY, SPEED),
              IMG_PATH);
    }

    @Override
    protected void update() {
        super.update();

        setVelocity(getUpdatedVector());
    }
}
