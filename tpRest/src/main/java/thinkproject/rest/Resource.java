package thinkproject.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @param url
     * @param client
     * @return
     */
    protected static List<String> getResources(RestClient client, String url) {
        int tries = 2;
        int calls = 0;
        int pageSize = 1000;
        boolean init = false;
        int total = Integer.MAX_VALUE;
        String[] raw = url.split("/");
        String resourceType = raw[raw.length - 1];
        //String endResult = "[]";

        List<String> list = new ArrayList<>();

        while (total > calls * pageSize) {

            String response = client.executeRequest(url + "?page_size=" + pageSize + "&page_offset=" + pageSize * calls, RestClient.Method.GET);
            while (response == null && tries != 0) {
                response = client.executeRequest(url + "?page_size=" + pageSize + "&page_offset=" + pageSize * calls, RestClient.Method.GET);
                tries--;
            }

            //abort if the server hasn't given any response after 3 times
            if (response == null) {
                return list;
            }
            tries = 2;
            calls++;
            if (init == false) {
                String meta = thinkproject.Util.getProperty(response, "meta");
                total = Integer.parseInt(thinkproject.Util.getProperty(meta, "total"));
                init = true;

                Logger.getLogger(Resource.class.getName()).log(Level.INFO, "{0} {1} found",
                        new Object[]{
                            total,
                            resourceType                             
                        });

            }
            String resources = thinkproject.Util.getProperty(response, resourceType);
            list.addAll(thinkproject.Util.toList(resources));
            //endResult = thinkproject.Util.join(endResult, resources);
        }

        return list;
    }

    /**
     *
     * @param url
     * @param id
     * @param client
     * @return
     */
    protected static String getResourceById(RestClient client, String url, String id) {

        String[] raw = url.split("/");
        String resourceType = raw[raw.length - 1];

        String response = client.executeRequest(url + "?query_identification=" + id, RestClient.Method.GET);

        if (response == null) {
            return null;
        }

        String resource = thinkproject.Util.getProperty(response, resourceType);
        return resource;
    }

    protected static int getTotal(RestClient client, String url) {
        String response = client.executeRequest(url, RestClient.Method.GET);
        if (response == null) {
            return -1;
        }
        String meta = thinkproject.Util.getProperty(response, "meta");
        return Integer.parseInt(thinkproject.Util.getProperty(meta, "total"));
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
