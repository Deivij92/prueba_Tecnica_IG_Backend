package com.ig.test.model.persistence;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface DataAccesObject<P> {
        public P create(P persistentObj);

        public P read(Object id);

        @Transactional
        public void update(P persistentObj);

        @Transactional
        public void delete(P persistentObj);

        @Transactional
        public List<P> listAll();

        @Transactional
        List<P> listAllID(long id);
}
