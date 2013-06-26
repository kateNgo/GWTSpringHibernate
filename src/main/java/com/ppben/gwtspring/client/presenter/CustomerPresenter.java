package com.ppben.gwtspring.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.ppben.gwtspring.client.event.AddCustomerEvent;
import com.ppben.gwtspring.client.event.EditCustomerEvent;
import com.ppben.gwtspring.shared.dto.CustomerDTO;
import com.ppben.gwtspring.shared.services.CustomerServiceAsync;

public class CustomerPresenter implements Presenter {

	private List<CustomerDTO> customers;

	public interface Display {
		HasClickHandlers getDeleteButton();

		HasClickHandlers getEditButton();

		List<CustomerDTO> getList();

		void setData(List<CustomerDTO> data);

		List<Integer> getSelectedRows();

		Widget asWidget();
	}

	private final CustomerServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public CustomerPresenter(CustomerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		super();
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;

	}

	public void bind() {

		display.getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				deleteSelectedCustomers();
			}
		});
		
		display.getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editSelectedCustomers();
			}
		});
		
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());
		fetchCustomerDetails();

	}

	private void fetchCustomerDetails() {

		rpcService.findAll(new AsyncCallback<List<CustomerDTO>>() {

			@Override
			public void onSuccess(List<CustomerDTO> result) {
				customers = result;

				display.setData(customers);

			}

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Error fetching customer details");

			}
		});
	}

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}

	public CustomerDTO getCustomer(int index) {
		return customers.get(index);
	}

	private void deleteSelectedCustomers() {

		List<Integer> selectedRows = display.getSelectedRows();
		ArrayList<Long> ids = new ArrayList<Long>();

		for (int i = 0; i < selectedRows.size(); ++i) {
			ids.add(customers.get(selectedRows.get(i)).getId());

		}
		rpcService.deleteCustomers(ids, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				Window.alert("Error deleting selected customers");
			}

			@Override
			public void onSuccess(Void arg0) {
				fetchCustomerDetails();

			}
		});

	}
	private void editSelectedCustomers() {

		List<Integer> selectedRows = display.getSelectedRows();
		if (selectedRows.size()==1){
			long id = customers.get(selectedRows.get(0)).getId();
			eventBus.fireEvent(new EditCustomerEvent(id));
		}
		

	}

}
