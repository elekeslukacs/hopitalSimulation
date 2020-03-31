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
public class Reason {
    String name;
    int time;
    int price;
}
