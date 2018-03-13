package constraints;

import java.util.LinkedList;
import java.util.List;


public class ConstraintCollection {

   private List<Constraint> constraints;

    public ConstraintCollection() {
        this.constraints = new LinkedList<>();
    }

    public void addConstraintToCollection(Constraint constraint) {
        this.constraints.add(constraint);
    }


    public Constraint getConstraintForPredicate(String predicate) {
        for(Constraint constraint : this.constraints) {
            if(constraint.getPredicate().equals(predicate)) {
                return constraint;
            }
        }
        return null;
    }

}
