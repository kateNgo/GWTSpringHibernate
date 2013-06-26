package com.ppben.gwtspring.client.view;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ppben.gwtspring.client.presenter.CustomerPresenter;
import com.ppben.gwtspring.shared.dto.CustomerDTO;

public class CustomerView extends Composite implements CustomerPresenter.Display{
	
	  private final Button deleteButton;
	  private final Button editButton;
	  private List<CustomerDTO> customersTable;
	  private final FlexTable contentTable;
	  private void setHeader(){
		  contentTable.setText(0,0, "");
		    contentTable.setText(0,1, "First name");
		    contentTable.setText(0,2, "Last name");
		    contentTable.setText(0,3, "Address");
		    contentTable.setText(0,4, "Email");
		    contentTable.setText(0,5, "Phone");
		    contentTable.getRowFormatter().addStyleName(0, "customerListRowHeader");
	  }
	  public CustomerView(){
		// Create the menu
		  
		  	deleteButton = new Button("Delete");
		  	editButton = new Button("Edit");
		  	editButton.getElement().setId("editCustomerButton");
		  	VerticalPanel userPanel = new VerticalPanel();
		  	HorizontalPanel buttonsPanel = new HorizontalPanel();
		  	buttonsPanel.setSpacing(5);
		  	buttonsPanel.add(deleteButton);
		  	buttonsPanel.add(editButton);
		  	buttonsPanel.addStyleName("customerListButtonsPanel");
		  	userPanel.add(buttonsPanel);
		  	ScrollPanel contentTableDecorator = new ScrollPanel();
		   
		    contentTableDecorator.setWidth("100%");
		    contentTable = new FlexTable();
		    contentTable.setWidth("100%");
		    contentTable.setCellPadding(5);
		    setHeader();
		    
		    // add data into table
		   
		   
		    contentTableDecorator.add(contentTable);
		    userPanel.add(contentTableDecorator);
		    initWidget(userPanel);
	  }
	

	  @Override
		public HasClickHandlers getEditButton() {
			// TODO Auto-generated method stub
			return editButton;
		}

	@Override
	public HasClickHandlers getDeleteButton() {
		// TODO Auto-generated method stub
		return deleteButton;
	}

	@Override
	public List<CustomerDTO> getList() {
		// TODO Auto-generated method stub
		return customersTable;
	}

	@Override
	public void setData(List<CustomerDTO> data) {
		contentTable.removeAllRows();
		 setHeader();
		customersTable = data;
		 for (int i = 0; i < customersTable.size(); ++i) {
			 CheckBox checkBox = new CheckBox();
		    	contentTable.setWidget(i+1, 0, checkBox);
		    	 // checkBox.getElement().setId("userId_" + customersTable.get(i).getId());
		    	contentTable.setText(i+1, 1, customersTable.get(i).getFirstName());
		    	contentTable.setText(i+1, 2, customersTable.get(i).getLastName());
		    	contentTable.setText(i+1, 3, customersTable.get(i).getAddress());
		    	contentTable.setText(i+1, 4, customersTable.get(i).getEmailAddress());
		    	contentTable.setText(i+1, 5, customersTable.get(i).getPhone());
		    	
		    }
	}

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();
	   
	    for (int i = 1; i < contentTable.getRowCount(); ++i) {
	      CheckBox checkBox = (CheckBox)contentTable.getWidget(i, 0);
	    
	      if (checkBox.getValue()) {
	    	
	        selectedRows.add(i-1);
	      }
	    }
	    
	    return selectedRows;
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	

}
