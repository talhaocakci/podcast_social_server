package com.podcastmodern.service;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.internal.Constants;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.impl.rest.httpclient.RestStorageService;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.security.ProviderCredentials;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUploadService {

    // change this to the desired path you wish the files to be uploaded
    private String FilePath = "C:\\workspace\\Temp\\";

    public Boolean doUpload(String fileName, byte[] data) {

        try {
            RestS3Service s3Service = (RestS3Service) getStorageService(getCredentials(), Constants.S3_HOSTNAME);

            String bucketName = "paperify";
            S3Bucket testBucket = s3Service.getOrCreateBucket(bucketName);
            org.jets3t.service.acl.AccessControlList bucketAcl = s3Service.getBucketAcl(testBucket);
            bucketAcl.grantPermission(org.jets3t.service.acl.GroupGrantee.ALL_USERS,
                org.jets3t.service.acl.Permission.PERMISSION_FULL_CONTROL);

            // Update the bucket's ACL. Now anyone can view the list of objects
            // in this bucket.
            testBucket.setAcl(bucketAcl);
            s3Service.putBucketAcl(testBucket);
            System.out.println("Created test bucket: " + testBucket.getName());
            org.jets3t.service.model.S3Object object = new org.jets3t.service.model.S3Object(fileName);
            System.out.println("S3Object before upload: " + object);
            org.jets3t.service.model.S3Object helloWorldObject = new org.jets3t.service.model.S3Object(fileName);

            InputStream is = new ByteArrayInputStream(data);

            helloWorldObject.setDataInputStream(is);
            helloWorldObject.setContentLength(data.length);
            helloWorldObject.setContentType("application/octet-stream");
            helloWorldObject.setAcl(bucketAcl);

            s3Service.putObject(testBucket, helloWorldObject);

            // Print details about the uploaded object.
            System.out.println("S3Object with data: " + helloWorldObject);

            String amazonId = "http://s3.amazonaws.com/" + testBucket.getName() + "/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    protected ProviderCredentials getCredentials() {
        PropertiesCredentials credentials = null;
        try {

            credentials = new PropertiesCredentials(
                FileUploadService.class.getResourceAsStream("AwsCredentials.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String awsAccessKey = credentials.getAWSAccessKeyId();
        String awsSecretKey = credentials.getAWSSecretKey();

        AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);

        return awsCredentials;
    }

    protected RestStorageService getStorageService(ProviderCredentials credentials, String endpointHostname)
        throws ServiceException {
        Jets3tProperties properties = new Jets3tProperties();
        properties.setProperty("s3service.s3-endpoint", endpointHostname);
        return new RestS3Service(credentials, null, null, properties);
    }

}
