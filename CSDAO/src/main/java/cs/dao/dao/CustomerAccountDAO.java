package cs.dao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cs.dao.util.HibernateUtil;
import cs.model.CustomerAccount;
import cs.dao.DAO;

public class CustomerAccountDAO extends DAO
{
	/**
	 * Constructor by default
	 */
	public CustomerAccountDAO()
	{
		System.out.println("[Spring] load class:cs.dao.dao.CustomerAccountDAO");
	}
	
	public CustomerAccount load(Integer id)
	{
		 // Open session
		 Session session = HibernateUtil.currentSession();
		 Transaction tx = session.beginTransaction();
		 session.beginTransaction();

		 // Load Data object
		 CustomerAccount ca = (CustomerAccount) session.load(CustomerAccount.class, id);
		 
		 tx.commit();

		 HibernateUtil.closeSession();
		 
		 return ca;
	}
	
	public Boolean save(CustomerAccount ca)
	{
		 
		 Session session = HibernateUtil.currentSession();		   
		 Transaction tx = session.beginTransaction();
		 tx.begin();
		 session.beginTransaction();

		 session.save(ca);

		 tx.commit();
		 HibernateUtil.closeSession();

		 return true;
	}
	
	/**
	 *  Search a customer account
	 *  If one customer account is founded -> OK (the customer is returned)
	 *  Else return NULL
	 */
	public CustomerAccount identification(String customerLogin, String customerPassword)
	{
	     Session session = HibernateUtil.currentSession();
	     
	     String SQLQuery = "SELECT * FROM customer_accounts " + 
	    	               "WHERE customer_login = '" + customerLogin + "' " + 
	    	               "AND customer_password = '" + customerPassword + "'";
	     
	     System.out.println(SQLQuery);
	     
	     Query query = session.createSQLQuery(SQLQuery).addEntity(CustomerAccount.class);
	     
	     List customerAccountList = query.list();
		 
	     HibernateUtil.closeSession();

	     if(customerAccountList.size() == 1)
	    	 return (CustomerAccount)customerAccountList.get(0);
	     else
	    	 return null;
	}
	
	public void saveCustomerLocation(Integer idCustomerAccount, double longitude, double latitude)
	{
		Session session = HibernateUtil.currentSession();
		
		String SQLQuery = "UPDATE customer_accounts " +
						  "SET geoloc_longitude=" + Double.toString(longitude) + ", " +
						  "geoloc_latitude=" + Double.toString(latitude) + " " +
						  "WHERE _id_customer_account=" + Integer.toString(idCustomerAccount);
		
		System.out.println(SQLQuery);
		
		Query query = session.createSQLQuery(SQLQuery);
		query.executeUpdate();
		
		HibernateUtil.closeSession();
	}
	
	public void saveCustomerGeneralsInfos(Integer idCustomerAccount, String lastName, String firstName, String emailAddress, String mobile)
	{
		Session session = HibernateUtil.currentSession();
		
		Transaction transaction = session.beginTransaction();
		CustomerAccount customerAccount = (CustomerAccount)session.get(CustomerAccount.class, idCustomerAccount);
		
		customerAccount.setLastName(lastName);
		customerAccount.setFirstName(firstName);
		customerAccount.setEmailAddress(emailAddress);
		customerAccount.setMobile(mobile);
		
		// Ugly part
		session.save(customerAccount);
		
		//session.update(customerAccount);
		
		transaction.commit();
		HibernateUtil.closeSession();
	}
	
	public void saveCustomerPreferences(Integer idCustomerAccount, Integer acceptAnimals, Integer acceptRadio, Integer acceptSmoker, Integer acceptToDiscuss, Integer acceptToMakeADetour)
	{
		Session session = HibernateUtil.currentSession();
		
		Transaction transaction = session.beginTransaction();
		CustomerAccount customerAccount = (CustomerAccount)session.get(CustomerAccount.class, idCustomerAccount);
	    
		customerAccount.setAcceptAnimals(acceptAnimals);
		customerAccount.setAcceptRadio(acceptRadio);
		customerAccount.setAcceptSmoker(acceptSmoker);
		customerAccount.setAcceptToDiscuss(acceptToDiscuss);
		customerAccount.setAcceptToMakeADetour(acceptToMakeADetour);
		
		// Ugly part
		session.save(customerAccount);
		
		//session.update(customerAccount);
		
		transaction.commit();
		HibernateUtil.closeSession();
	}
	
	/**
	 * Function to update the customer password
	 * 
	 * Check if the customer give his right current password:
	 * If OK, the new password is saved (status = 1)
	 * Else no update (status = 2)
	 */
    public Integer saveCustomerNewPassword(Integer idCustomerAccount, String currentPassword, String newPassword)
    {
    	Integer saveCustomerNewPasswordStatus = 0;
    	
    	Session session = HibernateUtil.currentSession();
    	
		Transaction transaction = session.beginTransaction();
    	
    	String SQLQuery = "SELECT * FROM customer_accounts " +
    					  "WHERE _id_customer_account=" + idCustomerAccount.toString() + " " +
    					  "AND customer_password = '" + currentPassword + "'";
    	
    	Query query = session.createSQLQuery(SQLQuery).addEntity(CustomerAccount.class);

    	List customerAccountList = query.list();
    	
    	if(!customerAccountList.isEmpty())
    	{
    		CustomerAccount customerAccount = (CustomerAccount)customerAccountList.get(0);
    		
    		customerAccount.setCustomerPassword(newPassword);
    		session.save(customerAccount);
    	   
    		saveCustomerNewPasswordStatus = 1;
    	}
    	else saveCustomerNewPasswordStatus = 2;
    	
    	transaction.commit();
		
    	HibernateUtil.closeSession();

    	return saveCustomerNewPasswordStatus;
    }
}