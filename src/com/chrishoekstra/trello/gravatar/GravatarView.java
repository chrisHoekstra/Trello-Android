/* Copyright (c) 2011 Philippe Bernard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chrishoekstra.trello.gravatar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GravatarView extends LinearLayout {

	public static final String GRAVATAR_VIEW_XML_NAMESPACE = "https://github.com/phbernard/android-gravatar-view/schemas";
	public static final String GRAVATAR_URL = "url";
	public static final String GRAVATAR_EMAIL = "email";
	public static final String GRAVATAR_RESCUE_DRAWABLE = "rescue_drawable";
	public static final String GRAVATAR_DEFAULT_IMAGE_TYPE = "default_image_type";

	private static final int COMPLETE = 0;
	private static final int FAILED = 1;

	private Context mContext;
	private Drawable mDrawable;
	private ImageView mImage;
    private TextView mText;
	
	// Avatar data
	private String avatarURL;
	private String avatarEmail;
	private String defaultImageType;
	
	// In case of failure
	private int rescueDrawableId;
	private Drawable rescueDrawable;

	private boolean sizeKnown = false;

	private int width, height;

	public GravatarView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);

		rescueDrawableId = attrSet.getAttributeResourceValue(
				GravatarView.GRAVATAR_VIEW_XML_NAMESPACE,
				GravatarView.GRAVATAR_RESCUE_DRAWABLE, -1);
		defaultImageType = attrSet.getAttributeValue(
				GravatarView.GRAVATAR_VIEW_XML_NAMESPACE,
				GravatarView.GRAVATAR_DEFAULT_IMAGE_TYPE);
		avatarURL = attrSet.getAttributeValue(
				GravatarView.GRAVATAR_VIEW_XML_NAMESPACE,
				GravatarView.GRAVATAR_URL);
		avatarEmail = attrSet.getAttributeValue(
				GravatarView.GRAVATAR_VIEW_XML_NAMESPACE,
				GravatarView.GRAVATAR_EMAIL);

		initialize(context);
	}

	public GravatarView(final Context context) {
		super(context);
		initialize(context);
	}

	private void initialize(final Context context) {
		mContext = context;

		setGravity(Gravity.CENTER);

		mImage = new ImageView(mContext);
		mImage.setScaleType(ScaleType.FIT_XY);
		mImage.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		mText = new TextView(mContext);
		mText.setTextColor(0xFF8F8F8F);
		mText.setTextSize(12);
		mText.setTypeface(Typeface.DEFAULT_BOLD);
		mText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
		
		addView(mImage);
		addView(mText);

		getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (!sizeKnown) {
							width = getWidth();
							height = getHeight();
							sizeKnown = true;
							loadImage();
						}
					}
				});
	}

	private void loadImage() {
		String url = getAvatarURL();
		if (sizeKnown && (url != null)) {
			setImageDrawable(addURLParameter(url, "s",
					Integer.toString(Math.min(width, height))));
		}
	}

	public String getAvatarURL() {
		if (avatarURL != null) {
			return avatarURL;
		} else if (avatarEmail != null) {
			return addURLParameter(GravatarAPI.getAvatarURL(avatarEmail), "d",
					getDefaultImageType());
		}
		return null;
	}

	public void setInitials(String initials) {
	    mText.setText(initials);
	}
	
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
		loadImage();
	}

	public String getAvatarEmail() {
		return avatarEmail;
	}

	public void setAvatarEmail(String avatarEmail) {
		this.avatarEmail = avatarEmail;
		loadImage();
	}

	public void setDefaultImageType(String defaultImageType) {
		this.defaultImageType = defaultImageType;
	}

	public String getDefaultImageType() {
		return defaultImageType;
	}

	private void setImageDrawable(final String imageUrl) {
		mDrawable = null;
		mImage.setVisibility(View.GONE);
		new Thread() {
			public void run() {
				try {
					mDrawable = getDrawableFromURL(imageUrl);
					imageLoadedHandler.sendEmptyMessage(COMPLETE);
				} catch (MalformedURLException e) {
					imageLoadedHandler.sendEmptyMessage(FAILED);
				} catch (IOException e) {
					imageLoadedHandler.sendEmptyMessage(FAILED);
				}
			};
		}.start();
	}

	private final Handler imageLoadedHandler = new Handler(
			new Handler.Callback() {

				@Override
				public boolean handleMessage(Message msg) {
					switch (msg.what) {
					case COMPLETE:
						mImage.setImageDrawable(mDrawable);
						mImage.setVisibility(View.VISIBLE);
						mText.setVisibility(View.GONE);
						break;
					case FAILED:
					default:
						if (rescueDrawableId > 0) {
							if (rescueDrawable == null) {
								rescueDrawable = getResources().getDrawable(
										rescueDrawableId);
							}
							mImage.setImageDrawable(rescueDrawable);
							mImage.setVisibility(View.VISIBLE);
							mText.setVisibility(View.GONE);
						}
						break;
					}
					return true;
				}
			});

	private static Drawable getDrawableFromURL(final String url)
			throws IOException, MalformedURLException {
		return Drawable.createFromStream(
				((InputStream) new URL(url).getContent()), "Avatar picture");
	}

	private String addURLParameter(String url, String paramName,
			String paramValue) {
		if (paramValue == null) {
			return url;
		}
		return url + (url.contains("?") ? "&" : "?") + paramName + "="
				+ paramValue;
	}

}
