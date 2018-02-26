package rdfGenerator;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import triple.Triple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("Duplicates")
public class RdfGenerator {

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

        // update single triple
        Triple triple0 = new Triple("230056", "council_person", "Some Name");
        model = RDFUpdator.update(model, triple0);

        // update multiple triples at once
        Triple triple1 = new Triple("230129", "district_num", "-1");
        Triple triple2 = new Triple("230112", "city", "UNKNOWN");
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(triple1);
        triples.add(triple2);
        model = RDFUpdator.update(model, new ArrayList<Triple>(triples));

        // save rdf file
        try {
            File outputFile = new File("src/main/resources/sample-rdf/AddressesShortModified.ttl");

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


    public static void main(String[] args) {
        RdfGenerator.updateRDF();
    }

}
