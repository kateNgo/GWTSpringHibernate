package com.ppben.gwtspring.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ppben.gwtspring.shared.dto.CustomerDTO;

public class CustomerUpdatedEvent extends GwtEvent<CustomerUpdatedEventHandler>{
  public static Type<CustomerUpdatedEventHandler> TYPE = new Type<CustomerUpdatedEventHandler>();
  private final CustomerDTO updatedCustomer;
  
  public CustomerUpdatedEvent(CustomerDTO updatedCustomer) {
    this.updatedCustomer = updatedCustomer;
  }
  
  public CustomerDTO getUpdatedCustomer() { return updatedCustomer; }
  

  @Override
  public Type<CustomerUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CustomerUpdatedEventHandler handler) {
    handler.onCustomerUpdated(this);
  }
}
