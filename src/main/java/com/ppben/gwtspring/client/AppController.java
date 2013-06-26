package com.ppben.gwtspring.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.ppben.gwtspring.client.event.AddCustomerEvent;
import com.ppben.gwtspring.client.event.AddCustomerEventHandler;
import com.ppben.gwtspring.client.event.CustomerUpdatedEvent;
import com.ppben.gwtspring.client.event.CustomerUpdatedEventHandler;
import com.ppben.gwtspring.client.event.EditCustomerCancelledEvent;
import com.ppben.gwtspring.client.event.EditCustomerCancelledEventHandler;
import com.ppben.gwtspring.client.event.EditCustomerEvent;
import com.ppben.gwtspring.client.event.EditCustomerEventHandler;
import com.ppben.gwtspring.client.presenter.CustomerPresenter;
import com.ppben.gwtspring.client.presenter.EditCustomerPresenter;
import com.ppben.gwtspring.client.presenter.Presenter;
import com.ppben.gwtspring.client.view.CustomerView;
import com.ppben.gwtspring.client.view.EditCustomerView;
import com.ppben.gwtspring.shared.services.CustomerServiceAsync;


public class AppController implements Presenter, ValueChangeHandler<String> {

	 private final HandlerManager eventBus;
	  private final CustomerServiceAsync rpcService; 
	  private HasWidgets container;
	  private CommandNameEnum commandName;
	
	
	public AppController(HandlerManager eventBus,
			CustomerServiceAsync rpcService, CommandNameEnum command) {
		super();
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		this.commandName = command ;
		if (command != null){
			History.newItem(command.getValue());
		}
		this.bind();
	}
	private void bind() {
	    History.addValueChangeHandler(this);

	    eventBus.addHandler(AddCustomerEvent.TYPE,
	        new AddCustomerEventHandler() {
	          public void onAddCustomer(AddCustomerEvent event) {
	            doAddNewCustomer();
	          }
	        });  

	    eventBus.addHandler(EditCustomerEvent.TYPE,
	        new EditCustomerEventHandler() {
	          public void onEditCustomer(EditCustomerEvent event) {
	            doEditCustomer(event.getId());
	          }
	        });  
	
	    eventBus.addHandler(EditCustomerCancelledEvent.TYPE,
	            new EditCustomerCancelledEventHandler() {
	              
				@Override
				public void onEditCustomerCancelled(
						EditCustomerCancelledEvent event) {
					// TODO Auto-generated method stub
					 doEditCustomerCancelled();
				}
	            });  
	    eventBus.addHandler(CustomerUpdatedEvent.TYPE,
	            new CustomerUpdatedEventHandler() {

					@Override
					public void onCustomerUpdated(CustomerUpdatedEvent event) {
						doCustomerUpdated();
						
					}
	              
	            });  
	    
	  }
	private void doEditCustomerCancelled(){
		History.newItem(CommandNameEnum.LIST_USER.getValue());
		
	}
	private void doCustomerUpdated(){
		Window.alert("private void doCustomerUpdated()");
		 History.newItem(CommandNameEnum.LIST_USER.getValue());
	}
	private void doAddNewCustomer(){
		 History.newItem(CommandNameEnum.ADD_USER.getValue());
	}
	private void doEditCustomer(long id){
		
		 History.newItem(CommandNameEnum.EDIT_USER.getValue(), false);
		 Presenter presenter = new EditCustomerPresenter(rpcService, eventBus, new EditCustomerView(CommandNameEnum.EDIT_USER), id);
		    presenter.go(container);
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
	    if (token != null) {
	      Presenter presenter = null;

	      if (token.equals(CommandNameEnum.LIST_USER.getValue())) {
	    	 
	        presenter = new CustomerPresenter(rpcService, eventBus, new CustomerView());
	      }
	      else if (token.equals(CommandNameEnum.ADD_USER.getValue())) {
	        presenter = new EditCustomerPresenter(rpcService, eventBus, new EditCustomerView());
	      }
	      else if (token.equals(CommandNameEnum.EDIT_USER.getValue())) {
	        presenter = new EditCustomerPresenter(rpcService, eventBus, new EditCustomerView());
	      }
	      
	      if (presenter != null) {
	        presenter.go(container);
	      }
	    }
		
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
	    
	    if ("".equals(History.getToken())) {
	      History.newItem("list");
	    }
	    else {
	      History.fireCurrentHistoryState();
	    }
		
	}

}
