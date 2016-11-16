package edu.cornell.finapiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AccountClient {

    public static void main(String[] args) throws Exception{
        AccountClient accountClient = new AccountClient();
        HttpResponse response = accountClient.getAccounts();
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println(body);
        Map<String, Object> accounts = accountClient.parseVendorsFromBody(body);
        System.out.println(accounts.size());
        Map<String, Object> query = (Map<String, Object>)accounts.get("query");
        System.out.println("query: " + query);
        int limit = (Integer)accounts.get("limit");
        System.out.println("limit: " + limit);
        int skip = (Integer)accounts.get("skip");
        System.out.println("skip: " + skip);
        ArrayList sort = (ArrayList)accounts.get("sort");
        System.out.println("sort: " + sort);
        int totalCount = (Integer)accounts.get("totalCount");
        System.out.println("totalCount: " + totalCount);
        //"""query":{"chartOfAccountsCode":"BL"},"limit":200,"skip":0,"sort":["chartOfAccountsCode","accountNumber"],"totalCount":2525,"results":""
    }

    public HttpResponse getAccounts() throws IOException {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpHost httpHost = new HttpHost("dragons-tst.kuali.co");
        HttpRequest httpRequest = new HttpGet("https://dragons-tst.kuali.co/fin/coa/api/v1/reference/acct?chartOfAccountsCode=BL");
        httpRequest.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU4MmNhZTRmOWFhYjg2MDAwMTYxZTUwMiIsImlzcyI6Imt1YWxpLmNvIiwiZXhwIjoxNDgwNTMyODE1LCJpYXQiOjE0NzkzMjMyMTV9.JF6acNOxMZaA2wc6BlvfigUwiH5IOG4LoTEzRocBjDQ");
        return defaultHttpClient.execute(httpHost, httpRequest);
    }

    public Map<String, Object> parseVendorsFromBody(String body) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> vendors;
        try {
            vendors = mapper.readValue(body, Map.class);
        } catch (IOException e) {
//            LOG.error("saveInstitutionPreferences Error parsing json", e);
            throw new RuntimeException("Error parsing json");
        }
        return vendors;
    }
}
