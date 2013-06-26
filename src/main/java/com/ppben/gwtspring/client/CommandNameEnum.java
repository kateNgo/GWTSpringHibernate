package com.ppben.gwtspring.client;

public enum CommandNameEnum {
	LIST_USER("ListUser"), ADD_USER("AddUser"), EDIT_USER("EditUser"), DELETE_USER(
			"DeleteUser"), UPDATED_USER("UpdatedUser"), ADDED_USER("AddedUser");
	private final String value;

	private CommandNameEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
