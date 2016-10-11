package com.podcastmodern.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DBQueryObject {	
	
	Map parameters;
	
	public DBQueryObject(){
		 parameters = new HashMap<String, Object>();
	}
	
	public Set<Entry<String, Object> >getParameters(){
		return parameters.entrySet();
	}
	
	public void addParameter(String name, Object value){
		parameters.put(name, value);
	}
	
	public void modifyParameter(String name, Object value)
	{
		if(parameters.containsKey(name))
		{
			parameters.put(name, value);
		}
	}
	
	public void removeParameter(String name){
		if(parameters.containsKey(name))
			parameters.remove(name);
	}
	
	public String getParameterValue(String name){
		
		Object value = parameters.get(name);
		
		if(value == null)
			return null;
		
		Class classTypeOfEntry = value.getClass();	
		
		if (classTypeOfEntry == java.lang.Integer.class) {
			return ((Integer)value).toString();
		} else if (classTypeOfEntry == java.lang.String.class) {
			return "'"+(String)value+"'";
		}
		else if (!classTypeOfEntry.isPrimitive()){
			StringBuffer valuesString = new StringBuffer();
			List<String> values = (List<String>)value;
			for(String singleValue:values)
				valuesString.append(singleValue).append(",");
			
			valuesString.delete(valuesString.length()-1, valuesString.length());
			
			return valuesString.toString();
		}
		else
			return null;
	}
	

}
