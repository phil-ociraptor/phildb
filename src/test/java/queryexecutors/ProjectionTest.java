package queryexecutors;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ProjectionTest {

  @Test
  void test1() {
    Scan scan = new Scan("movies");
    Predicate<Map<String, String>> predicate = tuple -> tuple.get("movieId").equalsIgnoreCase("5000");
    Selection selection = new Selection(predicate, scan);
    Node projection = new Projection(Lists.newArrayList("title"), selection);

    Optional<Map> first = projection.next();
    first.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(first.isPresent());

  }
}