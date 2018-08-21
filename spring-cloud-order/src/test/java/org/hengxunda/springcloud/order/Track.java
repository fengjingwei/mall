package org.hengxunda.springcloud.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer count;

}
