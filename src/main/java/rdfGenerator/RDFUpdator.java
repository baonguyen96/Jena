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

            System.out.println(statement);

            /*
             * if found the matched subject:
             *      and found the predicate:
             *          then add to the list to be removed
             */
            for(Triple triple: triples) {
                System.out.println("Statement.subject   = " + subject.getURI());
                System.out.println("Statement.predicate = " + predicate.getLocalName());
                System.out.println("Triple.subject      = " + triple.getSubject());
                System.out.println("Triple.predicate    = " + triple.getPredicate());

                if(subject.getURI().endsWith(triple.getSubject())) {
                    if(predicate.getLocalName().endsWith(triple.getPredicate().substring(1))) {
                        statementsToRemove.add(statement);
                    }
                }
            }
        }

        System.out.println("#statementsToRemove = " + statementsToRemove.size());

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
                        updatedPredicate.getLocalName().endsWith(currentTriple.getPredicate().substring(1))) {
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

}
