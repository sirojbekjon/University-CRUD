package com.example.appjparelationships.payload;

import lombok.Data;

@Data
public class StudentDto {
        private String firstName;
        private String lastName;
        private Integer address_id;
        private Integer subjects_id;
        private Integer grouph_id;

}
