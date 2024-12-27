package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.repositories.UserRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraUserMapper;
import com.printerapp.infrastructure.persistence.models.User;
import com.printerapp.infrastructure.persistence.utils.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final InfraUserMapper userMapper;

    @Override
    public void save(com.printerapp.domain.aggregates.user.User userDomain) {
        User user = this.userMapper.toPersistenct(userDomain);
        this.entityManager.merge(user);
    }

    @Override
    public Optional<com.printerapp.domain.aggregates.user.User> findById(UserId userId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive")))
                .where(cb.equal(root.get("id"), userId.getValue()));
        try {
            User res = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.userMapper.toDomain(res));
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<com.printerapp.domain.aggregates.user.User> findByEmail(Email email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive")))
                .where(cb.equal(root.get("email"), email.getValue()));
        try {
            User res = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.userMapper.toDomain(res));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public PaginatedResult<com.printerapp.domain.aggregates.user.User> findByCriteria(List<FilterParam<?>> filters,
            List<SortParam> sorts,
            PaginatedParam paginatedParam) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive")));
        // filter
        cq.where(cb.and(Util.buidFilter(cb, filters, root).toArray(new Predicate[0])));

        // sort
        cq.orderBy(Util.buidSort(cb, sorts, root));
        int offset = (paginatedParam.getPage() - 1) * paginatedParam.getSize();
        List<com.printerapp.domain.aggregates.user.User> res = this.entityManager.createQuery(cq).setFirstResult(offset)
                .setMaxResults(paginatedParam.getSize()).getResultList().stream()
                .map((user) -> this.userMapper.toDomain(user)).toList();
        long count = Util.countByCriteria(entityManager, cb, null, filters, User.class);
        return PaginatedResult.of(res, paginatedParam.getPage(), paginatedParam.getSize(), count);
    }

    public PaginatedResult<com.printerapp.domain.aggregates.user.User> search(
            List<FilterParam<?>> search,
            List<FilterParam<?>> criteria,
            List<SortParam> sorts,
            PaginatedParam paginatedParam) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive")));
        // filter
        cq.where(cb.and(
                cb.or(Util.buidFilter(cb, search, root).toArray(new Predicate[0])),
                cb.and(Util.buidFilter(cb, criteria, root).toArray(new Predicate[0]))));

        // sort
        cq.orderBy(Util.buidSort(cb, sorts, root));
        int offset = (paginatedParam.getPage() - 1) * paginatedParam.getSize();
        List<com.printerapp.domain.aggregates.user.User> res = this.entityManager.createQuery(cq).setFirstResult(offset)
                .setMaxResults(paginatedParam.getSize()).getResultList().stream()
                .map((user) -> this.userMapper.toDomain(user)).toList();
        long count = Util.countByCriteria(entityManager, cb, search, criteria, User.class);
        return PaginatedResult.of(res, paginatedParam.getPage(), paginatedParam.getSize(), count);
    }

}
