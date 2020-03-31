package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ToString
@Setter
@Getter
@Builder
public class Patient {
    String firstName;
    String lastName;
    int age;
    Reason reason;
    String category;
}
