package rdf;

import org.apache.jena.rdf.model.*;
import triple.Triple;

import java.util.ArrayList;
import java.util.List;


public class RDFGenerator {

    public static Model createDefaultModel() {
        return ModelFactory.createDefaultModel();
    }

    public static Model addTripleToModel(Model model, Triple triple) {
        List<Triple> triples = new ArrayList<>();
        triples.add(triple);
        return addMultipleTriplesToModel(model, triples);
    }


    public static Model addMultipleTriplesToModel(Model model, List<Triple> triples) {
        Model newModel = ModelFactory.createDefaultModel().add(model);
        Resource subject = null;
        Property predicate = null;
        Resource object = null;

        for(Triple triple : triples) {
            subject = newModel.createResource(triple.getSubject());
            predicate = newModel.createProperty(triple.getPredicate());
            object = newModel.createResource(triple.getObject());
            newModel.add(subject, predicate, object);
        }

        return newModel;
    }


}
