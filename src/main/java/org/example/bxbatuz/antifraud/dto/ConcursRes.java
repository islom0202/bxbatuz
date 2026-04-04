package org.example.bxbatuz.antifraud.dto;

import java.sql.Timestamp;

public interface ConcursRes {
    Long getId();
    String getName();
    String getDescription();
    Boolean getIsActive();
    String getAdminName();
    Timestamp getCreatedAt();
}
