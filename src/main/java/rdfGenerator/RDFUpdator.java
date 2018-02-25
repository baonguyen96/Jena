package rdfGenerator;

import org.apache.jena.rdf.model.*;
import triple.Triple;

import java.util.ArrayList;
import java.util.List;


public class RDFUpdator {

    /***
     * method: update
     *
     * update the RDF model
     *
     * @param model: the RDF model
     * @param triple: subject-predicate-object
     * @return an updated model
     */
    public static Model update(Model model, Triple triple) {
        List<Statement> statementsToRemove = new ArrayList<Statement>();
        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        RDFNode object;

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
            if(subject.getURI().endsWith(triple.getSubject())) {
                if(predicate.getLocalName().equalsIgnoreCase(triple.getPredicate())) {
                    statementsToRemove.add(statement);
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
            updatedSubject.addProperty(updatedPredicate, triple.getObject());

            // delete the current statement and add new statement
            model.remove(removingStatement);
        }

        return model;
    }


    /***
     * method: update
     *
     * update the RDF model
     *
     * @param model: the RDF model
     * @param triples: list of 1 or more triples
     * @return an updated model
     */
    public static Model update(Model model, List<Triple> triples) {
        List<Statement> statementsToRemove = new ArrayList<Statement>();
        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        RDFNode object;

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
            for(Triple tuple: triples) {
                if(subject.getURI().endsWith(tuple.getSubject())) {
                    if(predicate.getLocalName().equalsIgnoreCase(tuple.getPredicate())) {
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


}
