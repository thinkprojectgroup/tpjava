/*
 * The MIT License
 *
 * Copyright 2018 joost.meulenkamp.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package thinkproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import thinkproject.rest.Communication;
import thinkproject.rest.DocumentFieldDefinition;
import thinkproject.rest.DocumentFormDefinition;
import thinkproject.rest.Draft;
import thinkproject.rest.DraftDocument;
import thinkproject.rest.Project;
import thinkproject.rest.RestClient;

/**
 *
 * @author joost.meulenkamp
 */
public class Alpha {

    public static void main(String[] args) {
           writeDocLists();
    }

    public static void writeDocLists() {

        List<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\aCDE\\creds.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Alpha.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Alpha.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list.size() < 4) {
            return;
        }

        RestClient client = new RestClient(list.get(0), list.get(1));
        client.authenticate(list.get(2), list.get(3));

        //create new draft
        //create new draft doc
        //upload zip with csv to clipboard
        //put values to draft doc
        //send draft
       // List<Project> projects = Project.getProjects(client);

        
       // Draft draft = Draft.createDraft(client, projects.get(0), "subject", "message");
       // DocumentFormDefinition doclist = DocumentFormDefinition.getDocumentFormDefinitionById(client, projects.get(0), "doclist");
        String clipboardFileUrl = client.postClipboardFile(new File("topics.zip"));
        
        
        if(true) {
            return;
        }
        
        
//        DraftDocument draftDoc = DraftDocument.createDraftDocument(client, draft, doclist);

        
        
        
//        DocumentFieldDefinition csv = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "DocumentListe");
//        DocumentFieldDefinition sep = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "Separator");
//        DocumentFieldDefinition filetype = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "FileType");
//        DocumentFieldDefinition coding = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "Coding");
//        DocumentFieldDefinition sepmulti = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "SeparatorMultiValue");
//        DocumentFieldDefinition os = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "SeparatorDataSets");
//        DocumentFieldDefinition doctype = DocumentFieldDefinition.getDocumentFieldDefinitionById(client, doclist, "DocumentType");

//        Map<DocumentFieldDefinition, Object> values = new HashMap<>();
//        values.put(csv, clipboardFileUrl);
//        values.put(sep, ",");
//        values.put(filetype, "csv");
//        values.put(coding, "utf8");
//        values.put(sepmulti, "|");
//        values.put(os, "lin");
//        values.put(doctype, "BIM_TPC");        
//        
//        draftDoc.setValues(client, values);
//
//        Communication.createCommunication(client, draft);
    }
}
