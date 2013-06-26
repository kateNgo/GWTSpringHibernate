package com.ppben.gwtspring.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditCustomerEvent extends GwtEvent<EditCustomerEventHandler> {
	public static Type<EditCustomerEventHandler> TYPE = new Type<EditCustomerEventHandler>();
	private final long id;

	public EditCustomerEvent(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public Type<EditCustomerEventHandler> getAssociatedType() {
		return TYPE;
	}

	
	@Override
	protected void dispatch(EditCustomerEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onEditCustomer(this);
	}

}
