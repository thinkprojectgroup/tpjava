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
public class Communication extends Resource {

    public static List<Communication> getCommunications(RestClient client, Project project) {
        List<Communication> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, project.url + "/documents");
        for (String item : list) {
            Communication resource = new Communication();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }

    public static Communication createCommunication(RestClient client, Draft draft) {
        String response = client.executeRequest(draft.url + "/send_wait", RestClient.Method.POST);
        Communication communication = new Communication();
        communication.url = thinkproject.Util.getProperty(response, "location");
        return communication;
    }
    
    public static Communication createCommunication(RestClient client, DraftDocument draftDocument) {
        String response = client.executeRequest(draftDocument.url + "/send_wait", RestClient.Method.POST);
        Communication communication = new Communication();
        communication.url = thinkproject.Util.getProperty(response, "location");
        return communication;
    }
   
}
