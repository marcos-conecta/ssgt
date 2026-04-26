package br.com.oxy.ssgt.infra.controller.project;



import java.util.List;

public record ProjectDTO(

        Long id,
        String name,
        String description,
        Long ownerId,
        List<Long> members

) {
}

