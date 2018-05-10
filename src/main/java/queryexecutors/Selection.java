package queryexecutors;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Selection implements Node {

  private Node child;
  private Predicate predicate;

  public Selection(Predicate<ResultElement> predicate, Node child) {
    this.predicate = predicate;
    this.child = child;
  }

  @Override
  public Optional<List<ResultElement>> next() {
    Optional<List<ResultElement>> current = child.next();
    while (current.isPresent()) {
      Boolean aBoolean =
          current.map(resultElements -> resultElements.stream().anyMatch(predicate)).get();
      if (aBoolean) {
        return current;
      } else {
        current = child.next();
      }
    }

    return current;
  }

  // sad panda, functionalNext() isn't tail-recursive
  public Optional<List<ResultElement>> functionalNext() {
    Optional<List<ResultElement>> current = child.next();
    return current
        .map(resultElements -> resultElements.stream().anyMatch(predicate))
        .flatMap(tupleMatches -> tupleMatches ? current : this.next());
  }
}
