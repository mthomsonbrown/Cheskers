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

import com.ookamijin.framework.Graphics.ImageFormat;
import com.ookamijin.framework.Image;

public class AndroidImage implements Image {
	Bitmap bitmap;
	ImageFormat format;

	public AndroidImage(Bitmap bitmap, ImageFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public ImageFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}
}