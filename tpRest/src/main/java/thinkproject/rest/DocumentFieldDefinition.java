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
public class DocumentFieldDefinition extends Resource {

    public static List<DocumentFieldDefinition> getDocumentFieldDefinitions(RestClient client, DocumentFormDefinition documentFormDefinition) {
        List<DocumentFieldDefinition> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, documentFormDefinition.url + "/document_field_definitions");
        for (String item : list) {
            DocumentFieldDefinition resource = new DocumentFieldDefinition();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }

    public static DocumentFieldDefinition getDocumentFieldDefinitionById(RestClient client, DocumentFormDefinition documentFormDefinition, String id) {
        String item = Resource.getResourceById(client, documentFormDefinition.url + "/document_field_definitions", id);
        if (item == null) {
            return null;
        }
        DocumentFieldDefinition resource = new DocumentFieldDefinition();
        resource.title = thinkproject.Util.getProperty(item, "title");
        resource.url = thinkproject.Util.getProperty(item, "href");
        return resource;
    }
}
