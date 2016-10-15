/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.podcastmodern.cdi;

import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.entity.Application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author ocakcit
 */

public class ApplicationLazyDataModel extends LazyDataModel<Application>{

    @Inject
    ApplicationDao appDao;
    
    List<Application> applicationList = new  ArrayList<Application>();
    
    public ApplicationLazyDataModel(){
        
    }

    

    @Override
    public List<Application> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
       this.applicationList = appDao.findAllByCriteria(first, pageSize, multiSortMeta, filters);
       this.setRowCount(appDao.findCountByCriteria(multiSortMeta, filters));
       return this.applicationList;
    }
    
    
    
    @Override
    public int getRowCount() {
        return applicationList.size();
    }

    @Override
    public Application getRowData(String key) {
        for(Application app: this.applicationList)
        {
            if(app.getApplicationId().toString().equals(key) )
                return app;
        }
        return null;
    }

   @Override
    public Integer getRowKey(Application app) {
		return app.getApplicationId();
    }
    
    
    
    
    
}
