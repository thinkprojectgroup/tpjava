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
import java.util.Map.Entry;

/**
 *
 * @author joost.meulenkamp
 */
public class DraftDocument extends Resource {

    public static List<DraftDocument> getDraftDocumentss(RestClient client, Draft draft) {
        List<DraftDocument> resources = new ArrayList<>();
        List<String> list = Resource.getResources(client, draft.url + "/draft_documents");
        for (String item : list) {
            DraftDocument resource = new DraftDocument();
            resource.title = thinkproject.Util.getProperty(item, "title");
            resource.url = thinkproject.Util.getProperty(item, "href");
            resources.add(resource);
        }
        return resources;
    }

    public static DraftDocument createDraftDocument(RestClient client, Draft draft, DocumentFormDefinition documentFormDefinition) {
        DraftDocument draftDocument = new DraftDocument();
        return draftDocument;
    }

    /**
     *
     * @param values
     * @param client
     * @return
     */
    public DraftDocument setValues(RestClient client, Map<DocumentFieldDefinition, Object> values) {
        String json = "{fields:[";
        for (Entry<DocumentFieldDefinition, Object> entry : values.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("definition", entry.getKey().url);
            map.put("value", entry.getValue());
            json += thinkproject.Util.toJson(map) + ",";
        }
        json = json.substring(0, json.length() -1) + "]}";
        client.executeRequest(this.url, RestClient.Method.PUT, json);
        return this;
    }
}
