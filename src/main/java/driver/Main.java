package driver;

import constraints.Constraint;
import constraints.ConstraintCollection;
import constraints.Mutation;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import rdf.QueryPreparer;
import rdf.RDFGenerator;
import triple.Triple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        // good data
        ResultSet originalResultSet = executeQuery();
        List<Triple> triples = createListOfTriples(
                originalResultSet, null,
                "council_person", "full_address", "city", "zip"
        );
        Model model = RDFGenerator.createDefaultModel();
        model = RDFGenerator.addMultipleTriplesToModel(model, triples);
        saveRDFGraphToFile(model, "GoodModelFromFuseki.ttl");

        // bad data
//        ResultSet modifiedResultSet = executeQuery();
//        ConstraintCollection constraintCollection = createConstraintCollection();
//        List<Triple> modifiedTriples = createListOfTriples(
//                modifiedResultSet, constraintCollection,
//                "council_person", "full_address", "city", "zip"
//        );
//        Model badModel = RDFGenerator.createDefaultModel();
//        badModel = RDFGenerator.addMultipleTriplesToModel(badModel, modifiedTriples);
//        saveRDFGraphToFile(badModel, "BadModel.ttl");

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


    private static void printTriples(List<Triple> triples) {
        for(Triple triple : triples) {
            System.out.println(triple.toString());
        }
    }


    private static ResultSet executeQuery() {
        String queryString = QueryPreparer.createSelectQuery(
                "80", "council_person", "full_address", "city", "zip");

        System.out.println(queryString);


//        Model model = ModelFactory.createDefaultModel();
//        model.read("sample-rdf/AddressesShortNoSubject.ttl");
//        Query query = QueryFactory.create(queryString);
//        QueryExecution qExe = QueryExecutionFactory.create(query, model);

        QueryExecution qExe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/dataset.html?tab=upload&ds=/query/sparql?force=true",
                queryString
        );

        ResultSet resultSet = qExe.execSelect();

        ResultSetFormatter.out(System.out, resultSet);

        return resultSet;
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
