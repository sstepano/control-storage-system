package org.code_studio.model;

import java.util.Objects;


public enum PaletteStatus {

    PREPARING       (1, "U procesu pripreme"),
    WAITINGFORENTRY (2, "Čeka skladištenje, da se preuzme kada dodje RP1003"),
    STORED          (3, "Paleta smeštena na zadatu poziciju"),
    READYFOREXIT    (4, "Paleta čeka na izlaz iz magacina"),
    EXITED          (5, "Paleta je izašla iz magacina"),
    PROCESSING      (6, "Paleta je na putu u ili iz magacina"),
    ERROR           (7, "Greška pri smeštanju, škart, nije prošla kontrolu"),
    ;

    private final Integer id;
    private final String description;

    private PaletteStatus (Integer id, String description) {
        this.id = Objects.requireNonNull(id);
        this.description = Objects.requireNonNull(description);
    }

    public Integer getId() {
        return this.id;
    };

    public String getDescription() {
        return this.description;
    }

}
