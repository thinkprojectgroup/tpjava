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
public class DocumentFormDefinition extends Resource {

    public static List<DocumentFormDefinition> getDocumentFormDefinitions(RestClient client, Project project) {
        List<DocumentFormDefinition> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, project.url + "/document_form_definitions");
        for (String item : list) {
            DocumentFormDefinition resource = new DocumentFormDefinition();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }

    public static DocumentFormDefinition getDocumentFormDefinitionById(RestClient client, Project project, String id) {
        String item = Resource.getResourceById(client, project.url + "/document_form_definitions", id);
        if (item == null) {
            return null;
        }
        DocumentFormDefinition resource = new DocumentFormDefinition();
        resource.title = thinkproject.Util.getProperty(item, "title");
        resource.url = thinkproject.Util.getProperty(item, "href");
        return resource;
    }
}
