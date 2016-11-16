package edu.cornell.finapiclient;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class AccountClientTest {

    @Test
    public void getAccounts() throws Exception {
        AccountClient accountClient = new AccountClient();
        HttpResponse httpResponse = accountClient.getAccounts();
        Assert.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void parseVendorsFromBody() {
        AccountClient accountClient = new AccountClient();
        String body = "{\"account\": [{\"chartOfAccountsCode\":\"BL\",\"accountNumber\":\"1031400\"}]}";
        Map<String, Object> accounts = accountClient.parseVendorsFromBody(body);
        Assert.assertEquals(1, accounts.size());
    }

}