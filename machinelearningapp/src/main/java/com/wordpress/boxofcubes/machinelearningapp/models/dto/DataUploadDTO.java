package com.wordpress.boxofcubes.machinelearningapp.models.dto;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wordpress.boxofcubes.machinelearningapp.validation.ListOfNumbers;
import com.wordpress.boxofcubes.machinelearningapp.validation.DataLengthMatches;
import org.springframework.web.multipart.MultipartFile;


public class DataUploadDTO extends DataSubmitSharedDTO{
    @NotNull(message="X data file is missing")
    //@ListOfNumbers
    private MultipartFile xFile;
    @NotNull(message="Y data file is missing")
    //@ListOfNumbers(message="Y data file is not readable or contains non-numbers")
    private MultipartFile yFile;

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
