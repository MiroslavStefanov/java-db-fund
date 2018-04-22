package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "wizard_deposits")
public class Deposit {
    private Long id;
    private String firstName;
    private String lastName;
    private String notes;
    private Integer age;
    private String magicWandCreator;
    private Integer magicWandSize;
    private String depositGroup;
    private Timestamp depositStartDate;
    private BigDecimal depositAmount;
    private Double depositInterest;
    private BigDecimal depositCharge;
    private Timestamp depositExpirationDate;
    private Boolean isDepositExpired;

    public Deposit() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "notes", length = 1000)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magic_wand_creator) {
        this.magicWandCreator = magic_wand_creator;
    }

    @Column(name = "magic_wand_size")
    public Integer getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(Integer magic_wand_size) {
        this.magicWandSize = magic_wand_size;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String deposit_group) {
        this.depositGroup = deposit_group;
    }

    @Column(name = "deposit_start_date")
    public Timestamp getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(Timestamp deposit_start_date) {
        this.depositStartDate = deposit_start_date;
    }

    @Column(name = "deposit_amount")
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal deposit_amount) {
        this.depositAmount = deposit_amount;
    }

    @Column(name = "deposit_interest")
    public Double getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(Double deposit_interest) {
        this.depositInterest = deposit_interest;
    }

    @Column(name = "deposit_charge")
    public BigDecimal getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(BigDecimal deposit_charge) {
        this.depositCharge = deposit_charge;
    }

    @Column(name = "deposit_expiration_date")
    public Timestamp getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public void setDepositExpirationDate(Timestamp deposit_expiration_date) {
        this.depositExpirationDate = deposit_expiration_date;
    }

    @Column(name = "is_deposit_expired")
    public Boolean getIsDepositExpired() {
        return isDepositExpired;
    }

    public void setIsDepositExpired(Boolean is_deposit_expired) {
        this.isDepositExpired = is_deposit_expired;
    }
}
