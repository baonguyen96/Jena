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
     * @param model: the RDF model
     * @param triple: subject-predicate-object
     * @return number of triples affected
     */
    public static int update(Model model, Triple triple) {
        List<Triple> triples = new ArrayList<>();
        triples.add(triple);
        return update(model, triples);
    }


    /***
     * method: update
     *
     * update multiple triples in the RDF model
     * performing in-place update,
     * i.e. change on the original model, not the copy
     *
     * @param model: the RDF model
     * @param triples: list of 1 or more triples
     * @return number of triples affected
     */
    public static int update(Model model, List<Triple> triples) {
        List<Statement> statementsToRemove = new ArrayList<Statement>();
        StmtIterator stmtIterator = model.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        int affectedTriplesCount = 0;

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
            for(Triple triple: triples) {
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
                Triple currentTriple = triples.get(i);

                if(updatedSubject.getURI().endsWith(currentTriple.getSubject()) &&
                        updatedPredicate.getLocalName().equals(currentTriple.getPredicate())) {
                    newObjectValue = currentTriple.getObject();

                    // remove if not empty (empty <-> affect every subject)
                    if(!currentTriple.getSubject().isEmpty()) {
                        triples.remove(i);
                    }
                    break;
                }
            }
            updatedSubject.addProperty(updatedPredicate, newObjectValue);

            // delete the current statement and add new statement
            model.remove(removingStatement);
            affectedTriplesCount++;
        }

        return affectedTriplesCount;
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
