package fun.wsss.uio.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpTest {

        @org.junit.jupiter.api.Test
        void get() {
            Http http = new Http();
            System.out.println(http.response);
            Json json = new Json();
            json.processResponse(http);
            System.out.println(json.quantity);
        }

        @org.junit.jupiter.api.Test
        void post() {
        }

        @org.junit.jupiter.api.Test
        void put() {
        }

        @org.junit.jupiter.api.Test
        void delete() {
        }

}