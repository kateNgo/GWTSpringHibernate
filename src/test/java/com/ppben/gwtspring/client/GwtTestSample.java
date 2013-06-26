package com.ppben.gwtspring.client;

import junit.framework.Assert;

import com.google.gwt.junit.client.GWTTestCase;
import com.ppben.gwtspring.client.presenter.EditCustomerPresenter;

public class GwtTestSample
    extends GWTTestCase
{

   public String getModuleName()
   {
      return "com.ppben.gwtspring.Application";
   }

   public void testSomething()
   {
      // Not much to actually test in this sample app
      // Ideally you would test your Controller here (NOT YOUR UI)
      // (Make calls to RPC services, test client side model objects, test client side logic, etc)
	   
      Assert.assertTrue( true );
   }
   public void testIsMailMethod(){
	   Application app = new Application();
	  Assert.assertTrue(app.isEmail("phuongngovn@yahoo.com"));
	  Assert.assertFalse(app.isEmail("phuongngovn@yahoo"));
	  Assert.assertFalse(app.isEmail("phuongn govn@yahoo.com"));
	  Assert.assertTrue(app.isEmail("phuongngovn123@yahoo.com"));
	   
   }
   public void testIsPhone(){
	   Application app = new Application();
	  Assert.assertTrue(app.isPhoneNumber("0413 948 650"));
	  Assert.assertTrue(app.isPhoneNumber("0413948650"));
	  Assert.assertTrue(app.isPhoneNumber("0413-948-650"));
	  Assert.assertTrue(app.isPhoneNumber("0413 948-650"));
	  Assert.assertFalse(app.isPhoneNumber("0413-948--650"));
	  Assert.assertFalse(app.isPhoneNumber("0413 948 650a"));
	   
   }
}