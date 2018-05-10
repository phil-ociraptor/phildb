package queryexecutors;

import java.util.List;
import java.util.Optional;

public interface Node {
    Optional<List<ResultElement>> next();
}
