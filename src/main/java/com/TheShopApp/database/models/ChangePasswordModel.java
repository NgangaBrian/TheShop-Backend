package com.TheShopApp.database.models;

import lombok.Data;

@Data
public class ChangePasswordModel {
    private int id;
    private String email;
    private String oldPassword;
    private String newPassword;
}
