package com.podcastmodern.lazydatamodel;

import com.podcastmodern.entity.Customer;
import com.podcastmodern.entity.CustomerHome;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


public class CustomerLazyDataModel extends LazyDataModel<Customer> {


    @Inject
    CustomerHome customerDao;

    List<Customer> datasource;

    public CustomerLazyDataModel() {

    }

    ;

    @Override
    public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object>
        filters)

    {
        this.datasource = customerDao.findAll(first, pageSize, sortField, sortOrder, filters);
        this.setRowCount(customerDao.findCount(filters).intValue());
        return datasource;

    }

    @Override
    public Customer getRowData(String rowKey) {
        if (rowKey == null)
            return null;
        for (Customer customer : datasource) {
            if (customer.getCustomerId().toString().equals(rowKey))
                return customer;
        }

        return null;
    }

    @Override
    public Object getRowKey(Customer customer) {
        return customer.getCustomerId();
    }

}
