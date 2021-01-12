package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class DataUploadDTO extends DataSubmitSharedDTO{
    @NotNull(message="X data file is missing")
    private MultipartFile xFile;
    @NotNull(message="Y data file is missing")
    private MultipartFile yFile;

    public DataUploadDTO(){}
    
    public MultipartFile getXFile(){
        return xFile;
    }
    public void setXFile(MultipartFile xFile){
        this.xFile = xFile;
    }
    public MultipartFile getYFile(){
        return yFile;
    }
    public void setYFile(MultipartFile yFile){
        this.yFile = yFile;
    }
}
