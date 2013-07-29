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
import hermes.Interactor;
import hermes.hshape.HShape;
import processing.core.PApplet;

/**
 * User: andrew
 * Date: 7/15/13
 * Time: 6:51 PM
 */
class MissileUfoInteractor extends Interactor<Missile, Ufo> {
    private final GameWorld   world;
    private final AudioPlayer player;

    MissileUfoInteractor(GameWorld world, PApplet parent) {
        super();

        Minim minim = new Minim(parent);
        this.world = world;
        player = minim.loadFile("rlaunch.wav");
    }

    @Override
    public boolean detect(Missile missile, Ufo ufo) {
        final HShape missileShape = missile.getShape();
        final HShape ufoShape     = ufo.getShape();

        return missileShape.collide(ufoShape);
    }

    // BUG: collision detection fires twice and causes the score to go up twice per actual collision
    @Override
    public void handle(Missile missile, Ufo ufo) {

        world.addExplosion(missile.getX(), missile.getY());

        // Delete missile after impact
        world.delete(missile);

        // Ufo flees off-screen after impact
        ufo.setVelocityX(1600);

        // Increase the number of successful hits
        world.newHit();

        player.rewind();
        player.play();
    }
}
