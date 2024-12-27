package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.repositories.EmployeeRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraEmployeeMapper;
import com.printerapp.infrastructure.persistence.mappers.InfraUserMapper;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaEmployeeRepository implements EmployeeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final InfraUserMapper userMapper;

    private final InfraEmployeeMapper employeeMapper;

    @Override
    public void save(com.printerapp.domain.aggregates.employee.Employee employee) {
        Employee employeeModel = this.employeeMapper.toPersistence(employee);
        User user = this.entityManager.getReference(User.class, employeeModel.getUser().getId());
        employeeModel.setUser(user);
        this.entityManager.merge(employeeModel);
    }

    @Override
    public Optional<com.printerapp.domain.aggregates.employee.Employee> findById(UserId userId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.select(
                cb.construct(
                        Employee.class,
                        root.get("id"),
                        root.get("isOnWork"),
                        root.get("startWorkDate"),
                        root.get("employeeId")))
                .where(cb.equal(root.get("id"), userId.getValue()));
        try {
            Employee employee = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.employeeMapper.toDomain(employee));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<com.printerapp.domain.aggregates.user.User> findAllEmployeeOfPrinter(PrinterId printerId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Employee> employeeJoin = root.join("employee", JoinType.RIGHT);
        Join<Employee, PrinterEmployee> printerEmployeeJoin = employeeJoin.join("printers", JoinType.LEFT);
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
        cq.where(cb.equal(printerEmployeeJoin.get("printer").get("id"), printerId.getValue()));
        return this.entityManager.createQuery(cq).getResultList().stream()
                .map((employee) -> this.userMapper.toDomain(employee)).toList();
    }

}
