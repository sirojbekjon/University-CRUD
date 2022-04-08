package com.example.appjparelationships.payload;

import lombok.Data;
//universityga malumotlarni tashish uchun hizmat qiladi
@Data
public class UniversityDto {

    private String name;
    private String city;
    private String district;
    private String street;
}
