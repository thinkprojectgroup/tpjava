/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinkproject.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joost.meulenkamp
 */
public class Draft extends Resource {

    public static List<Draft> getDrafts(RestClient client, Project project) {
        List<Draft> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, project.url + "/drafts");
        for (String item : list) {
            Draft resource = new Draft();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }

    public static Draft createDraft(RestClient client, Project project, String subject, String message) {
        Map<String, String> map = new HashMap<>();
        map.put("subject", subject);
        map.put("message", message);
        String body = thinkproject.Util.toJson(map);
        String response = client.executeRequest(project.url + "/drafts", RestClient.Method.POST, body);
        Draft draft = new Draft();
        draft.url = thinkproject.Util.getProperty(response, "location");
        Logger.getLogger(Draft.class.getName()).log(Level.INFO, "{0} {1}",
                new Object[]{
                    subject,
                    draft.url
                });
        return draft;
    }
}
