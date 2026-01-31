package repositories.base;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T create(T entity);

    List<T> findAll();


    default Optional<T> findById(ID id) {
        throw new UnsupportedOperationException("findById is not supported for this repository");
    }


    default void deleteById(ID id) {
        throw new UnsupportedOperationException("deleteById is not supported for this repository");
    }
}
