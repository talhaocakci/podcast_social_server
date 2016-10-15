/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.rs.endpoints;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.podcastmodern.apientity.SecureUrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;

@Controller
@RequestMapping("/url/generateurl/{key}/{bucket}")
public class UrlAPI {

    private AmazonS3 amazonS3Client;

    public UrlAPI() {
        super();
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
        amazonS3Client = new AmazonS3Client(
            new ProfileCredentialsProvider(), clientConfiguration);
        amazonS3Client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
    }

    public AmazonS3 getAmazonS3Client() {
        return amazonS3Client;
    }

    public void setAmazonS3Client(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public SecureUrl generateURL(@PathVariable String key, @PathVariable String bucket) {

        SecureUrl secureUrl = new SecureUrl();
        secureUrl.setUrl(generateSignedURL(bucket, key));
        return secureUrl;

    }

    private String generateSignedURL(String bucket, String key) {


        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60; // 1 hour.
        expiration.setTime(msec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, URLDecoder.decode(key));
        generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
        generatePresignedUrlRequest.setExpiration(expiration);

        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();


    }

}
