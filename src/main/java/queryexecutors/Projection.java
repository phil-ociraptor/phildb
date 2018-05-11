package queryexecutors;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Projection implements Node {

  private Node child;
  private HashSet<String> fieldsSet;

  public Projection(List<String> fields, Node child) {
    this.fieldsSet = Sets.newHashSet(fields);
    this.child = child;
  }

  @Override
  public Optional<List<ResultElement>> next() {
    Optional<List<ResultElement>> current = child.next();
    return current.map(
        elements ->
            elements
                .stream()
                .filter(elem -> fieldsSet.contains(elem.field))
                .collect(Collectors.toList()));
  }
}
