package domain;

import infrastructure.repositories.CandidateRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateServiceTest {
    @Inject
    CandidateService candidateService;
    @InjectMock
    CandidateRepositoryImpl candidateRepository;

    @Test
    void save() {
        Candidate candidate = Instancio.create(Candidate.class);

        candidateService.save(candidate);

        verify(candidateRepository).save(candidate);
        verifyNoMoreInteractions(candidateRepository);
    }

    @Test
    void findAll() {
        List<Candidate> candidateList = Instancio.stream(Candidate.class).limit(10).toList();
        when(candidateRepository.findAll()).thenReturn(candidateList);

        List<Candidate> result = candidateService.findAll();

        verify(candidateRepository).findAll();
        verifyNoMoreInteractions(candidateRepository);
        assertEquals(candidateList, result);
    }

    @Test
    void findByIdSuccess() {
        Candidate candidate = Instancio.create(Candidate.class);
        when(candidateRepository.findById(candidate.id())).thenReturn(Optional.of(candidate));

        Candidate result = candidateService.findById(candidate.id());

        verify(candidateRepository).findById(candidate.id());
        verifyNoMoreInteractions(candidateRepository);
        assertEquals(candidate, result);
    }

    @Test
    void findByIdFailure() {
        Candidate candidate = Instancio.create(Candidate.class);
        when(candidateRepository.findById(candidate.id())).thenReturn(Optional.empty());

        long candidateId = candidate.id();
        assertThrows(IllegalArgumentException.class, () -> candidateService.findById(candidateId));
        verify(candidateRepository).findById(candidateId);
        verifyNoMoreInteractions(candidateRepository);
    }
}