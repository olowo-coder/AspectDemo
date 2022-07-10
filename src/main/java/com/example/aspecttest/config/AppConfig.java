package com.example.aspecttest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.net.http.HttpHeaders;
import java.util.*;


//@Configuration
public class AppConfig implements Filter {
    private static final List<String> HEADERS_TO_SKIP = Arrays.asList("authorization", "token", "security", "oauth", "auth");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(httpRequest);
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String element = headerNames.nextElement();
                System.out.println( element + ": " + httpRequest.getHeader(element));
                if(element.equalsIgnoreCase("client")){
                    String val = httpRequest.getHeader(element);
                    System.out.println("Client ID: " + val);
                    if(!val.equalsIgnoreCase("MTN")){
                       throw new ResponseStatusException(HttpStatus.FORBIDDEN,"wrong client");
                    }
                }
            }
        }
//        if (Arrays.asList(RequestMethod.POST.name(), RequestMethod.PUT.name()).contains(httpRequest.getMethod())) {
//            String requestBody = httpRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        }

        System.out.println("Body: ");
        httpServletResponse.setHeader(
                "Example-Filter-Header", "Value-Filter");
        CachedRequestHttpServletRequest cachedRequestHttpServletRequest =
                new CachedRequestHttpServletRequest((HttpServletRequest) servletRequest);
        String log = String.format("URL: %s \n Requester: %s \n HTTP Method: %s \n Headers: %s \n QueryStringParams: %s \n RequestBody: %s",
                cachedRequestHttpServletRequest.getRequestURL(), cachedRequestHttpServletRequest.getRemoteAddr(),
                cachedRequestHttpServletRequest.getMethod(), getRequestHeaders(cachedRequestHttpServletRequest),
                cachedRequestHttpServletRequest.getQueryString(), getBody(cachedRequestHttpServletRequest));
        // Log your request here!
        System.out.println(log); //Try using a logger instead of the print statement.
        filterChain.doFilter(cachedRequestHttpServletRequest, httpServletResponse);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                httpServletResponse.getCharacterEncoding());
        System.out.println("response BODY: " + responseBody);
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
    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement();
            // Filter the headers that you need to skip.
            // If you don't want to filter any headers and want to log all of them,
            // you can remove the condition below.
            if (HEADERS_TO_SKIP.stream().noneMatch(h -> h.toLowerCase().contains(header.toLowerCase())
                    || header.toLowerCase().contains(h.toLowerCase()))) {
                headersMap.put(header, request.getHeader(header));
            }
        }
        return headersMap;
    }
    private String getBody(CachedRequestHttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }
    private static class CachedRequestHttpServletRequest extends HttpServletRequestWrapper {
        private final byte[] cachedBody;
        public CachedRequestHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            this.cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
        }
        @Override
        public ServletInputStream getInputStream() {
            return new CachedRequestServletInputStream(this.cachedBody);
        }
        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.cachedBody)));
        }
    }
    private static class CachedRequestServletInputStream extends ServletInputStream {
        private final InputStream cachedBodyInputStream;
        public CachedRequestServletInputStream(byte[] cachedBody) {
            this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
        }
        @Override
        public boolean isFinished() {
            try {
                return cachedBodyInputStream.available() == 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        public boolean isReady() {
            return true;
        }
        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }
    }


//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
//        Set<String> headerNames = httpHeaders.keySet();
//        headerNames.forEach(head -> {
//            String headerValue = httpHeaders.getFirst(head);
//            System.out.println(head + ": " + headerValue);
//        });
//        exchange.getResponse()
//                .getHeaders().add("web-filter", "web-filter-test");
//        return chain.filter(exchange);
//    }
}







