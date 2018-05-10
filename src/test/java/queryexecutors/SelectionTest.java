package queryexecutors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SelectionTest {

  @Test
  public void shouldSelectToyStory() {
    Scan scan = new Scan("movies");
    Selection selection = new Selection(
        (ResultElement element) ->
            element.field.equalsIgnoreCase("title")
                && element.value.equalsIgnoreCase("Toy Story (1995)"),
        scan);

    Optional<List<ResultElement>> first = selection.next();
    first.ifPresent(result -> result.stream().forEach(slot -> System.out.println(slot.toString())));
    assertTrue(first.isPresent());

    Optional<List<ResultElement>> second = selection.next();
    second.ifPresent(result -> result.stream().forEach(slot -> System.out.println(slot.toString())));
    assertFalse(second.isPresent());
  }
}
