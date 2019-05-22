/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinkproject.rest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joost.meulenkamp
 */
public class Document extends Resource {

    public static List<Document> getDocuments(RestClient client, Filter filter) {
        List<Document> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, filter.url + "/documents");
        for (String item : list) {
            Document resource = new Document();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }
}
