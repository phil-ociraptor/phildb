package queryexecutors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NLJoinTest {

  @Test
  public void shouldFindJoin() {
    Scan movies = new Scan("movies");
    Scan ratings = new Scan("ratings");
    BiPredicate<Map, Map> theta = (left, right) -> {
      String leftVal = (String) left.get("movieId");
      String rightVal = (String) right.get("movieId");
      return leftVal.equalsIgnoreCase(rightVal);
    };
    NLJoin join = new NLJoin(theta, movies, ratings);

    Optional<Map> first = join.next();
    first.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(first.isPresent());

    Optional<Map> second = join.next();
    second.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(second.isPresent());
  }

  @Test
  public void shouldJoinWithSelection() {
    Scan movies = new Scan("movies");
    Selection mediumCool = new Selection(tuple -> tuple.get("title").equalsIgnoreCase("Medium Cool (1969)"), movies);
    Scan ratings = new Scan("ratings");
    BiPredicate<Map, Map> theta = (left, right) -> {
      String leftVal = (String) left.get("movieId");
      String rightVal = (String) right.get("movieId");
      return leftVal.equalsIgnoreCase(rightVal);
    };
    NLJoin join = new NLJoin(theta, mediumCool, ratings);

    Optional<Map> first = join.next();
    first.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(first.isPresent());

    Optional<Map> second = join.next();
    second.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(second.isPresent());
  }
}
