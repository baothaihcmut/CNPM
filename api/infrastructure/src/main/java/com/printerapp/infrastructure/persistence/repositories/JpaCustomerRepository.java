package com.printerapp.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.repositories.CustomerRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraCustomerMapper;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final InfraCustomerMapper customerMapper;

    @Override
    public void save(com.printerapp.domain.aggregates.customer.Customer customer) {
        Customer customerModel = this.customerMapper.toPersistence(customer);
        User user = this.entityManager.getReference(User.class, customerModel.getUser().getId());
        customerModel.setUser(user);
        this.entityManager.merge(customerModel);
    }

    @Override
    public Optional<com.printerapp.domain.aggregates.customer.Customer> findById(UserId userId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);
        cq.select(
                cb.construct(
                        Customer.class,
                        root.get("id"),
                        root.get("numOfPapersA3"),
                        root.get("numOfPapersA4"),
                        root.get("numOfPapersA5"),
                        root.get("customerId"),
                        root.get("customerType")))
                .where(cb.equal(root.get("id"), userId.getValue()));
        try {
            Customer res = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.customerMapper.toDomain(res));
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

}
