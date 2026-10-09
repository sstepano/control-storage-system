package org.code_studio.model;

import java.util.List;

public class JsonResponse <T> {

		private Integer status;
		private String message;
		private List <T> data;

		public JsonResponse () {}

		public Integer getStatus() {
			return this.status;
		}

		public String message() {
			return this.message;
		}

		public List <T> getData() {
			return this.data;
		}
	
}
