package rdfGenerator;

import org.apache.jena.rdf.model.*;
import triple.Triple;

import java.util.ArrayList;
import java.util.List;


public class RDFUpdator {
    /***
     * method: update
     *
     * update a single triple in the RDF model
     *
     * @param originalModel: the RDF model
     * @param triple: subject-predicate-object
     * @return an updated model
     */
    public static Model update(Model originalModel, Triple triple) {
        List<Triple> triples = new ArrayList<>();
        triples.add(triple);
        return update(originalModel, triples);
    }


    /***
     * method: update
     *
     * update multiple triples in the RDF model
     *
     * @param originalModel: the RDF model
     * @param triples: list of 1 or more triples
     * @return an updated model
     */
    public static Model update(Model originalModel, List<Triple> triples) {
        Model model = ModelFactory.createDefaultModel().add(originalModel);
        List<Statement> statementsToRemove = new ArrayList<Statement>();
        List<Triple> copyOfTriples = new ArrayList<>(triples);
        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;

        // read all triple from the ttl file
        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            subject = statement.getSubject();
            predicate = statement.getPredicate();

            /*
             * if found the matched subject:
             *      and found the predicate:
             *          then add to the list to be removed
             */
            for(Triple triple: copyOfTriples) {
                if(subject.getURI().endsWith(triple.getSubject())) {
                    if(predicate.getLocalName().equalsIgnoreCase(triple.getPredicate())) {
                        statementsToRemove.add(statement);
                    }
                }
            }
        }

        // replace old statements with new ones
        for(Statement removingStatement : statementsToRemove) {
            // build new statement

            // subject
            subject = removingStatement.getSubject();
            Resource updatedSubject = model.createResource(subject.getURI());

            // predicate
            Property updatedPredicate = removingStatement.getPredicate();

            // object
            String newObjectValue = "Default";
            for(int i = 0; i < triples.size(); i++) {
                if(updatedSubject.getURI().endsWith(triples.get(i).getSubject()) &&
                        triples.get(i).getPredicate().equals(updatedPredicate.getLocalName())) {
                    newObjectValue = triples.remove(i).getObject();
                }

            }
            updatedSubject.addProperty(updatedPredicate, newObjectValue);

            // delete the current statement and add new statement
            model.remove(removingStatement);
        }

        return model;
    }


    /***
     * method: update
     *
     * update a range of predicates of a given subject
     *
     * @param originalModel: original model
     * @param subject: subjectID (row ID)
     * @param predicates: list of predicates to change
     * @return updated model
     */
    public static Model update(Model originalModel, String subject, List<String> predicates) {
        Model model = ModelFactory.createDefaultModel().add(originalModel);



        return model;
    }


}
