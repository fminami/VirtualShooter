package com.example.shooter;

import android.os.Build;

public final class Utils {

	public static boolean isEmulator(){
		return Build.FINGERPRINT.startsWith("generic");
	}
}
