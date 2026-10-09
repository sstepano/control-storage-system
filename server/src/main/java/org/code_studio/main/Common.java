package org.code_studio.main;

import org.springframework.boot.system.ApplicationHome;

public final class Common {

	public Common() {}

	public static String getApplicationHome() {
		ApplicationHome applicationHome = new ApplicationHome();
		return applicationHome.getDir().toString();
	}
	
}
