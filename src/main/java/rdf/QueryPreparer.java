package rdf;

import annotations.Note;


@Note(message = "Require knowledge of the RDF model and its prefixes")
public class QueryPreparer {

    static final String PREFIXES =
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
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n\n";

    private static final String COUNT_ALL_QUERY =
            "SELECT (COUNT(?s) AS ?triples) WHERE {\n" +
            "    ?s ?p ?o\n" +
            "}";


    public static String createSelectQuery(String limit, String... predicates) {
        return PREFIXES + createSelectWhereClause(limit, predicates);
    }


    private static String createSelectWhereClause(String limit, String... predicates) {
        StringBuilder query = new StringBuilder();

        // select clause
        query.append("select distinct");
        for (String predicate : predicates) {
            query.append(String.format(" ?%s", predicate));
        }

        // where clause
        query.append("\nwhere {\n");
        for (String predicate : predicates) {
            query.append(String.format("    ?subject ds:%s ?%s.\n", predicate, predicate));
        }
        query.append("}\n");
        query.append(String.format("limit %s", limit));

        return query.toString();
    }


    public static String createCountAllQuery() {
        return COUNT_ALL_QUERY;
    }

}
