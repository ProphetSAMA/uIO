package fun.wsss.uio.utils;

class HttpTest {

        @org.junit.jupiter.api.Test
        void get() {
            Http http = new Http();
            String response = http.sendPostRequest("2-1--3-7301");
            System.out.println(response);
            Json json = new Json();
            json.processResponse(response);
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
