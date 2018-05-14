package queryexecutors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NLJoinTest {

  @Test
  public void shouldFindJoin() {
    Scan movies = new Scan("movies");
    Scan ratings = new Scan("ratings");
    BiPredicate<List<ResultElement>, List<ResultElement>> theta = (left, right) -> {
      Optional<ResultElement> leftElem = left.stream()
          .filter(elem -> elem.value.equals("movieId"))
          .findFirst();
      Optional<ResultElement> rightElem = right.stream()
          .filter(elem -> elem.value.equals("movieId"))
          .findFirst();
      return leftElem.map(l -> rightElem.map(r -> r.value.equals(l.value)).orElse(false)).orElse(false);
    };
    NLJoin join = new NLJoin(theta, movies, ratings);

    Optional<List<ResultElement>> first = join.next();
    first.ifPresent(result -> result.stream().forEach(slot -> System.out.println(slot.toString())));
    assertTrue(first.isPresent());

    Optional<List<ResultElement>> second = join.next();
    second.ifPresent(result -> result.stream().forEach(slot -> System.out.println(slot.toString())));
    assertFalse(second.isPresent());
  }
}
