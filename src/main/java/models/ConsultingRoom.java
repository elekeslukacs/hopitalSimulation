package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ToString
@Setter
@Getter
@Builder
public class ConsultingRoom {
    int roomNo;
    Doctor currentDoctor;
    Patient currentPatient;
    List<Patient> patientsConsulted;
    int shiftTime;
    int totalTime;
    boolean isOpen;


}
