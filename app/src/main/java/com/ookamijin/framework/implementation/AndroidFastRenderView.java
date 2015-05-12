/***********************************************************************************************************************
 * Copyright (C) 2011, Mario Zechner <badlogicgames@gmail.com>
 * Copyright (C) 2013, Michael Brown <mthomsonbrown@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/

package com.ookamijin.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();

	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();

	}

	public void run() {
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running) {
			if (!holder.getSurface().isValid())
				continue;

			float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
			startTime = System.nanoTime();

			if (deltaTime > 3.15) {
				deltaTime = (float) 3.15;
			}

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().paint(deltaTime);

			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);

		}
	}

	public void pause() {
		running = false;
		while (true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}

		}
	}

}