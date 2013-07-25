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

import hermes.Being;
import hermes.animation.AnimatedSprite;
import hermes.animation.Animation;
import hermes.hshape.Rectangle;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * User: andrew
 * Date: 7/25/13
 * Time: 4:33 PM
 */
public class Explosion extends Being {
    private static int WIDTH    = 68;
    private static int HEIGHT   = 95;
    private static int LIFETIME = 1000;

    private boolean expired;
    private long created;

    private final Animation      animation;
    private final AnimatedSprite sprite;
    private final PApplet        parent;

    boolean isExpired() {
        return expired;
    }

    public Explosion(PApplet parent, PVector location, Animation animation) {
        super(new Rectangle(location.x, location.y, WIDTH, HEIGHT));

        created = System.currentTimeMillis();

        this.parent    = parent;
        this.animation = animation;

        sprite = new AnimatedSprite();
        sprite.addAnimation(animation);
        sprite.setActiveAnimation(0);
    }

    @Override
    public void draw() {
        super.draw();

        if(!expired) {
            parent.image(sprite.animate(), -(WIDTH / 2), -(HEIGHT / 2), WIDTH, HEIGHT);
        }
    }

    @Override
    protected void update() {
        super.update();

        expired = System.currentTimeMillis() - created > LIFETIME;
    }
}
