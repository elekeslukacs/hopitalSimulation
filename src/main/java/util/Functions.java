package util;

import models.Doctor;
import models.Patient;
import models.Reason;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Functions {
    public List<Doctor> generateDoctors(int numberOfDoctors){

        if(numberOfDoctors < 8){
            throw new IllegalArgumentException("at least 8 doctors");
        }

        List<Doctor> doctors = new ArrayList<Doctor>();
        List<Integer> ids = new ArrayList<>();
        String lastName;
        String firstName;
        int age;
        int id;

        for(int i = 0; i <numberOfDoctors; i++){
            firstName = getRandomName(3);
            lastName = getRandomName(2);
            age = getRandomNumberInRange(30,65);
            id = getRandomNumberInRange(1000, 9999);

            //Store ids to assure uniqueness
            if(!ids.contains(id))
                ids.add(id);
            doctors.add(Doctor.builder().firstName(firstName).lastName(lastName).age(age).id(id).build());
        }
        return doctors;
    }

    public List<Patient> generatePatients(){
        List<Patient> patients = new ArrayList<>();
        String firstName;
        String lastName;
        int age;
        String type = "";
        Reason reason;


        //Add manually patients of each type with each reason
        age = getRandomNumberInRange(0, 1);
        reason = Reason.builder().name("consultation").time(30).price(50).build();
        patients.add(Patient.builder()
                .firstName(getRandomName(5))
                .lastName(getRandomName(4))
                .age(age)
                .category(decideCategory(age))
                .reason(reason)
                .build());

        age = getRandomNumberInRange(2, 7);
        reason = Reason.builder().name("prescription").time(20).price(30).build();
        patients.add(Patient.builder()
                .firstName(getRandomName(5))
                .lastName(getRandomName(4))
                .age(age)
                .category(decideCategory(age))
                .reason(reason)
                .build());

        age = getRandomNumberInRange(8, 18);
        reason = Reason.builder().name("prescription").time(20).price(30).build();
        patients.add(Patient.builder()
                .firstName(getRandomName(5))
                .lastName(getRandomName(4))
                .age(age)
                .category(decideCategory(age))
                .reason(reason)
                .build());

        age = getRandomNumberInRange(19, 85);

        reason = getRandomReason();
        patients.add(Patient.builder()
                .firstName(getRandomName(5))
                .lastName(getRandomName(4))
                .age(age)
                .category(decideCategory(age))
                .reason(reason)
                .build());

        //generate the rest of the patients
        for(int i = 0; i < 96; i++){
            firstName = getRandomName(5);
            lastName = getRandomName(4);
            age = getRandomNumberInRange(0, 85);

            reason = getRandomReason();

            type = decideCategory(age);
            patients.add(Patient.builder().firstName(firstName).lastName(lastName).age(age).category(type).reason(reason).build());
        }

        return patients;
    }

    private static Reason getRandomReason(){
        String[] reasons = {"consultation", "treatment", "prescription"};
        int index = getRandomNumberInRange(0, 2);
        Reason reason;

        if(reasons[index].equals("consultation")){
            reason = Reason.builder().name(reasons[index]).time(30).price(50).build();
            return reason;
        }
        if(reasons[index].equals("prescription")){
            reason = Reason.builder().name(reasons[index]).time(20).price(30).build();
            return reason;
        }
        if(reasons[index].equals("treatment")){
            reason = Reason.builder().name(reasons[index]).time(40).price(35).build();
            return reason;
        }

        return null;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static String getRandomName(int length){
        String name = RandomStringUtils.randomAlphabetic(length);

        //First character upper case, the rest of the name to lower case
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }

    private static String decideCategory(int age){
        String type = "";
        if(age <2){
            type = "Children";
        }
        if(age >=2 && age <8){
            type = "Pupil";
        }
        if(age >=8 && age <18){
            type = "Student";
        }
        if(age > 18){
            type = "Adult";
        }
        return type;
    }

    public void printDoctors(List<Doctor> doctors){
        for(Doctor d : doctors){
            System.out.println(d);
        }
    }

    public void printPatients(List<Patient> patients){
        for(Patient p : patients){
            System.out.println(p);
        }
    }

    public void printSummary(List<Patient> patients){
        int children = 0;
        int pupil = 0;
        int student = 0;
        int adult = 0;

        for(Patient p : patients){
            if(p.getCategory().equals("Children")){
                children++;
            }

            if(p.getCategory().equals("Pupil")){
                pupil++;
            }

            if(p.getCategory().equals("Student")){
                student++;
            }

            if(p.getCategory().equals("Adult")){
                adult++;
            }
        }

        System.out.println("Children (0-1): " + children + " patients");
        System.out.println("Pupil (1-7): " + pupil + " patients");
        System.out.println("Student (7-18): " + student + " patients");
        System.out.println("Adult (>18): " + adult + " patients");
    }

    public void saveDoctorsOnDisck(List<Doctor> doctors){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("doctors.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Doctor d : doctors)
            pw.println(d.toString());
        pw.close();
    }

    public void savePatientsOnDisck(List<Patient> patients){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("patients.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Patient p : patients)
            pw.println(p.toString());
        pw.close();
    }
}
