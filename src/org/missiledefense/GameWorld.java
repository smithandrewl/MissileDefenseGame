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
import hermes.postoffice.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * User: andrew
 * Date: 7/13/13
 * Time: 6:52 PM
 */
class GameWorld extends World implements MouseSubscriber, KeySubscriber {
    public static final int UFO_COUNT   = 4;
    public static final int LOWEST_UFO  = 427 ;
    public static final int SLOWEST_UFO = 100;
    public static final int FASTEST_UFO = 400;

    private final PApplet        parent;
    private final PImage         background;
    private final PImage         silo;
    private final MissileGroup   missiles;
    private final UfoGroup       ufos;
    private final ExplosionGroup explosions;

    private int  hits;
    private int  escaped;
    private int  launched;
    private long lastLaunch;

    int getLaunched() {
        return launched;
    }

    void newLaunch() {
        missiles.playSound();
        launched++;
    }

    int getHits() {
        return hits;
    }

    public void newHit() {
        hits++;
    }

    int getScore() {
        return hits * 10;
    }

    public void newEscapee() {
        escaped++;
    }

    double getAccuracy() {
        //Convert to doubles for a cast-free division
        double  hits        = this.hits;
        double  total       = escaped + hits;
        boolean firstLaunch = total == 0;

        // return 0 if this is the first launch
        // to avoid a divide by zero
        if(firstLaunch) { return 0; }

        return hits / total;
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
        subscribe(this, POCodes.Key.SPACE);
        subscribe(this, POCodes.Button.LEFT);
    }

    @Override
    public void update() {
        super.update();

        Dimension screenSize = parent.getSize();
        ArrayList<Ufo> ufoList = ufos.getObjects();

        if(ufos.size() < 2) {
            ufos.addRandomUfos(UFO_COUNT, 0, screenSize.width / 8, 0, LOWEST_UFO, SLOWEST_UFO, FASTEST_UFO);
        } else {
            for(Ufo ufo : ufoList) {
                if(isOffscreen(ufo.getX(), ufo.getY())) {
                    delete(ufo);
                }
            }
        }
    }

    private boolean isOffscreen(float x, float y) {
        Dimension screenSize = parent.getSize();

        boolean xOffscreen = (x < 0) || (x > screenSize.getWidth());
        boolean yOffscreen = (y < 0) || (y > screenSize.getHeight());

        return xOffscreen || yOffscreen;
    }

    @Override
    public void draw() {
        parent.image(background, 0, 0);
        super.draw();
        parent.image(silo, 0, 0);

        String accuracyStr = MessageFormat.format("{0,number,#.##%}", getAccuracy());

        int x        = 10;
        int ySpacing = 15;

        parent.textSize(14);

        parent.text("Click mouse for single fire", 10, ySpacing * 1);
        parent.text("Press spacebar for missile burst", 10, ySpacing * 2);

        // Render the HUD
        parent.text(String.format("Score: %s",    getScore()),    x, ySpacing * 4);
        parent.text(String.format("Launched: %s", getLaunched()), x, ySpacing * 5);
        parent.text(String.format("Accuracy: %s", accuracyStr),   x, ySpacing * 6);
        parent.text(String.format("Hits: %s",     getHits()),     x, ySpacing * 7);

        parent.textSize(10);

    }

    @Override
    public void receive(MouseMessage m) {
        long currentTime = System.currentTimeMillis();

        // The amount of time since the last left mouse button release
        long dt = currentTime - lastLaunch;

        // To prevent a single event from firing 4 times (it does),
        // ignore events within 100 milliseconds of each other.
        if(dt > 100) {
            if(m.getAction() == POCodes.Click.RELEASED) {
                Dimension size  = parent.getSize();
                Missile missile;
                Ufo ufo = ufos.getAt(m.getX(), m.getY());

                if(ufo != null) {
                    missile = missiles.addGuidedMissile((size.width / 2) + 51, size.height);
                    missile.launch(ufo.getPosition());
                } else {
                    missile = missiles.addBasicMissile((size.width / 2) + 51, size.height);
                    missile.launch(new PVector(m.getX(), m.getY()));
                }

                newLaunch();

                lastLaunch = currentTime;
            }
        }
    }

    @Override
    public void receive(KeyMessage m) {
       if(m.isPressed()) {
            Dimension size = parent.getSize();

            for(Ufo ufo : ufos.getObjects()) {
                Missile missile = missiles.addGuidedMissile((size.width / 2) + 51, size.height);
                missile.launch(ufo.getPosition());
            }
        }
    }
}
