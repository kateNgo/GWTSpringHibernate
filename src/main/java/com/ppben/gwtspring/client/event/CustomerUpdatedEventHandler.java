package com.ppben.gwtspring.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface CustomerUpdatedEventHandler extends EventHandler{
  void onCustomerUpdated(CustomerUpdatedEvent event);
}
