package org.egov.legal.entity;

import jakarta.persistence.*;

//@Entity
//@Table(name = "advocates")
public class Advocate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advocateId;

    @Column(unique = true, nullable = false)
    private String barRegistrationNumber;

    @Column(unique = true)
    private String aorCode; //  for AORs  --> Advocate-on-Record 

    private String name;

    private Integer enrolmentYear;

    @Column(name = "is_aor")
    private Boolean aor = false;

    private String mobile;
    private String email;

    private Boolean active = true;
}

