package driver;

import constraints.Constraint;
import constraints.ConstraintCollection;
import constraints.Mutation;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import rdfGenerator.RDFGenerator;
import triple.Triple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        ResultSet defaultResultSet = executeQuery();
//        ResultSet modifiedResultSet = executeQuery();
        ConstraintCollection constraintCollection = createConstraintCollection();
        List<Triple> triples = createListOfTriples(
                defaultResultSet, constraintCollection,
                "council_person", "full_address", "city", "zip"
        );
        Model model = RDFGenerator.createDefaultModel();
        model = RDFGenerator.addMultipleTriplesToModel(model, triples);
        saveRDFGraphToFile(model, "GeneratedRDFModel3.ttl");
    }


    private static void printTriples(List<Triple> triples) {
        for(Triple triple : triples) {
            System.out.println(triple.toString());
        }
    }


    public static ResultSet executeQuery() {
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
                "select distinct ?council_person ?full_address ?city ?zip\n" +
                "where {\n" +
                "  {\n" +
                "    ?person ds:council_person ?council_person.\n" +
                "    ?person ds:full_address ?full_address.\n" +
                "    ?person ds:city ?city.\n" +
                "    ?person ds:zip ?zip\n" +
                "  }  \n" +
                "}\n" +
                "limit 80\n";
        Model model = ModelFactory.createDefaultModel();
        model.read("sample-rdf/AddressesShortNoSubject.ttl");
        Query query = QueryFactory.create(queryIntegrity);
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        return qExe.execSelect();
    }


    private static List<Triple> createListOfTriples(
            ResultSet resultSet,
            ConstraintCollection constraintCollection,
            String... predicateNames) {

        List<Triple> triples = new LinkedList<>();
        QuerySolution solution = null;
        String subjectName = "", objectValue = "";
        int totalSubjectCount = 0;

        while(resultSet.hasNext()) {
            solution = resultSet.next();
            subjectName= "Subject" + totalSubjectCount++;

            for(String predicate : predicateNames) {
                objectValue = solution.get(predicate).toString();
                objectValue = mutateObjectIfRequiredByPredicate(predicate, objectValue, constraintCollection);
                triples.add(new Triple(subjectName, predicate, objectValue));
            }
        }

        return triples;
    }


    private static String mutateObjectIfRequiredByPredicate(
            String predicate,
            String objectValue,
            ConstraintCollection constraintCollection) {

        if(constraintCollection != null) {
            Constraint constraintForCurrentPredicate =
                    constraintCollection.getConstraintForPredicate(predicate);

            if (constraintForCurrentPredicate != null) {
                constraintForCurrentPredicate.appendMutationParameter(objectValue);
                objectValue = Mutation.mutate(
                        constraintForCurrentPredicate.getMutationOption(),
                        constraintForCurrentPredicate.getMutationParameters()
                );
            }
        }
        return objectValue;
    }


    /*
     * this method receives all necessary parameters from the UI
     */
    private static ConstraintCollection createConstraintCollection() {
        ConstraintCollection constraintCollection = new ConstraintCollection();
        Constraint constraint = null;

        constraint = new Constraint("zip",
                Mutation.CREATE_RANDOM_STRING, "6");
        constraintCollection.addConstraintToCollection(constraint);

        constraint = new Constraint("council_person",
                Mutation.TRUNCATE_NUMBER_OF_CHARACTERS, "3");
        constraintCollection.addConstraintToCollection(constraint);

        return constraintCollection;
    }


    private static void saveRDFGraphToFile(Model model, String fileName) {
        try {
            File outputFile = new File("src/main/resources/sample-rdf/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            if(!outputFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.createNewFile();
            }

            RDFDataMgr.write(fileOutputStream, model, RDFFormat.TURTLE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
