package com.gefe.rrhh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date fecha_nacimiento;
    private Double nroDocumento;
    private String tipoDocumento;
}
