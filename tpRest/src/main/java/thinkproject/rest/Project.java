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
public class Project extends Resource {

    public static List<Project> getProjects(RestClient client) {
        List<Project> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, "/services/api/projects");
        for (String item : list) {
            Project resource = new Project();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }
}
