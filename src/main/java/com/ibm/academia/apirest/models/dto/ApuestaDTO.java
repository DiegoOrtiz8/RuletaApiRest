package com.ibm.academia.apirest.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApuestaDTO {
    private Integer id;
    private Double dinero;
    private String apuestaRealizada;
    private String resultado;
    private String estado;
}
