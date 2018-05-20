package queryexecutors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class SelectionTest {

  @Test
  public void shouldSelectToyStory() {
    Scan scan = new Scan("movies");
    Predicate<Map<String, String>> predicate = tuple -> tuple.get("title").equalsIgnoreCase("Toy Story (1995)");
    Selection selection = new Selection(predicate, scan);

    Optional<Map> first = selection.next();
    first.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertTrue(first.isPresent());

    Optional<Map> second = selection.next();
    second.ifPresent(tuple -> System.out.println(tuple.toString()));
    assertFalse(second.isPresent());
  }
}
