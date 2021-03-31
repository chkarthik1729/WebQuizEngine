package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Integer> {

    @Query(value = "SELECT * FROM Submission WHERE username = ?1", nativeQuery = true)
    public Page<Submission> getSubmissions(String username, Pageable pageable);
}
