package queryexecutors;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjectionTest {

  @Test
  void test1() {
    Scan scan = new Scan("movies");
    Selection selection = new Selection(
        (ResultElement element) ->
            element.field.equalsIgnoreCase("movieId")
                && element.value.equalsIgnoreCase("5000"),
        scan);
    Node projection = new Projection(Lists.newArrayList("title"), selection);


    Optional<List<ResultElement>> first = projection.next();
    first.ifPresent(result -> result.stream().forEach(elem -> System.out.println(elem.toString())));
    assertTrue(first.isPresent());

  }
}