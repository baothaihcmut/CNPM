package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraPrinterEmployeeMapper;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaPrinterEmployeeRepository implements PrinterEmployeeRepository {
        @PersistenceContext
        private EntityManager entityManager;

        private final InfraPrinterEmployeeMapper printerEmployeeMapper;

        @Override
        public void save(com.printerapp.domain.aggregates.printer_employee.PrinterEmployee printerEmployee) {
                PrinterEmployee model = this.printerEmployeeMapper.toPersistence(printerEmployee);
                System.out.println(model);
                model.setEmployee(
                                this.entityManager.getReference(Employee.class,
                                                printerEmployee.getId().getEmployeeId().getValue()));
                model.setPrinter(
                                this.entityManager.getReference(Printer.class,
                                                printerEmployee.getId().getPrinterId().getValue()));
                this.entityManager.merge(model);
        }

        @Override
        public Optional<com.printerapp.domain.aggregates.printer_employee.PrinterEmployee> findById(
                        PrinterEmployeeId printerEmployeeId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer");
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                root.get("employee").get("id"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.and(cb.equal(root.get("printer").get("id"), printerEmployeeId.getPrinterId().getValue()),
                                cb.equal(root.get("employee").get("id"),
                                                printerEmployeeId.getEmployeeId().getValue())));
                try {
                        PrinterEmployee res = this.entityManager.createQuery(cq).getSingleResult();
                        return Optional.of(this.printerEmployeeMapper.toDomain(res));
                } catch (NoResultException e) {
                        return Optional.empty();
                }

        }

        @Override
        public List<com.printerapp.domain.aggregates.printer_employee.PrinterEmployee> findByIdEmployeeId(
                        UserId employeeId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer");
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                root.get("employee").get("id"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.equal(root.get("employee").get("id"), employeeId.getValue()));
                return this.entityManager.createQuery(cq).getResultList().stream()
                                .map((printerEmployee) -> this.printerEmployeeMapper.toDomain(printerEmployee))
                                .toList();
        }

        @Override
        public List<com.printerapp.domain.aggregates.printer_employee.PrinterEmployee> findByPrinterId(
                        PrinterId printerId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer");
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                root.get("employee").get("id"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.equal(root.get("printer").get("id"), printerId.getValue()));
                return this.entityManager.createQuery(cq).getResultList().stream()
                                .map((printerEmployee) -> this.printerEmployeeMapper.toDomain(printerEmployee))
                                .toList();
        }

}
