package SampleDataBase;

/**
 * Created by agua on 21/04/15.
 */
public class IndividualCreator {


    public static final String NO_ANTECEDENTES = "No antecedentes";

    Individual firstIndividual = new Individual("Jaime", NO_ANTECEDENTES, 1, "ADG-584");
    Individual secondIndividual = new Individual("Pedro", NO_ANTECEDENTES, 2, "LJG-885");
    Individual thirdIndividual = new Individual("Travis", NO_ANTECEDENTES, 3, "FFK-737");
    Individual fourthIndividual = new Individual("Javier", NO_ANTECEDENTES, 4, "GSC-853");
    Individual fifthIndividual = new Individual("James", NO_ANTECEDENTES, 5, "LOJ-235");


    public Individual[] individuals = {firstIndividual, secondIndividual, thirdIndividual, fourthIndividual, fifthIndividual};

    public Individual[] getIndividuals() {
        return individuals;
    }
}
