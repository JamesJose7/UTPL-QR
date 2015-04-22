package SampleDataBase;

/**
 * Created by agua on 21/04/15.
 */
public class IndividualCreator {


    private final String NO_ANTECEDENTES = "No antecedentes";
    private final String ANTECEDENTES_INFRACCIONES = "Infraccionado";

    Individual firstIndividual = new Individual("Jaime", NO_ANTECEDENTES, 001, "ADG-584");
    Individual secondIndividual = new Individual("Pedro", ANTECEDENTES_INFRACCIONES, 002, "LJG-885");
    Individual thirdIndividual = new Individual("Travis", NO_ANTECEDENTES, 003, "FFK-737");
    Individual fourthIndividual = new Individual("Javier", NO_ANTECEDENTES, 004, "GSC-853");
    Individual fifthIndividual = new Individual("James", ANTECEDENTES_INFRACCIONES, 005, "LOJ-235");


    public Individual[] individuals = {firstIndividual, secondIndividual, thirdIndividual, fourthIndividual, fifthIndividual};


}
