package driver;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import rdfGenerator.RDFGenerator;
import rdfGenerator.RDFUpdator;
import triple.Triple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("Duplicates")
public class Main {

    public static void main(String[] args) {
        Main.executeQuery();
//        Main.generateRDF();
    }


    public static void executeQuery() {
        String queryIntegrity =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX dcat: <http://www.w3.org/ns/dcat#>\n" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>\n" +
                "PREFIX ds: <https://data.brla.gov/resource/_6fyg-p3r9/>\n" +
                "PREFIX dsbase: <https://data.brla.gov/resource/>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n" +
                "PREFIX geo1: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX ods: <http://open-data-standards.github.com/2012/01/open-data-standards#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX socrata: <http://www.socrata.com/rdf/terms#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "\n" +
                "\n" +
                "select distinct ?council_person ?full_address ?city ?zip ?something\n" +
                "where {\n" +
                "  {\n" +
                "    ?person ds:council_person ?council_person.\n" +
                "    ?person ds:full_address ?full_address.\n" +
                "    ?person ds:city ?city.\n" +
                "    ?person ds:something ?something.\n" +
                "    ?person ds:zip ?zip\n" +
                "  }  \n" +
                "}\n" +
                "limit 80\n";
        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShortNoSubject.ttl");
        Query query = QueryFactory.create(queryIntegrity);
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        ResultSet results = qExe.execSelect();
        ResultSetFormatter.out(System.out, results, query) ;

//        while(results.hasNext()) {
//            QuerySolution solution = results.next();
//            RDFNode councilPerson = solution.get("council_person");
//            RDFNode fullAddress = solution.get("full_address");
//            System.out.println(councilPerson.toString() + " " + fullAddress.toString());
//        }
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
