package rdfGenerator;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import triple.Triple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("Duplicates")
public class Main {

    public static void main(String[] args) {
        Main.generateRDF();
    }


    /**
     * method: readRDF
     *
     * test reading the RDF file
     * read each line and print out each triple
     */
    public static void readRDF() {
        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShort.ttl");

        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        RDFNode object;
        int statementCount = 0;

        // read all triple from the ttl file
        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();

            subject = statement.getSubject();
            predicate = statement.getPredicate();
            object = statement.getObject();

            System.out.printf("Subject:   %s\n", subject.getURI());
            System.out.printf("Predicate: %s\n", predicate.getLocalName());
            System.out.printf("Object:    %s\n", object.toString());
            System.out.println();
            statementCount++;
        }

        System.out.println("Total statements: " + statementCount);
    }


    /***
     * method: updateRDF
     *
     * update a given triple with match ID in the RDF graph
     */
    public static void updateRDF() {
        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShort.ttl");

        // update valid single triple
        Triple triple0 = new Triple("230056", "council_person", "Some Name");
        int totalTriplesChanged = RDFUpdator.update(model, triple0);
        System.out.println("Total triples changed: " + totalTriplesChanged);

        // update invalid single triple (wrong id)
        triple0 = new Triple("230057", "council_person", "Some Name");
        totalTriplesChanged = RDFUpdator.update(model, triple0);
        System.out.println("Total triples changed: " + totalTriplesChanged);

        // update valid multiple triples at once
        Triple triple1 = new Triple("230129", "district_num", "-1");
        Triple triple2 = new Triple("230112", "city", "UNKNOWN");
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(triple1);
        triples.add(triple2);
        totalTriplesChanged = RDFUpdator.update(model, new ArrayList<>(triples));
        System.out.println("Total triples changed: " + totalTriplesChanged);

//        saveRDFGraphToFile(model);
    }


    public static void generateRDF() {
        Model model = RDFGenerator.createDefaultModel();
        Triple triple = new Triple("Address1", "zip",  "75090");
        model = RDFGenerator.addTripleToModel(model, triple);
        List<Triple> triples = new LinkedList<>();
        triples.add(new Triple("Address1", "city", "Richardson"));
        triples.add(new Triple("Address1", "state", "TX"));
        triples.add(new Triple("Address2", "zip",  "XHSBS"));
        triples.add(new Triple("Address2", "city", "Random"));
        triples.add(new Triple("Address2", "state", "UNKNOWN"));
        model = RDFGenerator.addMultipleTriplesToModel(model, triples);
        saveRDFGraphToFile(model, "GeneratedRDF1.ttl");
        int totalTriplesUpdated = RDFUpdator.update(model, new Triple("Address1", "zip", "12345"));
        System.out.println(totalTriplesUpdated);
        saveRDFGraphToFile(model, "GeneratedRDF1Modified.ttl");

    }


    public static void saveRDFGraphToFile(Model model, String fileName) {
        try {
            File outputFile = new File("src/main/resources/sample-rdf/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            if(!outputFile.exists()) {
                outputFile.createNewFile();
            }

            RDFDataMgr.write(fileOutputStream, model, RDFFormat.TURTLE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
