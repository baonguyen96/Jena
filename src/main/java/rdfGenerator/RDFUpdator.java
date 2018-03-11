package rdfGenerator;

import org.apache.jena.rdf.model.*;
import triple.Triple;

import java.util.LinkedList;
import java.util.List;


public class RDFUpdator {

    public static int update(Model model, Triple triple) {
        List<Triple> triples = new LinkedList<>();
        triples.add(triple);
        return update(model, triples);
    }


    public static int update(Model model, List<Triple> triples) {
        List<Statement> statementsToRemove = getAllStatementsToRemove(model, triples);
        int affectedTriplesCount = 0;
        Resource subject = null;
        Property predicate = null;
        Resource object = null;

        for(Statement removingStatement : statementsToRemove) {
            subject = model.createResource(removingStatement.getSubject().getURI());
            predicate = removingStatement.getPredicate();

            for(int i = 0; i < triples.size(); i++) {
                Triple currentTriple = triples.get(i);

                if(areStatementAndTripleMatched(removingStatement, currentTriple)) {
                    object = model.createResource(currentTriple.getObject());

                    if(!currentTriple.getSubject().isEmpty()) {
                        triples.remove(i);
                    }
                    break;
                }
            }

            model.add(subject, predicate, object);
            model.remove(removingStatement);
            affectedTriplesCount++;
        }

        return affectedTriplesCount;
    }


    private static List<Statement> getAllStatementsToRemove(Model model, List<Triple> triples) {
        List<Statement> statementsToRemove = new LinkedList<>();
        StmtIterator stmtIterator = model.listStatements();
        Statement statement = null;

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            for(Triple triple: triples) {
                if(areStatementAndTripleMatched(statement, triple)) {
                    statementsToRemove.add(statement);
                }
            }
        }

        return statementsToRemove;
    }


    private static boolean areStatementAndTripleMatched(Statement statement, Triple triple) {
        String statementSubjectValue = statement.getSubject().getURI();
        String statementPredicateValue = statement.getPredicate().getLocalName();
        String tripleSubjectValue = triple.getSubject();
        String triplePredicateValue = triple.getPredicate();

        // Jena skips the first letter of the predicate name
        return (statementSubjectValue.endsWith(tripleSubjectValue) &&
                statementPredicateValue.endsWith(triplePredicateValue.substring(1)) &&
                statementPredicateValue.length() >= triplePredicateValue.length() - 1);
    }

}
