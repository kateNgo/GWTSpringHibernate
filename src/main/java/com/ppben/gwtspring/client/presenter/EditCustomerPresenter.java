package com.ppben.gwtspring.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.ppben.gwtspring.client.Application;
import com.ppben.gwtspring.client.CommandNameEnum;
import com.ppben.gwtspring.client.event.CustomerUpdatedEvent;
import com.ppben.gwtspring.client.event.EditCustomerCancelledEvent;
import com.ppben.gwtspring.client.presenter.CustomerPresenter.Display;
import com.ppben.gwtspring.shared.dto.CustomerDTO;
import com.ppben.gwtspring.shared.services.CustomerService;
import com.ppben.gwtspring.shared.services.CustomerServiceAsync;

public class EditCustomerPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getSaveButton();

		HasClickHandlers getCancelButton();

		HasClickHandlers getAddMoreButton();

		HasValue<String> getFirstName();

		HasValue<String> getLastName();

		HasValue<String> getAddress();

		HasValue<String> getEmailAddress();

		HasValue<String> getPhone();

		void setResultMessage(String message);

		void setMode(CommandNameEnum command);

		Widget asWidget();

	}

	private CustomerDTO customer;
	private final CustomerServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private CommandNameEnum command;

	public EditCustomerPresenter(CustomerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		super();
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.command = CommandNameEnum.ADD_USER;
		customer = new CustomerDTO();
		this.bind();
	}

	public EditCustomerPresenter(CustomerServiceAsync rpcService,
			HandlerManager eventBus, Display display, long id) {
		super();
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.command = CommandNameEnum.EDIT_USER;
		this.rpcService.findCustomer(id, new AsyncCallback<CustomerDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("edit error");

			}

			@Override
			public void onSuccess(CustomerDTO result) {
				customer = result;
				displayCustomerDetail();
			}
		});
		this.bind();
	}

	private void displayCustomerDetail() {
		this.display.getFirstName().setValue(customer.getFirstName());
		this.display.getLastName().setValue(customer.getLastName());
		this.display.getAddress().setValue(customer.getAddress());
		this.display.getEmailAddress().setValue(customer.getEmailAddress());
		this.display.getPhone().setValue(customer.getPhone());

	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setMessageResult("");
				if (command == CommandNameEnum.ADD_USER) {
					clearFields();
				} else {
					displayCustomerDetail();
				}
			}
		});
		this.display.getAddMoreButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				customer = new CustomerDTO();
				clearFields();
				command = CommandNameEnum.ADD_USER;
				display.setMode(command);
				setMessageResult("");

			}
		});
	}

	private void clearFields() {
		customer = new CustomerDTO();
		this.display.getFirstName().setValue("");
		this.display.getLastName().setValue("");
		this.display.getAddress().setValue("");
		this.display.getEmailAddress().setValue("");
		this.display.getPhone().setValue("");

	}

	private void setMessageResult(String message) {
		this.display.setResultMessage(message);
	}

	private void doSave() {
		/*
		 * customer.setFirstName(display.getFirstName().getValue());
		 * customer.setLastName(display.getLastName().getValue());
		 * customer.setAddress(display.getAddress().getValue());
		 * customer.setEmailAddress(display.getEmailAddress().getValue());
		 * customer.setPhone(display.getPhone().getValue());
		 */
		if (Application.checkCustomerInputForm(display.getFirstName().getValue(), display
				.getLastName().getValue(), display.getAddress().getValue(),
				display.getEmailAddress().getValue(), display.getPhone()
						.getValue())) {
			if (command == CommandNameEnum.ADD_USER) {
				rpcService.saveCustomer(display.getFirstName().getValue(),
						display.getLastName().getValue(), display.getAddress()
								.getValue(), display.getEmailAddress()
								.getValue(), display.getPhone().getValue(),
						new AsyncCallback<CustomerDTO>() {

							@Override
							public void onSuccess(CustomerDTO result) {
								setMessageResult("Saved successfully ");
								customer = result;
								command = CommandNameEnum.EDIT_USER;
								display.setMode(command);

							}

							@Override
							public void onFailure(Throwable arg0) {
								Window.alert("save error");

							}
						});
			} else {
				rpcService.updateCustomer(customer.getId(), display
						.getFirstName().getValue(), display.getLastName()
						.getValue(), display.getAddress().getValue(), display
						.getEmailAddress().getValue(), display.getPhone()
						.getValue(), new AsyncCallback<CustomerDTO>() {

					@Override
					public void onSuccess(CustomerDTO result) {
						// eventBus.fireEvent(new
						// CustomerUpdatedEvent(result));
						setMessageResult("Updated successfully ");
						customer = result;
					}

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("update error");

					}
				});
			}
		}
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	
	
}
