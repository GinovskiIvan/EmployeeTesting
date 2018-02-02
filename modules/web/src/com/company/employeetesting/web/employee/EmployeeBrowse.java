package com.company.employeetesting.web.employee;

import java.util.Map;

import javax.inject.Inject;

import com.company.employeetesting.entity.Employee;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Image.ScaleMode;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

public class EmployeeBrowse extends AbstractLookup {
	@Inject
    private ComponentsFactory componentsFactory;

    @Inject
    private GroupTable<Employee> employeesTable;

    @Override
    public void init(Map<String, Object> params) {

        employeesTable.addGeneratedColumn("name", entity -> {
            Image image = componentsFactory.createComponent(Image.class);
            image.setScaleMode(ScaleMode.CONTAIN);
            image.setHeight("40");
            image.setWidth("40");

            FileDescriptor userImageFile = entity.getImageFile();
            image.setSource(FileDescriptorResource.class).setFileDescriptor(userImageFile);

            Label userLogin = componentsFactory.createComponent(Label.class);
            userLogin.setValue(entity.getName());
            userLogin.setAlignment(Alignment.MIDDLE_LEFT);

            HBoxLayout hBox = componentsFactory.createComponent(HBoxLayout.class);
            hBox.setSpacing(true);
            hBox.add(image);
            hBox.add(userLogin);

            return hBox;
        });
    }
}