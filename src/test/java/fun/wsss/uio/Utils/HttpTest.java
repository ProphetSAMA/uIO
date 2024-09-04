package fun.wsss.uio.Utils;

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