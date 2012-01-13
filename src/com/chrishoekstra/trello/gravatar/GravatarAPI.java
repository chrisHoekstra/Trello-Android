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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GravatarAPI {

	public static final String DEFAULT_IMAGE_404 = "404";
	public static final String DEFAULT_IMAGE_MYSTERY_MAN = "mm";
	public static final String DEFAULT_IMAGE_IDENTICON = "identicon";
	public static final String DEFAULT_IMAGE_MONSTERID = "monsterid";
	public static final String DEFAULT_IMAGE_WAVATAR = "wavatar";
	public static final String DEFAULT_IMAGE_RETRO = "retro";

	private static MessageDigest md5;

	private static MessageDigest getMD5() {
		if (md5 == null) {
			try {
				md5 = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) {
				// MD5 is always available so it is save to hide
				// NoSuchAlgorithmException in a RuntimeException
				// subclass
				throw new UnsupportedOperationException(e);
			}
		}
		return md5;
	}

	public static String getHash(String userEmail) {
		MessageDigest md5 = getMD5();
		byte[] hash;
		byte[] dataToHash = userEmail.trim().toLowerCase().getBytes();
		synchronized (md5) {
			md5.reset();
			hash = md5.digest(dataToHash);
		}
		return bytesToString(hash);
	}

	public static String getAvatarURL(String userEmail) {
		return "http://www.gravatar.com/avatar/" + getHash(userEmail);
	}

    public static String getAvatarURLById(String avatarId) {
        return "http://www.gravatar.com/avatar/" + avatarId;
    }
	
	public static String bytesToString(byte[] hash) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String s = Integer.toHexString(((int) hash[i]) & 0xFF);
			if (s.length() < 2) {
				sb.append("0");
			}
			sb.append(s);
		}
		return sb.toString();
	}

}
