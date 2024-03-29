package com.happy3friends.eatcleanmenubackend.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "UserDietaryInfo", schema = "dbo", catalog = "ECM")
public class UserDietaryInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id", nullable = false)
    private int id;
    @Basic
    @Column(name = "UserId", nullable = false)
    private int userId;
    @Basic
    @Column(name = "DietTarget", nullable = false, length = 20)
    private String dietTarget;
    @Basic
    @Column(name = "UserAge", nullable = false)
    private int userAge;
    @Basic
    @Column(name = "UserHeight", nullable = false, precision = 0)
    private double userHeight;
    @Basic
    @Column(name = "UserWeight", nullable = false, precision = 0)
    private double userWeight;
    @Basic
    @Column(name = "ActivityRate", nullable = false, length = 50)
    private String activityRate;
    @Basic
    @Column(name = "BMI", nullable = false, precision = 0)
    private double bmi;
    @Basic
    @Column(name = "BMR", nullable = false, precision = 0)
    private double bmr;
    @Basic
    @Column(name = "CaloriesConsumed", nullable = true, precision = 0)
    private Double caloriesConsumed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "Id", insertable = false, updatable = false)
    private UsersEntity usersByUserId;

}
