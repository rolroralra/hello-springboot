package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataVO {
    private String dataId;
    private OwnershipVO ownership;
    private long time;

//    private DataVO(DataVO.Builder builder) {
//        this(builder.dataId, builder.ownership, builder.time);
//    }

//    static public Builder builder() {
//        return new Builder();
//    }
//
//    static public class Builder {
//        private String dataId;
//        private OwnershipVO ownership;
//        private long time;
//        public Builder dataId(String val) {
//            this.dataId = val;
//            return this;
//        }
//
//        public Builder ownership(OwnershipVO val) {
//            this.ownership = val;
//            return this;
//        }
//
//        public Builder time(Long val) {
//            this.time = val;
//            return this;
//        }
//
//        public DataVO build() {
//            return new DataVO(this);
//        }
//    }


}
