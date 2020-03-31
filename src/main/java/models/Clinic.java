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
public class Clinic {
    List<ConsultingRoom> rooms;
}
