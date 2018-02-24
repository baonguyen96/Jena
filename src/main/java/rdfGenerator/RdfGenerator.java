package rdfGenerator;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;


@SuppressWarnings("Duplicates")
public class RdfGenerator {

    /**
     * method: readRDF
     *
     * test reading the RDF file
     * read each line and print out each tuple
     */
    public static void readRDF() {
        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShort.ttl");

        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        RDFNode object;

        // read all tuple from the ttl file
        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();

            subject = statement.getSubject();
            predicate = statement.getPredicate();
            object = statement.getObject();

            System.out.printf("Subject:   %s\n", subject.getURI());
            System.out.printf("Predicate: %s\n", predicate.getLocalName());
            System.out.printf("Object:    %s\n", object.toString());
            System.out.println();
        }
    }


    /***
     * method: updateRDF
     *
     * update a given tuple with match ID in the RDF graph
     *
     * @param subjectID: select specific subject
     * @param predicateName: which field to update
     * @param newObject: new value of the predicate
     */
    public static void updateRDF(String subjectID, String predicateName, String newObject) {

        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShort.ttl");

        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        RDFNode object;

        // read all tuple from the ttl file
        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();

            subject = statement.getSubject();
            predicate = statement.getPredicate();
            object = statement.getObject();

            /*
             * if found the matched subject:
             *      and found the predicate:
             *          then update
             * else:
             *      then continue
             */
            if(subject.getURI().endsWith(subjectID)) {
                if(predicate.getLocalName().equalsIgnoreCase(predicateName)) {

                    // build new statement


                    // delete the current statement and add new statement
                    model.remove(statement);

                }
            }

//            System.out.printf("Subject:   %s\n", subject.getURI());
//            System.out.printf("Predicate: %s\n", predicate.getLocalName());
//            System.out.printf("Object:    %s\n", object.toString());
//            System.out.println();
        }
    }


    public static void main(String[] args) {
        RdfGenerator.readRDF();
    }

}
