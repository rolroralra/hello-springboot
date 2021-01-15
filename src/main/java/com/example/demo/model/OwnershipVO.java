package com.example.demo.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class OwnershipVO {
    private String ownershipId;
    private String permission;

    public OwnershipVO(String ownershipId, Permission permission) {
        this.ownershipId = ownershipId;
        this.permission = permission.getValue();
    }

    public OwnershipVO(OwnershipVO.Builder builder) {
        this(builder.ownershipId, builder.permission);
    }

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

    public static class Builder {
        private String ownershipId;
        private String permission;

        public Builder ownershipId(String val) {
            this.ownershipId = val;
            return this;
        }

        public Builder permission(OwnershipVO.Permission val) {
            this.permission = val.getValue();
            return this;
        }

        public Builder permission(String val) {
            this.permission = val;
            return this;
        }

        public OwnershipVO build() {
            return new OwnershipVO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
