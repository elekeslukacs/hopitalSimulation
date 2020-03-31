package util;

import models.ConsultingRoom;
import models.Doctor;
import models.Patient;

import java.util.ArrayList;
import java.util.List;

public class ClinicFunctions {
    public ConsultingRoom takeNewPatient(Patient patient, ConsultingRoom room) {

        int reasonTime = patient.getReason().getTime();
        int totalTime = room.getTotalTime();
        int shift = room.getShiftTime();

        if (!(reasonTime < shift)) {
            return room;
        }
        System.out.println("Room " + room.getRoomNo() + " consulting " + patient.getFirstName());

        room.setCurrentPatient(patient);
        room.getPatientsConsulted().add(patient);

        //Subtract from remaining open times
        room.setTotalTime(totalTime - reasonTime);
        room.setShiftTime(shift - reasonTime);

        System.out.println("Room " + room.getRoomNo() + " shift time " + room.getShiftTime() + " and total time " + room.getTotalTime());

        if(room.getTotalTime() < 20){  // 20 is the minimum time required by a reason
            room.setOpen(false);
        }
        return room;
    }

    public ConsultingRoom changeDoctor(Doctor doctor, ConsultingRoom room){
        //When it is time to change the doctor because shift is over, we calculate the statistics for the doctor
        //We will calculate at the end of simulation the statistics for the other doctor
        calculateRoomData(room);

        room.setCurrentDoctor(doctor);
        room.setShiftTime(7*60);
        System.out.println("Room " + room.getRoomNo() + " changed doctor.");
        return room;
    }

    private static boolean checkRoomsOpen(List<ConsultingRoom> rooms){
        for(ConsultingRoom r : rooms){
            if(!r.isOpen()){
                return false;
            }
        }
        return true;
    }

    public void calculateRoomData(ConsultingRoom room){
        //Calculate statistics
        List<Patient> patients = room.getPatientsConsulted();
        int patientNumber = 0;
        int sum = 0;
        int minutes = 0;
        for(Patient p : patients){
            patientNumber++;
            sum += p.getReason().getPrice();
            minutes += p.getReason().getTime();
        }
        room.getPatientsConsulted().clear();
        String statistics = room.getCurrentDoctor().getFirstName() + " " + room.getCurrentDoctor().getLastName() + " - " + room.getCurrentDoctor().getId() +
                ": " + patientNumber + " patients, " + minutes + " minutes, " + sum + " RON";
        room.getCurrentDoctor().setStatistics(statistics);

    }

    public void printRemainingPatients(List<Patient> patients){
        if (patients.size()<1){
            System.out.println("\n\nNo remaining patients.");
            return;
        }
        for(Patient p : patients){
            System.out.println(p.getFirstName() + " " + p.getLastName() + ", " + p.getAge() + " years, " + p.getReason().getName());
        }
    }

    public void prepareSimulation(){
        Functions f = new Functions();
        List<Doctor> doctors = f.generateDoctors(8);
        List<Patient> patients = f.generatePatients();

        f.saveDoctorsOnDisck(doctors);
        f.savePatientsOnDisck(patients);

        f.printDoctors(doctors);
        f.printSummary(patients);


        //Prepare the rooms with appropiate data, 7 hours of shifts, 12 hours of total functioning time
        List<ConsultingRoom> rooms = new ArrayList<>();
        ConsultingRoom r1 = ConsultingRoom.builder()
                .currentDoctor(doctors.get(0))
                .currentPatient(null)
                .roomNo(1)
                .isOpen(true)
                .shiftTime(7*60)
                .totalTime(12*60)
                .patientsConsulted(new ArrayList<>())
                .build();

        ConsultingRoom r2 = ConsultingRoom.builder()
                .currentDoctor(doctors.get(1))
                .currentPatient(null)
                .roomNo(2)
                .isOpen(true)
                .shiftTime(7*60)
                .totalTime(12*60)
                .patientsConsulted(new ArrayList<>())
                .build();

        ConsultingRoom r3 = ConsultingRoom.builder()
                .currentDoctor(doctors.get(2))
                .currentPatient(null)
                .roomNo(3)
                .isOpen(true)
                .shiftTime(7*60)
                .totalTime(12*60)
                .patientsConsulted(new ArrayList<>())
                .build();

        ConsultingRoom r4 = ConsultingRoom.builder()
                .currentDoctor(doctors.get(3))
                .currentPatient(null)
                .roomNo(4)
                .isOpen(true)
                .shiftTime(7*60)
                .totalTime(12*60)
                .patientsConsulted(new ArrayList<>())
                .build();

        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);
        rooms.add(r4);

        simulate(doctors, patients, rooms);
    }

    public void simulate(List<Doctor> doctors, List<Patient> patients, List<ConsultingRoom> rooms){
        if(rooms.size()<3){
            return;
        }
        ConsultingRoom r1 = rooms.get(0);
        ConsultingRoom r2 = rooms.get(1);
        ConsultingRoom r3 = rooms.get(2);
        ConsultingRoom r4 = rooms.get(3);

        int nextDoctor = 4;

        while(checkRoomsOpen(rooms) && patients.size()>0){

            r1 = takeNewPatient(patients.get(0), r1);
            if(r1 != null){
                patients.remove(0);
            }
            if(r1.getShiftTime()<20){                               //20 is the smallest time required by a reason
                r1 = changeDoctor(doctors.get(nextDoctor), r1);
                nextDoctor++;
            }
//
            r2 = takeNewPatient(patients.get(0), r2);
            if(r2 != null){
                patients.remove(0);
            }
            if(r2.getShiftTime()<20){
                r2 = changeDoctor(doctors.get(nextDoctor), r2);
                nextDoctor++;
            }

            r3 = takeNewPatient(patients.get(0), r3);
            if(r3 != null){
                patients.remove(0);
            }
            if(r3.getShiftTime()<20){
                r3 = changeDoctor(doctors.get(nextDoctor), r3);
                nextDoctor++;
            }

            r4 = takeNewPatient(patients.get(0), r4);
            if(r4 != null){
                patients.remove(0);
            }
            if(r4.getShiftTime()<20){
                r4 = changeDoctor(doctors.get(nextDoctor), r4);
                nextDoctor++;
            }

            System.out.println("Patients remaining: " + patients.size() + "\n\n\n");
        }

        //Calculate the statistics for the final doctors.
        calculateRoomData(r1);
        calculateRoomData(r2);
        calculateRoomData(r3);
        calculateRoomData(r4);

        for(Doctor d : doctors){
            System.out.println(d.getStatistics());
        }

        System.out.println("\n\nThe remaining patients:");
        printRemainingPatients(patients);
    }
}
