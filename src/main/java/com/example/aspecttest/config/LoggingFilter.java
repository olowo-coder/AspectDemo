package com.example.aspecttest.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.aspecttest.exception.UnknownClientException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    @Value("${list.client}")
    List<String> allowedClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        System.out.println(allowedClient);
        String ipAddress = request.getRemoteAddr();
        String xClient = request.getHeader("CLIENT");
        System.out.println("xClient -> " + xClient);
        System.out.println("ipAddress -> " + ipAddress);

        if(!allowedClient.contains(xClient)){
            throw new UnknownClientException("Invalid Client header");
        }

//        System.out.println("Extract: " + IOUtils.toString(request.getReader()));

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());


        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());

        String req = new String(requestWrapper.getContentAsByteArray());



        LOGGER.info(
                "FINISHED PROCESSING :\nMETHOD={};\nREQUESTURI={};\nREQUEST PAYLOAD={};\nRESPONSE CODE={};\nRESPONSE={};\nTIME TAKEN={}ms",
                request.getMethod(), request.getRequestURI(), requestBody.replaceAll(" ", ""), response.getStatus(),  responseBody,
                timeTaken);
        responseWrapper.copyBodyToResponse();


    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String extractPostBody(HttpServletRequest request){
        Scanner s = null;
        try{
            s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
        }catch (IOException ex){
            ex.printStackTrace();
        }
        assert s != null;
        return s.hasNext() ? s.next() : "";
    }

}
