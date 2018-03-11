package rdfGenerator;

import org.apache.jena.rdf.model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import triple.Triple;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RDFUpdatorTest {
    private static Model originalModel;
    private Model modifiedModel;


    @BeforeClass
    public static void setupOriginalModel() {
        System.out.println("Setup Original Model");
        originalModel = ModelFactory.createDefaultModel();
        originalModel.read("sample-rdf/AddressesShort.ttl");
    }


    @Before
    public void resetModifiedModel() {
        modifiedModel = ModelFactory.createDefaultModel().add(originalModel);
    }


    @Test
    public void testRdfUpdatorSingleTupleSuccess() {
        Triple triple = new Triple("230056", "council_person", "Some Name");
        int affectedStatementCount = RDFUpdator.update(modifiedModel, triple);

        StmtIterator stmtIterator = modifiedModel.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        boolean found = false;

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();

            subject = statement.getSubject();
            predicate = statement.getPredicate();

            if(subject.getURI().endsWith(triple.getSubject()) &&
                    predicate.getLocalName().equals(triple.getPredicate())) {
                found = true;
                assertEquals(triple.getObject(), statement.getObject().toString());
            }
        }

        assertTrue(found);
        assertEquals(1, affectedStatementCount);

    }


    @Test
    public void testRdfUpdatorInvalidUpdateEmpty() {
        // test empty subject
        Triple triple = new Triple("", "city", "Some City");
        int affectedStatementsCount = RDFUpdator.update(modifiedModel, triple);
        int totalStatementsCount = 0;

        StmtIterator stmtIterator = modifiedModel.listStatements();
        Statement statement;

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            assertNotEquals(triple.getSubject(), statement.getSubject().getURI());
            if(statement.getPredicate().getLocalName().equals(triple.getPredicate())) {
                assertEquals(triple.getObject(), statement.getObject().toString());
                totalStatementsCount++;
            }
        }

        assertEquals(affectedStatementsCount, totalStatementsCount);

        // test empty predicate
        triple = new Triple("230056", "", "Some Name");
        affectedStatementsCount = RDFUpdator.update(modifiedModel, triple);
        stmtIterator = modifiedModel.listStatements();

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            assertNotEquals(triple.getSubject(), statement.getSubject().getURI());
            assertNotEquals(triple.getPredicate(), statement.getPredicate().getLocalName());
            assertNotEquals(triple.getObject(), statement.getObject().toString());
        }

        assertEquals(0, affectedStatementsCount);
    }


    @Test
    public void testRdfUpdatorMultipleTuplesSuccess() {
        Triple triple1 = new Triple("230129", "district_num", "-1");
        Triple triple2 = new Triple("230112", "city", "UNKNOWN");
        List<Triple> triples = new ArrayList<Triple>();
        triples.add(triple1);
        triples.add(triple2);
        List<Triple> newTriples = new ArrayList<Triple>();
        newTriples.add(triple1);
        newTriples.add(triple2);

        int affectedStatementsCount = RDFUpdator.update(modifiedModel, triples);
        StmtIterator stmtIterator = modifiedModel.listStatements();
        Statement statement;
        Property predicate;
        Resource subject;
        int totalFound = 0;

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            subject = statement.getSubject();
            predicate = statement.getPredicate();

            // validate each triple
            for (Triple triple : newTriples) {
                if (subject.getURI().endsWith(triple.getSubject()) &&
                        predicate.getLocalName().equals(triple.getPredicate())) {
                    totalFound++;
                    assertEquals(triple.getObject(), statement.getObject().toString());
                    break;
                }
            }
        }

        // make sure can get back enough triple
        assertEquals(newTriples.size(), totalFound);
        assertEquals(newTriples.size(), affectedStatementsCount);

    }


}
