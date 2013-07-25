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
import hermes.postoffice.POCodes;
import processing.core.PApplet;
import processing.core.PImage;

import java.text.MessageFormat;

/**
 * User: andrew
 * Date: 7/13/13
 * Time: 6:52 PM
 */
class GameWorld extends World {
    private final PApplet        parent;
    private final PImage         background;
    private final PImage         silo;
    private final MissileGroup   missiles;
    private final UfoGroup       ufos;
    private final ExplosionGroup explosions;

    private int   hits;
    private int   launches;

    int getHits() {
        return hits;
    }

    public void newHit() {
        hits++;
    }

    int getScore() {
        return hits * 10;
    }

    int getLaunches() {
        return launches;
    }

    public void newLaunch() {
        launches++;
    }

    double getAccuracy() {
        //Convert to doubles for a cast-free division
        double  hits        = this.hits;
        double  launches    = this.launches;
        boolean firstLaunch = launches == 0;

        // return 0 if this is the first launch
        // to avoid a divide by zero
        if(firstLaunch) { return 0; }

        return hits / launches;
    }

    GameWorld(PApplet parent, int portIn, int portOut) {
        super(portIn, portOut);
        this.parent = parent;

        missiles   = new MissileGroup(this, parent);
        ufos       = new UfoGroup(this, parent);
        explosions = new ExplosionGroup(this, parent);

        background = parent.loadImage("tower_4.png");
        silo       = parent.loadImage("silo.png");

        register(missiles, ufos, new MissileUfoInteractor(this, parent));
        register(missiles);
        register(ufos);
        register(explosions);
    }

    public Explosion addExplosion(float x, float y) {
        return explosions.add(x, y);
    }

    @Override
    public void setup() {
        ufos.addUfo(50, 50);
        ufos.addUfo(50, 150);
        ufos.addUfo(150, 150);
        ufos.addUfo(150, 300);
        ufos.addUfo(300, 300);
        ufos.addUfo(300, 450);

        subscribe(missiles, POCodes.Button.LEFT);
    }

    @Override
    public void draw() {
        parent.image(background, 0, 0);
        super.draw();
        parent.image(silo, 0, 0);

        // Render the HUD
        parent.text(String.format("Score: %s", getScore()), 10, 10);
        parent.text(String.format("Launched: %s", getLaunches()), 10, 25);
        parent.text(String.format("Accuracy: %s", MessageFormat.format("{0,number,#.##%}", getAccuracy())), 10, 40);
        parent.text(String.format("Hits: %s", getHits()), 10, 55);
    }
}
