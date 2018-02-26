import org.apache.jena.rdf.model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import rdfGenerator.RDFUpdator;
import triple.Triple;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class RDFUpdatorTest {
    private static Model originalModel;

    @BeforeClass
    public static void setupModel() {
        System.out.println("Setup Original Model");
        originalModel = ModelFactory.createDefaultModel();
        originalModel.read("sample-rdf/AddressesShort.ttl");
    }


    @Test
    public void testRdfUpdatorSingleTupleSuccess() {
        Triple triple = new Triple("230056", "council_person", "Some Name");
        Model modifiedModel = RDFUpdator.update(originalModel, triple);

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

    }


    @Test
    public void testRdfUpdatorInvalidUpdateEmpty() {
        // test empty subject
        Triple triple = new Triple("", "city", "Some City");
        Model modifiedModel = RDFUpdator.update(originalModel, triple);

        StmtIterator stmtIterator = modifiedModel.listStatements();
        Statement statement;

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            assertNotEquals("", statement.getSubject().getURI());
        }

        // test empty predicate
        triple = new Triple("230056", "", "Some Name");
        modifiedModel = RDFUpdator.update(originalModel, triple);
        stmtIterator = modifiedModel.listStatements();

        while(stmtIterator.hasNext()) {
            statement = stmtIterator.next();
            assertNotEquals("", statement.getPredicate().getLocalName());
        }
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

        Model modifiedModel = RDFUpdator.update(originalModel, triples);
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

    }


}
