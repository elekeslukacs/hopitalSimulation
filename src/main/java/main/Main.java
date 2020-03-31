package main;

import models.Doctor;
import models.Patient;
import models.Reason;
import util.ClinicFunctions;
import util.Functions;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Functions f = new Functions();

        ClinicFunctions cf = new ClinicFunctions();
        cf.prepareSimulation();
    }
}
