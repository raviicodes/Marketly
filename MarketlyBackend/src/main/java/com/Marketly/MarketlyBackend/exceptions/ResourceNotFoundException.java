package com.Marketly.MarketlyBackend.exceptions;

public class ResourceNotFoundException extends RuntimeException {
       String resourceName;
       String fieldName;
       String field;
       Long fieldId;

    public ResourceNotFoundException(String resourceName,String field,String fieldName){
         super(String.format("%s not found with %s : %s",resourceName,fieldName,field));
         this.field=field;
          this.fieldName=fieldName;
          this.resourceName=resourceName;
    }
    public ResourceNotFoundException(String resourceName,String fieldName,Long fieldId){
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldId));
        this.fieldName=fieldName;
        this.fieldId=fieldId;
        this.resourceName=resourceName;
    }
}
