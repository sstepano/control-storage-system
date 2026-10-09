package org.code_studio.component;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceBundleBean {

	@Value("${ui.language}")
	String uiLanguage;
	private ResourceBundle bundle;

	public ResourceBundleBean() {
		//this.ctx = ctx;
		this.bundle = null;
	}

	public ResourceBundle getBundle() {
		Locale locale;
		try {
			switch (uiLanguage) {
				case "sr":
						locale = Locale.of("sr", "RS");
					break;
				case "en":
						locale = Locale.of("en", "US");
					break;
					default:
						locale = Locale.of("sr", "RS");
					break;
			}

			bundle = ResourceBundle.getBundle("strings", locale);
			return bundle;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
