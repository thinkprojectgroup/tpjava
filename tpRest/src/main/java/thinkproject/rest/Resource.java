package thinkproject.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * @class
 */
public abstract class Resource {

    public String title;
    public String url;

    @Override
    public String toString() {
        return title;
    }

    /**
     * This method tries to loop through and retrieve all resources of the
     * specified type from think project!
     *
     * @param client
     * @param url
     * @param resourceType
     * @return
     */
    protected static String getResources(RestClient client, String url, String resourceType) {
        int tries = 2;
        int calls = 0;
        int pageSize = 1000;
        boolean init = false;
        int total = Integer.MAX_VALUE;
        String endResult = "[]";

        while (total > calls * pageSize) {
            String response = client.executeRequest(url + "?page_size=" + pageSize + ",page_offset=" + pageSize * calls, RestClient.Method.GET);
            while (response == null && tries != 0) {
                response = client.executeRequest(url + "?page_size=" + pageSize + ",page_offset=" + pageSize * calls, RestClient.Method.GET);
                tries--;
            }

            //abort if the server hasn't given any response after 3 times
            if (response == null) {
                return null;
            }
            tries = 2;
            calls++;
            if (init == false) {
                String meta = thinkproject.Util.getProperty(response, "meta");
                total = Integer.getInteger(thinkproject.Util.getProperty(meta, "total"));
                init = true;
            }
            endResult = thinkproject.Util.join(endResult, thinkproject.Util.getProperty(response, resourceType));
        }

        return "";
    }

    protected static int getTotal(RestClient client, String url) {
        String response = client.executeRequest(url, RestClient.Method.GET);
        if (response == null) {
            return -1;
        }
        String meta = thinkproject.Util.getProperty(response, "meta");
        return Integer.getInteger(thinkproject.Util.getProperty(meta, "total"));
    }

    /**
     * Get tp! Resource Details
     *
     * @param client tp! Rest Client
     * @return Resource Details
     */
    public String getDetails(RestClient client) {
        return client.executeRequest(url, RestClient.Method.GET);
    }
}
