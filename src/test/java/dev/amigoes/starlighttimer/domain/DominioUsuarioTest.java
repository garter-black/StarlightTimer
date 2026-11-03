package dev.amigoes.starlighttimer.domain;

import dev.amigoes.starlighttimer.domain.entities.ConfiguracoesUsuario;
import dev.amigoes.starlighttimer.domain.entities.Perfil;
import dev.amigoes.starlighttimer.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DominioUsuarioTest {

    @Test
    void deveAceitarFusoHorarioIanaValido() {
        assertDoesNotThrow(() -> new Perfil(UUID.randomUUID(), UUID.randomUUID(), "Teste", null, null, "America/Sao_Paulo", 0));
        assertDoesNotThrow(() -> new Perfil(UUID.randomUUID(), UUID.randomUUID(), "Teste", null, null, "UTC", 0));
    }

    @Test
    void deveRejeitarFusoHorarioInvalido() {
        assertThrows(DomainValidationException.class,
                () -> new Perfil(UUID.randomUUID(), UUID.randomUUID(), "Teste", null, null, "FusoInvalido/Teste", 0));
    }

    @Test
    void deveAceitarDuracoesDeFocoEPausaValidasConformeDoc09() {
        assertDoesNotThrow(() -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 1500, 300, true, true)); // 25 min foco / 5 min pausa
        assertDoesNotThrow(() -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 900, 180, true, true));  // 15 min foco / 3 min pausa
    }

    @Test
    void deveRejeitarDuracaoDeFocoForaDosLimites() {
        // Menor que 15 min (900s)
        assertThrows(DomainValidationException.class,
                () -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 600, 300, true, true));

        // Maior que 90 min (5400s)
        assertThrows(DomainValidationException.class,
                () -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 6000, 300, true, true));

        // Não múltiplo de 5 min (300s)
        assertThrows(DomainValidationException.class,
                () -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 1000, 300, true, true));
    }

    @Test
    void deveRejeitarDuracaoDePausaForaDosLimites() {
        // Menor que 3 min (180s)
        assertThrows(DomainValidationException.class,
                () -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 1500, 120, true, true));

        // Maior que 15 min (900s)
        assertThrows(DomainValidationException.class,
                () -> new ConfiguracoesUsuario(UUID.randomUUID(), UUID.randomUUID(), 1500, 1200, true, true));
    }
}