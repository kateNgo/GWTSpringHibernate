package com.ppben.gwtspring.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ppben.gwtspring.client.CommandNameEnum;
import com.ppben.gwtspring.client.presenter.EditCustomerPresenter;

public class EditCustomerView extends Composite implements EditCustomerPresenter.Display{
	 private final TextBox firstName;
	  private final TextBox lastName;
	  private final TextBox emailAddress;
	  private final TextBox address;
	  private final TextBox phone;
	  private final FlexTable detailsTable;
	  private final Button saveButton;
	  private final Button addMoreButton;
	  private final Button cancelButton;
	  private final Label resultMessage;
	  private CommandNameEnum command;
	  public EditCustomerView(){
		  this(CommandNameEnum.ADD_USER);
	  }
	  public EditCustomerView(CommandNameEnum command){
		this.command = command;
		  DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
		    initWidget(contentDetailsDecorator);

		    VerticalPanel contentDetailsPanel = new VerticalPanel();
		    contentDetailsPanel.setWidth("100%");

		    // Create the contacts list
		    //
		    detailsTable = new FlexTable();
		    detailsTable.setCellSpacing(5);
		    detailsTable.setWidth("100%");
		    detailsTable.addStyleName("customer-ListContainer");
		    detailsTable.getColumnFormatter().addStyleName(0,"add-customer-label");
		    detailsTable.getColumnFormatter().addStyleName(1, "add-customer-input");
		    
		    firstName = new TextBox();
		    firstName.getElement().setId("firstName");
		    lastName = new TextBox();
		    lastName.getElement().setId("lastName");
		    emailAddress = new TextBox();
		    emailAddress.getElement().setId("emailAddress");
		    address = new TextBox();
		    address.getElement().setId("address");
		    phone = new TextBox();
		    phone.getElement().setId("phone");
		    initDetailsTable();
		    contentDetailsPanel.add(detailsTable);
		    
		    HorizontalPanel menuPanel = new HorizontalPanel();
		    menuPanel.addStyleName("add-customer-input-buttons");
		    menuPanel.setSpacing(5);
		    addMoreButton = new Button("Add More");
		    if(command == CommandNameEnum.ADD_USER){
		    	cancelButton = new Button("Clear");
		    	saveButton = new Button("Save");
		    }else{ 
		    	cancelButton = new Button("Cancel");
		    	saveButton = new Button("Update");
		    }
		   // saveButton.getElement().setAttribute("onclick","window.test();");
		    resultMessage = new Label();
		    resultMessage.addStyleName("resultMessage");
		    menuPanel.add(saveButton);
		    menuPanel.add(cancelButton);
		    menuPanel.add(addMoreButton);
		    addMoreButton.setVisible(false);
		    menuPanel.add(resultMessage);
		    contentDetailsPanel.add(menuPanel);
		    contentDetailsDecorator.add(contentDetailsPanel);
	  }
	  private void initDetailsTable() {
		    detailsTable.setWidget(0, 0, new Label("Firstname"));
		    detailsTable.setWidget(0, 1, firstName);
		    detailsTable.setWidget(1, 0, new Label("Lastname"));
		    detailsTable.setWidget(1, 1, lastName);
		    detailsTable.setWidget(2, 0, new Label("Address"));
		    detailsTable.setWidget(2, 1, address);
		    detailsTable.setWidget(3, 0, new Label("Email Address"));
		    detailsTable.setWidget(3, 1, emailAddress);
		    detailsTable.setWidget(4, 0, new Label("Phone"));
		    detailsTable.setWidget(4, 1, phone);
		    firstName.setFocus(true);
		  }
	@Override
	public HasClickHandlers getSaveButton() {
		// TODO Auto-generated method stub
		return saveButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		// TODO Auto-generated method stub
		return cancelButton;
	}

	@Override
	public HasValue<String> getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	@Override
	public HasValue<String> getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}
	@Override
	public void setResultMessage(String message) {
		// TODO Auto-generated method stub
		 resultMessage.setText(message);
	}
	

	@Override
	public HasValue<String> getAddress() {
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public HasValue<String> getEmailAddress() {
		// TODO Auto-generated method stub
		return emailAddress;
	}

	@Override
	public HasValue<String> getPhone() {
		// TODO Auto-generated method stub
		return phone;
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void setMode(CommandNameEnum command) {
		// check command turn from Add New into Edit
		if ((this.command== CommandNameEnum.ADD_USER) &&(command== CommandNameEnum.EDIT_USER)){
			addMoreButton.setVisible(true);
		}
		this.command  = command;
		 if(command == CommandNameEnum.ADD_USER){
		    	cancelButton.setText("Clear");
		    	saveButton.setText("Save");
		    	addMoreButton.setVisible(false);
		    }else{ 
		    	cancelButton.setText("Cancel");
		    	saveButton.setText("Update");
		    }
				
	}
	
	@Override
	public HasClickHandlers getAddMoreButton() {
		// TODO Auto-generated method stub
		return addMoreButton;
	}
	
	
	
}
