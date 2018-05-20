package queryexecutors;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Selection implements Node {

  private Node child;
  private Predicate predicate;

  public Selection(Predicate<Map<String, String>> predicate, Node child) {
    this.predicate = predicate;
    this.child = child;
  }

  @Override
  public Optional<Map> next() {
    Optional<Map> current = child.next();
    while (current.isPresent()) {
      if (predicate.test(current.get())) {
        return current;
      } else {
        current = child.next();
      }
    }

    return current;
  }

  // sad panda, functionalNext() isn't tail-recursive
  public Optional<Map> functionalNext() {
    Optional<Map> current = child.next();
    return current
        .map((Function<Map, Boolean>) predicate::test)
        .flatMap(tupleMatches -> tupleMatches ? current : this.next());
  }
}
