package com.company.employeetesting.web.employee;

import java.util.Map;

import javax.inject.Inject;

import com.company.employeetesting.entity.Employee;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

public class EmployeeEdit extends AbstractEditor<Employee> {
	@Inject
    private DataSupplier dataSupplier;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private Button downloadImageBtn;
    @Inject
    private Button clearImageBtn;
    @Inject
    private Datasource<Employee> employeeDs;

    @Inject
    private FileLoader fileLoader;
    
    @Inject
    private Image image;

    @Override
    public void init(Map<String, Object> params) {
    	//if(getItem().getImageFile()!=null) {
          //  image.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getImageFile());
    	//}
        uploadField.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = uploadField.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(uploadField.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            getItem().setImageFile(dataSupplier.commit(fd));
            displayImage();
        });

        uploadField.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));

        employeeDs.addItemPropertyChangeListener(event -> {
            if ("imageFile".equals(event.getProperty()))
                updateImageButtons(event.getValue() != null);
        });
    }

    @Override
    protected void postInit() {
        displayImage();
        updateImageButtons(getItem().getImageFile() != null);
    }

    public void onDownloadImageBtnClick() {
        if (getItem().getImageFile() != null)
            exportDisplay.show(getItem().getImageFile(), ExportFormat.OCTET_STREAM);
    }

    public void onClearImageBtnClick() {
        getItem().setImageFile(null);
        displayImage();
    }

    private void updateImageButtons(boolean enable) {
        downloadImageBtn.setEnabled(enable);
        clearImageBtn.setEnabled(enable);
    }

    private void displayImage() {
        if (getItem().getImageFile() != null) {
            image.setSource(FileDescriptorResource.class).setFileDescriptor(getItem().getImageFile());
            image.setVisible(true);
        } else {
            image.setVisible(false);
        }
    }
}