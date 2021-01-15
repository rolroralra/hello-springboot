package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnershipVO {
    @Getter
    public enum Permission {
        CREATE("CREATE"),
        READ("READ"),
        UPDATE("UPDATE"),
        DELETE("DELETE");

        private String value;

        Permission(String value) {
            this.value = value;
        }
    }

    public OwnershipVO(String ownershipId, Permission permission) {
        this.ownershipId = ownershipId;
        this.permission = permission.getValue();
    }

    private String ownershipId;
    private String permission;

}
