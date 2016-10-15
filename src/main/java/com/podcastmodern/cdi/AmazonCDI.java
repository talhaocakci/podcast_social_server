/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.cdi;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.GrantAndPermission;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.impl.rest.httpclient.RestStorageService;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.security.ProviderCredentials;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AmazonCDI implements Serializable {

    private String bucketName;

    public void createRSS() throws ServiceException, IOException, FeedException {
        com.amazonaws.auth.AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        RestS3Service s3Service = (RestS3Service) getStorageService(
            new AWSCredentials(credentials.getAWSAccessKeyId(), credentials.getAWSSecretKey()), Constants.S3_HOSTNAME);

        bucketName = "javacore-course";
        S3Bucket testBucket = s3Service.getOrCreateBucket(bucketName);
        org.jets3t.service.acl.AccessControlList bucketAcl = s3Service.getBucketAcl(testBucket);
        bucketAcl.grantPermission(org.jets3t.service.acl.GroupGrantee.ALL_USERS,
            org.jets3t.service.acl.Permission.PERMISSION_FULL_CONTROL);


        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
            .withBucketName(bucketName)
            .withPrefix("myprefix");

        S3Object[] objectListing;
        List entries = new ArrayList();
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");

        feed.setTitle("Java Course");
        feed.setLink("javacore-course");
        feed.setDescription("This is the items for java core course");


        objectListing = s3Service.listObjects(bucketName);
        for (S3Object object : objectListing) {
            SyndEntry entry;
            SyndContent description;

            long sizeInMB = object.getContentLength();

            org.jets3t.service.acl.AccessControlList controlList = s3Service.getObjectAcl(bucketName, object.getKey());
            //  s3Client.getResourceUrl(bucket, s3RelativeToBucketPath);

            String url = object.getName();
            for (GrantAndPermission permission : controlList.getGrantAndPermissions()) {
                if (permission.getPermission() == Permission.PERMISSION_READ) {
                    url = constructPublicLink(bucketName, object.getName());
                }
            }

            entry = new SyndEntryImpl();
            entry.setLink(object.getETag());
            entry.setTitle(object.getName().replace(".mp4", "").replace("597468 - ", ""));

            description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(object.getName());
            List<SyndEnclosure> enc = new ArrayList<SyndEnclosure>();
            SyndEnclosure e = new SyndEnclosureImpl();
            e.setLength(sizeInMB);
            e.setUrl(url);

            enc.add(e);
            entry.setEnclosures(enc);
            entry.setDescription(description);
            entries.add(entry);
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        feed.setEntries(entries);

        SyndFeedOutput output = new SyndFeedOutput();
        String s = output.outputString(feed);

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer
        ec.setResponseContentType("application/xml"); // Check http://www.iana.org/assignments/media-types for all
        ec.setResponseContentLength(s.length()); // Set it with the file size. This header is optional. It will work
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "javacourse.xml" + "\""); // The Save As

        OutputStream outputstream = ec.getResponseOutputStream();
        outputstream.write(s.getBytes());

        fc.responseComplete(); // I

    }

    private String constructPublicLink(String bucketName, String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + URLEncoder.encode(key);
    }

    protected RestStorageService getStorageService(ProviderCredentials credentials, String endpointHostname)
        throws ServiceException {
        Jets3tProperties properties = new Jets3tProperties();
        properties.setProperty("s3service.s3-endpoint", endpointHostname);
        return new RestS3Service(credentials, null, null, properties);
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
