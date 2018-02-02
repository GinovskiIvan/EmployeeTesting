package com.company.employeetesting.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@NamePattern("%s|name")
@Table(name = "EMPLOYEETESTING_EMPLOYEE")
@Entity(name = "employeetesting$Employee")
public class Employee extends StandardEntity {
    private static final long serialVersionUID = 5012814163766369540L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_FILE_ID")
    protected FileDescriptor imageFile;

    public void setImageFile(FileDescriptor imageFile) {
        this.imageFile = imageFile;
    }

    public FileDescriptor getImageFile() {
        return imageFile;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}