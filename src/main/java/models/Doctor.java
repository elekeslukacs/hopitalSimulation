package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ToString
@Setter
@Getter
@Builder
public class Doctor implements Serializable {
     String firstName;
     String lastName;
     int age;
     int id;
     @Builder.Default
     String statistics = "";

}
