package com.podcastmodern.cdi;

import com.podcastmodern.entity.Customer;
import com.podcastmodern.entity.CustomerHome;
import com.podcastmodern.lazydatamodel.CustomerLazyDataModel;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@SessionScoped
public class CustomerCDI implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(CustomerCDI.class);

	/**
	 * Here by default, injected POJO is annotated with @DependentScoped. That
	 * means, Customer entity is in the same scope with holder CustomerSaveCDI
	 * bean. So no annotation on his attribute means: @ViewScoped on Customer
	 * class and @Inject annotation over this attribute.
	 */
	@Inject
	private Customer selectedCustomer;
	
	private Customer selectedCustomerToEdit;

	@Inject
	private CustomerHome customerDao;
	
	@PersistenceUnit(unitName = "podcastmodern")
	EntityManagerFactory entityManagerFactory;
	
	@Inject
	private CustomerLazyDataModel customerModel;


	private HtmlInputText nameTextField;
	
	@Inject
	private SessionCDI sessionCDI;
	

	
	@PostConstruct
	public void initDataModel(){
		//customerModel = new CustomerLazyDataModel();
		//System.out.println("data model init");
	}
	

	public String save() {
		
		Session session = (Session)(entityManagerFactory.createEntityManager().getDelegate());
		Set s = new HashSet<Customer>();
		s.add(selectedCustomer);

		session.getTransaction().begin();
		
		session.save(selectedCustomer);
		session.getTransaction().commit();
 		session.close();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer saved", "Customer saved"));
		return "";
	}

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public HtmlInputText getNameTextField() {
		return nameTextField;
	}

	public void setNameTextField(HtmlInputText nameTextField) {
		this.nameTextField = nameTextField;
	}

	public CustomerHome getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerHome customerDao) {
		this.customerDao = customerDao;
	}

	public CustomerLazyDataModel getCustomerModel() {
		return customerModel;
	}

	public void setCustomerModel(CustomerLazyDataModel customerModel) {
		this.customerModel = customerModel;
	}


	public Customer getSelectedCustomerToEdit() {
		return selectedCustomerToEdit;
	}


	public void setSelectedCustomerToEdit(Customer selectedCustomerToEdit) {
		this.selectedCustomerToEdit = selectedCustomerToEdit;
	}

	public SessionCDI getSessionCDI() {
		return sessionCDI;
	}


	public void setSessionCDI(SessionCDI sessionCDI) {
		this.sessionCDI = sessionCDI;
	}

}
