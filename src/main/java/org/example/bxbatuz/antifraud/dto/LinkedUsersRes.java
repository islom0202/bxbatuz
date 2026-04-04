package org.example.bxbatuz.antifraud.dto;

import java.sql.Timestamp;

public interface LinkedUsersRes {
    String getLinkName();
    String getCode();
    String getUserDeviceId();
    Timestamp getSubmittedAt();
}
