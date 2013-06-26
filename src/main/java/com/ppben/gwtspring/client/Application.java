package com.ppben.gwtspring.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ppben.gwtspring.shared.dto.BookDTO;
import com.ppben.gwtspring.shared.services.BookServiceAsync;
import com.ppben.gwtspring.shared.services.CustomerService;
import com.ppben.gwtspring.shared.services.CustomerServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again. The error is : ";

	
	private final BookServiceAsync bookService = BookServiceAsync.Util
			.getInstance();
	private final CustomerServiceAsync customerService = CustomerServiceAsync.Util
			.getInstance();
	private ScrollPanel listBookPanel = new ScrollPanel();
	private FlowPanel searchPanel = new FlowPanel();
	private FlowPanel editBookPanel = new FlowPanel();
	private VerticalPanel bookPanel = new VerticalPanel();
	private MenuBar menu = new MenuBar(true);
	
	private final class EditBookPanel extends
	com.google.gwt.user.client.ui.Composite implements ClickHandler {
		private TextBox titleTB = new TextBox();
		private TextArea descriptionTA = new TextArea();
		private ListBox publishYearLB = new ListBox();
		private TextBox publisherTB = new TextBox();
		private Button saveBtn = new Button("SAVE");
		private Label labelResult = new Label();
		public EditBookPanel(){
			Label labelTitle = new Label("Title");
			Label labelDescription = new Label("Description");
			Label labelPublishYear = new Label("Year: ");
			Label labelPublisher = new Label("Publisher");
			int currentYear = new Date().getYear() + 1900;
			for(int i=currentYear - 10; i<=currentYear;i++){
				publishYearLB.addItem(String.valueOf(i));
			}
			publishYearLB.setVisibleItemCount(1);
			
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(3);
			verticalPanel.add(labelTitle); verticalPanel.add(titleTB);
			verticalPanel.add(labelDescription); verticalPanel.add(descriptionTA);
			HorizontalPanel h1 = new HorizontalPanel();
			h1.add(labelPublishYear); h1.add(publishYearLB);
			verticalPanel.add(h1);
			verticalPanel.add(labelPublisher); verticalPanel.add(publisherTB);
			HorizontalPanel h2 = new HorizontalPanel();
			h2.add(saveBtn); h2.add(labelResult);
			labelResult.addStyleName("labelResult");
			verticalPanel.add(h2);
			verticalPanel.addStyleName("editBookPanel");
			initWidget(verticalPanel);
			saveBtn.addClickHandler(this);
			
		}

		@Override
		public void onClick(ClickEvent arg0) {
			String title = titleTB.getText();
			if ((title ==null) ||(title.equals(""))){
				Window.alert("  Title is a required field");
				titleTB.setFocus(true);
				return;
			}
			String description = descriptionTA.getText();
			int selectedIndex = publishYearLB.getSelectedIndex();
			int publishYear = Integer.parseInt(publishYearLB.getItemText(selectedIndex));
			String publisher = publisherTB.getText();
			bookService.saveBook(title, description, publishYear, publisher, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable arg0) {
					labelResult.setText("  Error:" + arg0.getMessage());
					
				}

				@Override
				public void onSuccess(Void arg0) {
					labelResult.setText("Saved Successful");
					bookService.findAll(new AsyncCallback<List<BookDTO>>() {
					      public void onSuccess(List<BookDTO> result) {
					    	  loadSearchResult(result);
					      }
					      
					      public void onFailure(Throwable caught) {
					        Window.alert("No book in database");
					      }
					    });
					
				}
				
			});
		}
	}
	private final class SearchPanel extends
			com.google.gwt.user.client.ui.Composite implements ClickHandler {

		private Label label = new Label("Search:");
		private TextBox textBox = new TextBox();
		private Button btGo = new Button("GO");

		public SearchPanel() {
			HorizontalPanel panel = new HorizontalPanel();
			panel.setSpacing(10);
			panel.add(label);
			panel.add(textBox);
			panel.add(btGo);
			// DecoratorPanel decorationPanel = new DecoratorPanel();
			// decorationPanel.add(panel);
			initWidget(panel);
			panel.setStyleName("searchPanel");
			textBox.setWidth("200");
			btGo.addClickHandler(this);

		}

		@Override
		public void onClick(ClickEvent evt) {
			if (evt.getSource() == btGo) {
				String term = textBox.getText();
				
				bookPanel.remove(listBookPanel);
				
				BookDTO book = new BookDTO();
				 bookService.searchTerm(term, new AsyncCallback<List<BookDTO>>(){
				 

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						Window.alert("No Book found");
					}

					public void onSuccess(List<BookDTO> result) {
						loadSearchResult(result);
					}
					
				}); 
			}

		}
	}
	private InlineHTML createHR(){
		InlineHTML hrTag = new InlineHTML().wrap(Document.get()
				.createHRElement());
		return hrTag;
	}

	
	public void onModuleLoad() {
		createHorizantalMenuBar();
		loadBookPanel(null);
		createVerticalMenuBar();
	//	exportStaticMethod();
	}
	private void loadBookPanel(FlowPanel topPanel){
		// load search or edit panel
		bookPanel = new VerticalPanel();
		if (topPanel == null)
		{
			topPanel = new FlowPanel();
			topPanel.add(new SearchPanel());
		
		}
		bookPanel.add(topPanel);
			
		// createHR
		bookPanel.add(createHR());
		// load book list scroll panel
		bookService.findAll(new AsyncCallback<List<BookDTO>>() {
		      public void onSuccess(List<BookDTO> result) {
		    	  loadSearchResult(result);
		      }
		      
		      public void onFailure(Throwable caught) {
		        Window.alert("No book in database");
		      }
		    });
		RootPanel.get("mainContent").add(bookPanel);
	}
	
	private void clearBookPanel(){
		RootPanel.get("mainContent").clear();
		//RootPanel.get("mainContent").remove(bookPanel);
		
		
	}
	
	private void loadSearchResult(List<BookDTO> books) {
		listBookPanel = new ScrollPanel();
		if ((books != null)  && (!books.isEmpty())){

			FlexTable resultTable = new FlexTable();
			resultTable.setCellPadding(5);
			resultTable.getColumnFormatter().setWidth(0, "5%");
			resultTable.getColumnFormatter().setWidth(1, "25%");
			resultTable.getColumnFormatter().setWidth(2, "40%");
			resultTable.getColumnFormatter().addStyleName(2, "tdDescription");
			resultTable.getColumnFormatter().setWidth(3, "10%");
			resultTable.getColumnFormatter().setWidth(4, "20%");
			resultTable.setText(0, 0, "");
			resultTable.setText(0, 1, "Title");
			resultTable.setText(0, 2, "Description");
			resultTable.setText(0, 3, "Year");
			resultTable.setText(0, 4, "Publisher");
			
			resultTable.getRowFormatter().addStyleName(0,"hearderOfResultPanel");
			for(int i=0; i<books.size();i++){
				CheckBox ck = new CheckBox();
				int row = i+1;
				resultTable.setWidget(row,0, ck);
				resultTable.setText(row,1, books.get(i).getTitle());
				resultTable.setText(row,2, books.get(i).getDescription());
				resultTable.setText(row,3,String.valueOf(books.get(i).getPublishYear()));
				resultTable.setText(row,4, books.get(i).getPulisher());
			}
			resultTable.addStyleName("resultTable");
			listBookPanel.addStyleName("searchResultPanel");
			listBookPanel.add(resultTable);
			bookPanel.add(listBookPanel);

		}else{
			Window.alert("result is empty");
		}
	}
	private void searchBook(){
		clearBookPanel();
		loadBookPanel(null);
		
		
	}
	private void editBook(){
		clearBookPanel();
		editBookPanel = new FlowPanel();
		editBookPanel.add(new EditBookPanel());
		loadBookPanel(editBookPanel);
		
	}
	private void createVerticalMenuBar(){
		MenuBar menu= new MenuBar(true);
		menu.setAutoOpen(true);
		   menu.setAnimationEnabled(true);
		   
		   menu.addItem("Search", new Command(){

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				searchBook();
			}
			   
		   });
		   menu.addItem("Edit", new Command(){

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				 editBook();
			}
			   
		   });
		   		   
		   menu.addSeparator();
		   menu.addItem("List", new Command(){

			@Override
			public void execute() {
				doListUser();
			}
			   
		   });
		   menu.addItem("Management", new Command(){

				@Override
				public void execute() {
					// TODO Auto-generated method stub
					
					doUserManagement();
				}
				   
			   });
		 
		   
		   //add the menu to the root panel
		    
		   RootPanel.get("leftMenu").add(menu);

	}
	private void createHorizantalMenuBar(){
		// Create a menu bar
		   MenuBar menu = new MenuBar();
		   menu.setWidth("250");
		   menu.setAutoOpen(true);
		   menu.setAnimationEnabled(true);
		   MenuBar bookMenu = new MenuBar(true);
		   bookMenu.setWidth("100");
		   bookMenu.addItem("Search", new Command(){

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				searchBook();
			}
			   
		   });
		   bookMenu.addItem("Edit", new Command(){

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				 editBook();
			}
			   
		   });
		   		   
		   menu.addItem("Book           ", bookMenu);
		   menu.addSeparator();
		   MenuBar userMenu = new MenuBar(true);
		   userMenu.setWidth("100");
		   userMenu.addItem("List", new Command(){

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				
				doListUser();
			}
			   
		   });
		   userMenu.addItem("Management", new Command(){

				@Override
				public void execute() {
					// TODO Auto-generated method stub
					
					doUserManagement();
				}
				   
			   });
		   menu.addItem("User", userMenu);
		   
		   //add the menu to the root panel
		    
		   RootPanel.get("topmenuLeft").add(menu);
	}
	private void doUserManagement(){
		RootPanel.get("mainContent").clear();
		CustomerServiceAsync rpcService = customerService;
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(eventBus,rpcService, CommandNameEnum.ADD_USER);
	    appViewer.go(RootPanel.get("mainContent"));
	}
	private void doListUser(){
		RootPanel.get("mainContent").clear();
		CustomerServiceAsync rpcService = customerService;
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(eventBus,rpcService, CommandNameEnum.LIST_USER);
	    appViewer.go(RootPanel.get("mainContent"));
	}
	
	
	public static boolean isPhoneNumber(String phone){
		if (phone.contains("--"))
			return false;
		if (phone.matches("[0-9 -]*")){
			return true;
		}
		
		return false;
	}
	public static boolean isEmail(String email){
		 String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			if(email.matches(regex))
				return true;
			 return false;
	}
	/* JSNI */

	public  native static boolean checkCustomerInputForm(String firstName,
			String lastName, String address, String emailAddress, String phone) /*-{
		
		if (firstName == "") {
			$wnd.alert("First name is required field.");
			return false;
		}
		if (lastName == "") {
			$wnd.alert("Last name is required field.");
			return false;
		}
		if ((phone != "") && (!@com.ppben.gwtspring.client.Application::isPhoneNumber(Ljava/lang/String;)(phone))){
			$wnd.alert("Phone is invalid format");
			return false;
		}
		if ((emailAddress != "") && (!@com.ppben.gwtspring.client.Application::isEmail(Ljava/lang/String;)(emailAddress))){
			$wnd.alert("Email address is invalid format");
			return false;
		}
		return true;
	}-*/;
	
}
